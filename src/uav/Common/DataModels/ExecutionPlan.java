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
@Table(name = "EXECUTION_PLAN")
public class ExecutionPlan extends BaseEntity {
    public static final String PROCESS_EVENTS = "processEvents";

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="execution_plan_id", referencedColumnName="id", nullable = false)
    private List<ProcessEvent> processEvents;

    protected ExecutionPlan() {}

    @Nonnull
    public List<ProcessEvent> getProcessEvents() {
        return processEvents;
    }

    public void setProcessEvents(@Nonnull List<ProcessEvent> processEvents) {
        this.processEvents = processEvents;
    }
}
