
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
package uav.SparkIntegration;

import java.util.List;
import java.util.LinkedList;
import java.io.Serializable;

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
