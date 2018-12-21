/**The MIT License (MIT)
 Copyright (c) 2018 by AleksanderSergeevich
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package uav.Common.DataStructure;

import uav.Utils.DateTimeHelper;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BaseLRUInMemoryCache<T, ID extends Serializable> implements ICache<T, ID> {
    static public int DEFAULT_EXPIRATION_PERIOD = 5 * 60 * 1000;
    static public long DEFAULT_MAX_CASHE_SIZE = 1000;
    private AtomicLong size;
    private ConcurrentLinkedQueue<Item<ID>> queue;
    private BaseTreeForest<ObjectWrap, ID> storage;

    public BaseLRUInMemoryCache() {
        this.queue = new ConcurrentLinkedQueue<>();
        this.storage = new BaseTreeForest<>();
        this.size = new AtomicLong(0);
    }

    public boolean add(final ID key, final Object value) {
        if(this.queue.add(new Item<>(key, Instant.now()))) {
            this.storage.addKeyValue(key, new ObjectWrap(value));
            this.size.incrementAndGet();
            return true;
        }
        return false;
    }

    public T get(final ID key) {
        ObjectWrap objWrap = this.storage.getValueByKey(key);
        if(this.queue.add(new Item<>(key, Instant.now()))) objWrap.counter.incrementAndGet();
        return (T) objWrap.item;
    }

    public ObjectWrap getObjectWrap(final ID key) {
        ObjectWrap objWrap = this.storage.getValueByKey(key);
        if(this.queue.add(new Item<>(key, Instant.now()))) objWrap.counter.incrementAndGet();
        return objWrap;
    }

    public boolean remove(ID key) {
        if(this.queue.remove(key)) {
            this.storage.removeKeyValue(key);
            this.size.decrementAndGet();
            return true;
        }
        return false;
    }

    public boolean removeLastIfExpired(@Nonnull final TimeUnit units, @Nonnull final long interval) {
        Item<ID> lastItem = this.queue.peek();
        lastItem.stopWatch.stop();
        if(lastItem.stopWatch.getTimeMeasurement(units) >= interval) {
            return removeLast();
        }
        return false;
    }

    public boolean removeLast() {
        Item<ID> lastItem = this.queue.poll();
        if(lastItem != null) {
            ObjectWrap objWrap = this.storage.getValueByKey(lastItem.item);
            if(objWrap.counter.get() == 1) {
                this.storage.removeKeyValue(lastItem.item);
                this.size.decrementAndGet();
            }
            else {
                objWrap.counter.decrementAndGet();
            }
            return true;
        }
        return false;
    }

    public boolean clear() {
        this.queue.clear();
        this.storage.clear();
        return true;
    }

    public long size() {
        return this.size.get();
    }

    public void runClearThread() {
        Thread cleanerThread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    TimeUnit.SECONDS.sleep(DEDAULT_UPDATE_PERIOD);
                    while(this.size.get() > DEFAULT_MAX_CASHE_SIZE) {
                        removeLast();
                    }
                    while(removeLastIfExpired(TimeUnit.MILLISECONDS, DEFAULT_EXPIRATION_PERIOD)) {}
                }
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    public static class Item<E> {
        E item;
        private DateTimeHelper.StopWatch stopWatch;

        Item() {}

        Item(final E item, final Instant timestamp) {
            this.item = item;
            this.stopWatch = DateTimeHelper.StopWatch.getInstance();
        }
    }

    public static class ObjectWrap {
        private Object item;
        public ReadWriteLock rwLock = new ReentrantReadWriteLock();
        public AtomicInteger counter = new AtomicInteger(1);

        ObjectWrap() {}

        ObjectWrap(final Object item) {
            this.item = item;
        }

        public final <T> T readItem() {
            return (T) (rwLock.readLock().tryLock() ? item : null);
        }

        public <T> T writeItem() {
            return (T) (rwLock.writeLock().tryLock() ? item : null);
        }
    }
}
