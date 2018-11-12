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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RingBuffer<T> {
    static public final int DEFAULT_CAPACITY = 100;
    private int capacity = DEFAULT_CAPACITY;
    private int writePos = 0;
    private int readPos = capacity-1;
    private List<T> storage;

    public RingBuffer(final int capacity) {
        this.capacity = capacity;
        storage = new ArrayList<>(capacity);
    }

    public void reset() {
        writePos = 0;
        readPos = capacity-1;
        storage.clear();
    }

    public void append(final T element) {
        if(writePos == readPos) return;
        storage.add(writePos++, element);
        if(writePos == capacity) writePos = 0;
    }

    public T update(final T element) {
        T oldValue = storage.get(writePos);
        storage.add(writePos++, element);
        if(writePos == capacity) writePos = 0;
        if(writePos == readPos) ++readPos;
        if(readPos == capacity) readPos = 0;
        return oldValue;
    }

    @Nullable
    public T read() {
        int newReadPos = readPos + 1;
        if(newReadPos == capacity) newReadPos = 0;
        if(newReadPos == writePos) return null;
        readPos = newReadPos;
        return storage.get(readPos);
    }

    @Nullable
    public T get(final int index) {
        if(index < 0 || index >= capacity) return null;
        return storage.get(index);
    }
}
