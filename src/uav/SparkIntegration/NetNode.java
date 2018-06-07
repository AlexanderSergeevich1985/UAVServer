package uav.SparkIntegration;

import java.util.List;
import java.util.LinkedList;
import java.io.Serializable;

/**
 * Created by administrator on 05.06.18.
 */

public class NetNode implements Serializable {
    private Integer nodeId;
    private List<Integer> adjVertex = new LinkedList<>();
    private List<Double> adjVertexDist = new LinkedList<>();

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
    public void setAdjVertexDist(List<Double> adjVertexDist_) {
        if(!this.adjVertex.isEmpty()) adjVertex.clear();
        this.adjVertexDist.addAll(adjVertexDist_);
    }
    public List<Double> getAdjVertexDist() {
        return this.adjVertexDist;
    }
    public void setVertex(Integer nodeId, Double distance) {
        adjVertex.add(nodeId);
        adjVertexDist.add(distance);
    }
    @Override
    public int hashCode() {
        return nodeId;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        else if(obj == null || !(obj instanceof NetEdge))
            return false;
        NetNode other = (NetNode) obj;
        if(nodeId == null || other.nodeId == null) {
            return false;
        }
        else if(!(nodeId.equals(other.nodeId))) return false;
        if(adjVertex == null || other.adjVertex == null || adjVertexDist == null || other.adjVertexDist == null) {
            return false;
        }
        else if(!adjVertex.equals(other.adjVertex))
            return false;
        return true;
    }
}
