
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

import java.io.Serializable;
import java.util.List;

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
        hash = Math.abs(firstNodeId + secondNodeId);
        int counter = 10;
        int module = hash+1000;
        //Linear congruential generator
        while(counter > 0) {
            hash = (125*hash + 345)%module;
            --counter;
        }
        return hash;
    }
}
