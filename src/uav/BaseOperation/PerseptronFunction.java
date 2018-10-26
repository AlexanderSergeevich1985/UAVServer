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
package uav.BaseOperation;

import org.apache.spark.mllib.linalg.DenseVector;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.BLAS;

public class PerseptronFunction {
    private Vector weightCoeff;
    private double outSignalValue;
    public static double scalefactor = 60;
    long updateTime = System.currentTimeMillis();

    public PerseptronFunction(double[] weightCoeffArray) {
        weightCoeff = new DenseVector(weightCoeffArray);
    }

    double weightSignal(double[] signalArray) {
        return BLAS.dot(weightCoeff, (new DenseVector(signalArray)));
    }

    //forget rest value
    double calcDelayRestValue() {
        long eventTime = System.currentTimeMillis();
        long timeElapsed = eventTime - updateTime;
        this.updateTime = eventTime;
        return outSignalValue * Math.exp(-1 * timeElapsed/scalefactor);
    }

    double calcActivateFunction(double outSignalValue) {
        if(outSignalValue > 0)
            return outSignalValue;
        else
            return 0.001 * outSignalValue;
    }

    public double updateState(double[] signalArray) {
        this.outSignalValue = weightSignal(signalArray) + calcDelayRestValue();
        return calcActivateFunction(this.outSignalValue);
    }
}
