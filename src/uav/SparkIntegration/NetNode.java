package uav.SparkIntegration;

import java.util.List;

/**
 * Created by administrator on 05.06.18.
 */

public class NetNode {
    private Integer nodeId;
    private List<Integer> adjVertex;

    public NetNode(Integer nodeId_) { this.nodeId = nodeId_; }
    public void setNodeId(Integer nodeId_) { this.nodeId = nodeId_; }
    public Integer getNodeId() { return this.nodeId; }
    public void addAdjVertex(Integer adjVertexId) {
        this.adjVertex.add(adjVertexId);
    }
    public void setAdjVertex(List<Integer> adjVertex_) {
        if(!this.adjVertex.isEmpty()) adjVertex.clear();
        this.adjVertex.addAll(adjVertex_);
    }
    public List<Integer> getAdjVertex() {
        return this.adjVertex;
    }
}
