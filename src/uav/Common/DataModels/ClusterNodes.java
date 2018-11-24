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
@Table(name = "CLUSTER_NODES")
public class ClusterNodes extends BaseEntity {
    public static final int NODE_NAME_MAX_LENGTH = 100;
    public static final int OS_NAME_MAX_LENGTH = 100;

    @Column(name = "node_name", nullable = true, length = NODE_NAME_MAX_LENGTH)
    private String nodeName;

    @Column(name = "ip_adress", nullable = false, length = 45)
    private String ip;

    @Column(name = "os_name", nullable = true, length = OS_NAME_MAX_LENGTH)
    private String osName;

    @Column(name = "node_statistics_id", nullable = true)
    private Long nodeStatId;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = ProcessingOperation.SUPPORT_NODES)
    private List<ClusterNodes> supportNodes = new ArrayList<>();

    protected ClusterNodes() {}

    @Nullable
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(@Nullable String nodeName) {
        this.nodeName = nodeName;
    }

    @Nonnull
    public String getIp() {
        return ip;
    }

    public void setIp(@Nonnull String ip) {
        this.ip = ip;
    }

    @Nullable
    public String getOsName() {
        return osName;
    }

    public void setOsName(@Nullable String osName) {
        this.osName = osName;
    }

    @Nullable
    public Long getNodeStatId() {
        return nodeStatId;
    }

    public void setNodeStatId(@Nullable Long nodeStatId) {
        this.nodeStatId = nodeStatId;
    }

    @Nullable
    public List<ClusterNodes> getSupportNodes() {
        return supportNodes;
    }

    public void setSupportNodes(@Nullable List<ClusterNodes> supportNodes) {
        this.supportNodes = supportNodes;
    }
}
