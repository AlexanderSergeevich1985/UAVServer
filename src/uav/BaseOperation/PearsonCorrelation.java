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

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.mllib.linalg.Matrices;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;

import java.io.Serializable;
import java.util.function.*;

import static java.lang.Math.sqrt;

public class PearsonCorrelation implements Serializable {
    public class CovarianceCalculator implements Serializable {
        public long setSize;
        public int length;
        public int meanOfProductLength;
        public double[] mean;
        public double[] meanOfProduct;

        public CovarianceCalculator(long size, int length) {
            this.setSize = size;
            this.length = length;
            this.mean = new double[length];
            this.meanOfProductLength = length * (length + 1) / 2;
            this.meanOfProduct = new double[meanOfProductLength];
        }

        public void calcCovariance() {
            int index = 0;
            for(int i = 0; i < length; ++i) {
                for(int j = i; j < length; ++j) {
                    meanOfProduct[index] -= mean[i] * mean[j];
                    ++index;
                }
            }
        }
    }

    static public Matrix caclulateCovarianceMatrix( JavaRDD<Vector> procData ) {
        int vectorSize = procData.first().size();
        int vectorSize2 = 0;
        assert( vectorSize2 > 1 );

        Function2<CovarianceCalculator, Vector, CovarianceCalculator> meanCalcs =
                new Function2<CovarianceCalculator, Vector, CovarianceCalculator>() {
                    public CovarianceCalculator call(CovarianceCalculator a, Vector x) {
                        int index = 0;
                        for( int i = 0; i < a.mean.length; ++i ) {
                            a.mean[i] += x.apply(i)/a.setSize;
                            for( int j = i; j < a.mean.length; ++j ) {
                                a.meanOfProduct[index] += ( x.apply(i) * x.apply(j) )/a.setSize;
                                ++index;
                            }
                        }
                        return a;
                    }
                };

        Function2<CovarianceCalculator, CovarianceCalculator, CovarianceCalculator> combine =
                new Function2<CovarianceCalculator, CovarianceCalculator, CovarianceCalculator>() {
                    public CovarianceCalculator call(CovarianceCalculator a, CovarianceCalculator b) {
                        for( int i = 0; i < a.mean.length; ++i ) {
                            a.mean[i] += b.mean[i];
                        }
                        for( int i = 0; i < a.meanOfProduct.length; ++i ) {
                            a.meanOfProduct[i] += b.meanOfProduct[i];
                        }
                        return a;
                    }
                };

        CovarianceCalculator initial = new PearsonCorrelation().new CovarianceCalculator( procData.count(), vectorSize );
        CovarianceCalculator result = procData.aggregate( initial, meanCalcs, combine );
        result.calcCovariance();
        return covarianceMatrixFromArray( result.meanOfProduct, vectorSize );
    }

    static private Matrix covarianceMatrixFromArray( double[] covariance, int size ) {
        double[] covarianceArray = new double[size*size];
        int index = 0;
        for( int i = 0; i < size; ++i ) {
            covarianceArray[size*i +i] = covariance[index];
            for( int j = i+1; j < size; ++j ) {
                ++index;
                covarianceArray[size*i + j] = covariance[index];
                covarianceArray[size*j + i] = covariance[index];
            }
            ++index;
        }
        Matrix covarianceMat = Matrices.dense( size, size, covarianceArray );
        return covarianceMat;
    }

    static public Matrix CalculateCorrelation( Matrix covariance ) {
        int size = covariance.numCols();
        double[] correlationArray = new double[size*size];
        for( int i = 0; i < size; ++i ) {
            correlationArray[size*i +i] = 1;
            double qx = sqrt( covariance.apply( i, i ) );
            for( int j = i+1; j < size; ++j ) {
                double qy = sqrt( covariance.apply( j, j ) );
                double corCoefValue = covariance.apply( i, j ) / ( qx * qy );
                correlationArray[size*i + j] = corCoefValue;
                correlationArray[size*j + i] = corCoefValue;
            }
        }
        Matrix correlationMat = Matrices.dense( size, size, correlationArray );
        return correlationMat;
    }

    static public Matrix CalculateCorrelation( double[] covariance, int size ) {
        double[] correlationArray = new double[size*size];
        int index = 0;
        for( int i = 0; i < size; ++i ) {
            correlationArray[size*i +i] = 1;
            double qx = sqrt( covariance[(i*(1-i)/2 + i*size)] );
            for( int j = i+1; j < size; ++j ) {
                ++index;
                double qy = sqrt( covariance[(j*(1-j)/2 + j*size)] );
                double corCoefValue = covariance[index] / ( qx * qy );
                correlationArray[size*i + j] = corCoefValue;
                correlationArray[size*j + i] = corCoefValue;
            }
            ++index;
        }
        Matrix correlationMat = Matrices.dense( size, size, correlationArray );
        return correlationMat;
    }
}