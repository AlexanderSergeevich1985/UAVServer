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
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PROCESS_EVENTS")
public class ProcessEvent extends BaseEntity {
    public static final String EXECUTION_PLAN_ID = "executionPlanId";
    public static final String EVENT_GENERATOR_IDS = "eventGeneratorIds";
    public static final String EVENT_CONSUMER_IDS = "eventConsumerIds";
    public static final String EVENT_EVALUATOR_NAME = "eventEvaluatorName";

    @Column(name = "execution_plan_id", nullable = false)
    private Long executionPlanId;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="event_id", referencedColumnName="id", nullable = false)
    private List<EventGenerator> eventGeneratorIds;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="event_id", referencedColumnName="id", nullable = false)
    private List<EventConsumer> eventConsumerIds;

    @Column(name = "event_evaluator_name", nullable = false)
    private String eventEvaluatorName;

    @Nonnull
    public Long getExecutionPlanId() {
        return executionPlanId;
    }

    public void setExecutionPlanId(@Nonnull Long executionPlanId) {
        this.executionPlanId = executionPlanId;
    }

    @Nonnull
    public List<EventGenerator> getEventGeneratorIds() {
        return eventGeneratorIds;
    }

    public void setEventGeneratorIds(@Nonnull List<EventGenerator> eventGeneratorIds) {
        this.eventGeneratorIds = eventGeneratorIds;
    }

    @Nonnull
    public List<EventConsumer> getEventConsumerIds() {
        return eventConsumerIds;
    }

    public void setEventConsumerIds(@Nonnull List<EventConsumer> eventConsumerIds) {
        this.eventConsumerIds = eventConsumerIds;
    }

    @Nonnull
    public String getEventEvaluatorName() {
        return eventEvaluatorName;
    }

    public void setEventEvaluatorName(@Nonnull String eventEvaluatorName) {
        this.eventEvaluatorName = eventEvaluatorName;
    }
}
