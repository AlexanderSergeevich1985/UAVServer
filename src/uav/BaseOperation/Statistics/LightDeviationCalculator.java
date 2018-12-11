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

public class LightDeviationCalculator extends LightMeanCalculator {
    private double variance;
    private double deviation;

    public LightDeviationCalculator(final double devInitValue, final double meanInitValue, final int size) {
        super(meanInitValue, size);
        this.variance = Math.pow(devInitValue, 2);
        this.deviation = devInitValue;
    }

    @Override
    public double update(final double newValue) {
        double oldMean = super.getMean();
        double sampleValue = super.update(newValue);
        variance -= (Math.pow(sampleValue, 2) / super.getSize());
        variance += (Math.pow(newValue, 2) / super.getSize());
        variance += Math.pow(oldMean, 2);
        variance -= Math.pow(super.getMean(), 2);
        deviation = Math.sqrt(variance);
        return deviation;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }
}
