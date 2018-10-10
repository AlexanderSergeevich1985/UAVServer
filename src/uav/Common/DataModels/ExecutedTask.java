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

import uav.Common.DataModelTypes.ExecutionTaskStatus;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "EXECUTED_TASKS")
public class ExecutedTask {
    public static final String CHARTERER_ID = "chartererId";
    public static final String EXECUTOR_ID = "executorId";
    public static final String EXECUTION_START_TIME = "executionStartTime";
    public static final String EXECUTION_END_TIME = "executionEndTime";
    public static final String EXECUTION_TYPE = "executionType";
    public static final String EXECUTION_PLAN_ID = "executionPlanId";
    public static final String TASK_STATUS = "taskStatus";

    @Column(name = "charterer_id", nullable = false)
    private Long chartererId;

    @Column(name = "executor_id", nullable = false)
    private Long executorId;

    @Column(name = "execution_start_time", nullable = true)
    private Timestamp executionStartTime;

    @Column(name = "execution_end_time", nullable = true)
    private Timestamp executionEndTime;

    @Column(name = "execution_type", nullable = false, length = 50)
    private String executionType;

    @Column(name = "execution_plan_id", nullable = true)
    private Long executionPlanId;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private ExecutionTaskStatus taskStatus;

    public Long getChartererId() {
        return chartererId;
    }

    public void setChartererId(Long chartererId) {
        this.chartererId = chartererId;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    @Nullable
    public Timestamp getExecutionStartTime() {
        return executionStartTime;
    }

    public void setExecutionStartTime(@Nullable Timestamp executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    @Nullable
    public Timestamp getExecutionEndTime() {
        return executionEndTime;
    }

    public void setExecutionEndTime(@Nullable Timestamp executionEndTime) {
        this.executionEndTime = executionEndTime;
    }

    public String getExecutionType() {
        return executionType;
    }

    public void setExecutionType(String executionType) {
        this.executionType = executionType;
    }

    @Nullable
    public Long getExecutionPlanId() {
        return executionPlanId;
    }

    public void setExecutionPlanId(@Nullable Long executionPlanId) {
        this.executionPlanId = executionPlanId;
    }

    public ExecutionTaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(ExecutionTaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
