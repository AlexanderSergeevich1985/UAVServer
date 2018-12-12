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
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class BaseTransactionsQueue<T extends BaseTransaction> {
    private AtomicLong counter = new AtomicLong(0);
    private AtomicBoolean writeFlag = new AtomicBoolean(true);
    private AtomicBoolean readFlag = new AtomicBoolean(true);
    private LinkedList<T> queue = new LinkedList<>();

    public BaseTransactionsQueue(final int size) {}

    public boolean registerTransaction(final T transaction) {
        try {
            if(writeFlag.get()) writeFlag.set(false); else return false;
            transaction.setTransactionId(counter.incrementAndGet());
            ZonedDateTime zdtNow = DateTimeHelper.getCurrentTimeWithTimeZone("UTC");
            transaction.setCreationTime(DateTimeHelper.zdtToTimestamp(zdtNow));
            queue.add(transaction);
            return true;
        }
        finally {
            writeFlag.set(true);
        }
    }

    public T unregisterTransaction() {
        try {
            if(readFlag.get()) readFlag.set(false); else return null;
            return queue.pollFirst();
        }
        finally {
            readFlag.set(true);
        }
    }
}
