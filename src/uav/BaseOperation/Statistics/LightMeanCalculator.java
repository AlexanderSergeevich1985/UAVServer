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

import javax.annotation.Nonnull;

public class LightMeanCalculator {
    private double mean;
    private int size;
    private IDistributionSampler sampler;

    public LightMeanCalculator(final double mean, final int size) {
        this.mean = mean;
        this.size = size;
    }

    public LightMeanCalculator(final double mean, final int size, @Nonnull IDistributionSampler sampler) {
        this.mean = mean;
        this.size = size;
        this.sampler = sampler;
    }

    public double update(final double newValue) {
        double sampleValue = (sampler != null) ? sampler.sampleValue() : this.mean;
        mean -= ((double) sampleValue / size);
        mean += ((double) newValue / size);
        sampler.updateMean(mean);
        return mean;
    }

    public double getMean() {
        return this.mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public IDistributionSampler getSampler() {
        return sampler;
    }

    public void setSampler(IDistributionSampler sampler) {
        this.sampler = sampler;
    }
}
