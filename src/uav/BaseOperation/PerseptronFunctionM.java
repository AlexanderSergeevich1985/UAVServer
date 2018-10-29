package uav.BaseOperation;

import org.apache.spark.mllib.linalg.BLAS;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.DenseVector;
import org.apache.spark.mllib.linalg.Vector;
import uav.Utils.DateTimeHelper;

import java.util.concurrent.TimeUnit;

public class PerseptronFunctionM {
    static final int DEFAULT_VECTOR_SIZE = 10;

    private Vector weightCoeff;
    private Vector prevInputSignal;
    private double outSignalValue;
    private double delayCoeff;
    public double scalefactor = 60;
    public static double smoothFactor = 0.9; // 0.5 < smoothFactor < 0.9
    private DateTimeHelper.StopWatch timer = DateTimeHelper.StopWatch.getInstance();

    public PerseptronFunctionM(double[] weightCoeffArray) {
        weightCoeff = new DenseVector(weightCoeffArray);
        prevInputSignal = Vectors.zeros(weightCoeffArray.length);
    }

    double calcDelayCoeff() {
        timer.stop();
        long timeElapsed = timer.getTimeMeasurement(TimeUnit.MILLISECONDS);
        timer.reset();
        return Math.exp(-1 * timeElapsed/scalefactor);
    }

    Vector calcDiffInputSignal(double[] signalArray) {
        Vector result = new DenseVector(signalArray);
        BLAS.axpy((-1) * delayCoeff, prevInputSignal, result);
        return result;
    }

    double calcActivateFunction(double outSignalValue) {
        if(outSignalValue > 0)
            return outSignalValue;
        else
            return 0.001 * outSignalValue;
    }

    public double updateState(double[] inSignalArray) {
        this.delayCoeff = calcDelayCoeff();
        double newOutSignalValue = BLAS.dot(weightCoeff, calcDiffInputSignal(inSignalArray));
        double delayRestValue = delayCoeff * outSignalValue;
        this.outSignalValue = smoothFactor * (newOutSignalValue - delayRestValue) + (1 - smoothFactor) * delayRestValue;
        return calcActivateFunction(this.outSignalValue);
    }
}
