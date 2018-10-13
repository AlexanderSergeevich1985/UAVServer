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
@Table(name = "REF_NODE_STATISTICS")
public class NodeStatistics extends BaseEntity {
    public static final String NODE_ID = "nodeId";
    public static final String PERFORMANCE_INDEX = "performanceIndex";
    public static final String FAILURE_RATE = "failureRate";
    public static final String FAILURE_PROBABILITY = "failureProbability";

    @Column(name = "node_id", nullable = false)
    private Long nodeId;

    @Column(name = "performance_index", nullable = true)
    private Double performanceIndex;

    @Column(name = "failure_rate", nullable = true)
    private Double failureRate;

    @Column(name = "failure_probability", nullable = true, precision = 5, scale = 4)
    private Double failureProbability;

    @Nonnull
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(@Nonnull Long nodeId) {
        this.nodeId = nodeId;
    }

    @Nullable
    public Double getPerformanceIndex() {
        return performanceIndex;
    }

    public void setPerformanceIndex(@Nullable Double performanceIndex) {
        this.performanceIndex = performanceIndex;
    }

    @Nullable
    public Double getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(@Nullable Double failureRate) {
        this.failureRate = failureRate;
    }

    @Nullable
    public Double getFailureProbability() {
        return failureProbability;
    }

    public void setFailureProbability(@Nullable Double failureProbability) {
        this.failureProbability = failureProbability;
    }
}
