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

import uav.Common.DataModels.BaseTransaction;
import uav.Utils.DateTimeHelper;

import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class BaseTransactionsQueue<T extends BaseTransaction> {
    private volatile long counter;
    private AtomicLong size = new AtomicLong(0);
    private AtomicBoolean writeFlag = new AtomicBoolean(true);
    private AtomicBoolean readFlag = new AtomicBoolean(true);

    private volatile Node<T> first;
    private volatile Node<T> last;

    public BaseTransactionsQueue() {
        counter = 0L;
        this.first = new Node<>();
        this.last = new Node<>();
        this.first.next = this.last;
        this.last.prev = this.first;
    }

    public boolean registerTransaction(final T transaction) {
        if(!writeFlag.compareAndSet(true, false)) return false;
        try {
            transaction.setTransactionId(counter++);
            ZonedDateTime zdtNow = DateTimeHelper.getCurrentTimeWithTimeZone("UTC");
            transaction.setCreationTime(DateTimeHelper.zdtToTimestamp(zdtNow));
            if(this.size.get() > 1) {
                final Node<T> newNode = new Node<>(last, transaction);
                this.last.next = newNode;
                this.last = newNode;
            }
            else if(this.size.get() == 1) {
                this.last.item = transaction;
            }
            else {
                this.first.item = transaction;
            }
            this.size.incrementAndGet();
            return true;
        }
        finally {
            writeFlag.set(true);
        }
    }

    public T unregisterTransaction() {
        if(!readFlag.compareAndSet(true, false)) return null;
        try {
            T result;
            if(this.size.get() > 2) {
                result = this.first.item;
                this.first.item = null;
                final Node<T> next = this.first.next;
                this.first.next = null;
                this.first = next;
                this.first.prev = null;
                this.size.decrementAndGet();
            }
            else if(this.size.get() == 0) {
                return null;
            }
            else {
                if(!writeFlag.compareAndSet(true, false)) return null;
                result = this.first.item;
                if(this.size.get() == 2) {
                    this.first.item = this.last.item;
                    this.last.item = null;
                }
                else {
                    this.first.item = null;
                }
                this.size.decrementAndGet();
                writeFlag.set(true);
            }
            return result;
        }
        finally {
            readFlag.set(true);
        }
    }

    private static class Node<E> {
        volatile E item;
        Node<E> next;
        Node<E> prev;

        Node() {}

        Node(Node<E> prev, E element) {
            this.item = element;
            this.prev = prev;
        }

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}