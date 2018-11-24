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
package uav.Common.DataModels;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "REF_OPERATION_STATISTICS")
public class OperationStatistics extends BaseEntity {
    public static final String OPERATION_ID = "operationId";
    public static final String AVERAGE_OPERATION_TIME = "averageOperationTime";
    public static final String DEVIATION_OPERATION_TIME = "deviationOperationTime";
    public static final String FAILURE_PROBABILITY = "failureProbability";

    @Column(name = "operation_id", nullable = false)
    private Long operationId;

    @Column(name = "avg_operation_time", nullable = true)
    private Long averageOperationTime;

    @Column(name = "deviation_operation_time", nullable = true)
    private Long deviationOperationTime;

    @Column(name = "failure_probability", nullable = true, precision = 5, scale = 4)
    private Double failureProbability;

    protected OperationStatistics() {}

    @Nonnull
    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(@Nonnull Long operationId) {
        this.operationId = operationId;
    }

    @Nullable
    public Long getAverageOperationTime() {
        return averageOperationTime;
    }

    public void setAverageOperationTime(@Nullable Long averageOperationTime) {
        this.averageOperationTime = averageOperationTime;
    }

    @Nullable
    public Long getDeviationOperationTime() {
        return deviationOperationTime;
    }

    public void setDeviationOperationTime(@Nullable Long deviationOperationTime) {
        this.deviationOperationTime = deviationOperationTime;
    }

    @Nullable
    public Double getFailureProbability() {
        return failureProbability;
    }

    public void setFailureProbability(@Nullable Double failureProbability) {
        this.failureProbability = failureProbability;
    }
}
