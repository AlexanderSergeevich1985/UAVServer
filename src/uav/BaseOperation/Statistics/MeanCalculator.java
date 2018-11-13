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
package uav.BaseOperation.Statistics;

import uav.Common.DataStructure.RingBuffer;

public class MeanCalculator<T extends Number> {
    private RingBuffer<T> measurements;
    private double mean;
    private int counter;

    public MeanCalculator(final int capacity) {
        measurements = new RingBuffer<>(capacity);
        counter = 0;
    }

    public boolean init(final T... values) {
        for(T value: values) {
            mean += (double) value;
            measurements.append(value);
            ++counter;
        }
        if(counter == measurements.getCapacity()) {
            mean /= measurements.getCapacity();
            return true;
        }
        else {
            return false;
        }
    }

    public double update(final T value) {
        T oldValue = measurements.update(value);
        mean -= ((double) oldValue / counter);
        mean += ((double) value / counter);
        return mean;
    }

    public double getMean() {
        return mean;
    }
}
