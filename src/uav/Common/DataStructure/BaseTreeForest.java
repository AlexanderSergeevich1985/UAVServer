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

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BaseTreeForest<T, ID extends Serializable> {
    public static final int DEFAULT_FOREST_SIZE = 10;

    private int forestSize;

    private CopyOnWriteArrayList<ConcurrentSkipListMap<ID, T>> forest;

    public BaseTreeForest() {
        this(DEFAULT_FOREST_SIZE);
    }

    public BaseTreeForest(final int forestSize) {
        this.forestSize = forestSize;
        forest = new CopyOnWriteArrayList<>();
        for(int i = 0; i < forestSize; ++ i) {
            forest.add(new ConcurrentSkipListMap<>());
        }
    }

    public void addKeyValue(final ID key, final T value) {
        this.getTreeContainer(key).put(key, value);
    }

    public boolean isExistKeyValue(final ID key) {
        return this.getTreeContainer(key).containsKey(key);
    }

    public T getValueByKey(final ID key) {
        return this.getTreeContainer(key).get(key);
    }

    public T removeKeyValue(final ID key) {
        return this.getTreeContainer(key).remove(key);
    }

    public ConcurrentSkipListMap<ID, T> getTreeContainer(final ID key) {
        int treeIndex = key.hashCode() % this.forestSize;
        return forest.get(treeIndex);
    }
}
