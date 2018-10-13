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

import uav.Common.DataModelTypes.EventConsumerType;

import javax.annotation.Nonnull;
import javax.persistence.*;

@Entity
@Table(name = "REL_EVENT_CONSUMER")
public class EventConsumer extends BaseEntity {
    public static final String EVENT_ID = "eventId";
    public static final String EVENT_GENERATOR_ID = "eventConsumerId";
    public static final String EVENT_CONSUMER_TYPE = "eventConsumerType";

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "event_consumer_id", nullable = false)
    private Long eventConsumerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "consumer_type", nullable = false)
    private EventConsumerType eventConsumerType;

    @Nonnull
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(@Nonnull Long eventId) {
        this.eventId = eventId;
    }

    @Nonnull
    public Long getEventConsumerId() {
        return eventConsumerId;
    }

    public void setEventConsumerId(@Nonnull Long eventConsumerId) {
        this.eventConsumerId = eventConsumerId;
    }

    @Nonnull
    public EventConsumerType getEventConsumerType() {
        return eventConsumerType;
    }

    public void setEventConsumerType(@Nonnull EventConsumerType eventConsumerType) {
        this.eventConsumerType = eventConsumerType;
    }
}
