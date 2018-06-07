package uav.SparkIntegration;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administrator on 06.06.18.
 */

public class NetEdge implements Serializable {
    private Integer firstNodeId;
    private Integer secondNodeId;
    private Double distance;
    private String state;

    public NetEdge(Integer firstNodeId_, Integer secondNodeId_, Double distance_) {
        this.firstNodeId = firstNodeId_;
        this.secondNodeId = secondNodeId_;
        this.distance = distance_;
        this.state = "Undefined";
    }
    public void setFirstNodeId(Integer firstNodeId_) { this.firstNodeId = firstNodeId_; }
    public Integer getFirstNodeId() { return this.firstNodeId; }
    public void setSecondNodeId(Integer secondNodeId_) { this.secondNodeId = secondNodeId_; }
    public Integer getSecondNodeId() { return this.secondNodeId; }
    public void setDistance(Double distance_) { this.distance = distance_; }
    public Double getDistance() { return this.distance; }
    public void setState(String state_) { this.state = state_; }
    public String getState() { return this.state; }
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        else if(obj == null || !(obj instanceof NetEdge))
            return false;
        NetEdge other = (NetEdge) obj;
        if(firstNodeId == null || other.firstNodeId == null || secondNodeId == null || other.secondNodeId == null) {
            return false;
        }
        else if(!(firstNodeId.equals(other.firstNodeId) && secondNodeId.equals(other.secondNodeId))) {
            if(!(firstNodeId.equals(other.secondNodeId) && secondNodeId.equals(other.firstNodeId))) {
                return false;
            }
        }
        if(distance == null || other.distance == null) {
            return false;
        }
        else if(!distance.equals(other.distance))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        Integer hash = 0;
        hash = firstNodeId + secondNodeId;
        int counter = 10;
        while(counter > 0) {
            hash = (125*hash + 345)%1000;
            --counter;
        }
        return hash;
    }
}
