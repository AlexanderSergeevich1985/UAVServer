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

import uav.Common.DataModelTypes.EventGeneratorType;

import javax.annotation.Nonnull;
import javax.persistence.*;

@Entity
@Table(name = "REL_EVENT_GENERATOR")
public class EventGenerator extends BaseEntity {
    public static final String EVENT_ID = "eventId";
    public static final String EVENT_GENERATOR_ID = "eventGeneratorId";
    public static final String EVENT_GENERATOR_TYPE = "eventGeneratorType";

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "event_generator_id", nullable = false)
    private Long eventGeneratorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "generator_type", nullable = false)
    private EventGeneratorType eventGeneratorType;

    @Nonnull
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(@Nonnull Long eventId) {
        this.eventId = eventId;
    }

    @Nonnull
    public Long getEventGeneratorId() {
        return eventGeneratorId;
    }

    public void setEventGeneratorId(@Nonnull Long eventGeneratorId) {
        this.eventGeneratorId = eventGeneratorId;
    }

    @Nonnull
    public EventGeneratorType getEventGeneratorType() {
        return eventGeneratorType;
    }

    public void setEventGeneratorType(@Nonnull EventGeneratorType eventGeneratorType) {
        this.eventGeneratorType = eventGeneratorType;
    }
}
