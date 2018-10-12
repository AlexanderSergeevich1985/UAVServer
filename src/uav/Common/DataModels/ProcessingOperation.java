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
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "REG_PROCESSING_OPERATIONS")
public class ProcessingOperation {
    public static final int OPERATION_DESCRIPTION_MAX_LENGTH = 2000;

    public static final String OPERATION_NAME = "operationName";
    public static final String OPERATION_STATISTICS_ID = "operationStatId";
    public static final String OPERATION_DESCRIPTION = "operationDescription";
    public static final String SUPPORT_NODES = "supportNodes";

    @Column(name = "operation_name", nullable = false)
    private String operationName;

    @Column(name = "operation_statistics_id", nullable = true)
    private Long operationStatId;

    @Column(name = "operation_description", nullable = true, length = OPERATION_DESCRIPTION_MAX_LENGTH)
    private String operationDescription;

    @ManyToMany
    @JoinTable(name = "REL_NODES_OPERATIONS",
            joinColumns = {@JoinColumn(name = "operation_id", referencedColumnName="id", nullable = true)},
            inverseJoinColumns = {@JoinColumn(name = "node_id", referencedColumnName="id", nullable = true)})
    private List<ClusterNodes> supportNodes = new ArrayList<>();

    @Nonnull
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(@Nonnull String operationName) {
        this.operationName = operationName;
    }

    @Nullable
    public Long getOperationStatId() {
        return operationStatId;
    }

    public void setOperationStatId(@Nullable Long operationStatId) {
        this.operationStatId = operationStatId;
    }

    @Nullable
    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(@Nullable String operationDescription) {
        this.operationDescription = operationDescription;
    }

    @Nullable
    public List<ClusterNodes> getSupportNodes() {
        return supportNodes;
    }

    public void setSupportNodes(@Nullable List<ClusterNodes> supportNodes) {
        this.supportNodes = supportNodes;
    }
}
