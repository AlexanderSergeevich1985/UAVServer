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
package uav.Common.ObjectState;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

class StateDesc {
    public Timestamp timeFrom;
    public String objectIdFrom;

    public Timestamp timeTo;
    public String objectIdTo;

    public StateDesc(Timestamp timeFrom, String objectIdFrom) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.objectIdFrom = objectIdFrom;
        this.objectIdTo = objectIdTo;
    }

    public StateDesc(Timestamp timeFrom, Timestamp timeTo, String objectIdFrom, String objectIdTo) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.objectIdFrom = objectIdFrom;
        this.objectIdTo = objectIdTo;
    }
}

public class StateTransitionMap {
    private Map<StateDesc, Byte[]> stateMap;

    public boolean isValidStateTransVector(StateDesc desc, Byte[] checkedTransitionVector) {
        if(!stateMap.containsKey(desc)) return false;
        return Arrays.equals(stateMap.get(desc), checkedTransitionVector);
    }
    public Map<StateDesc, Byte[]> getStateMap() {
        return stateMap;
    }

    public void setStateMap(Map<StateDesc, Byte[]> stateMap) {
        this.stateMap = stateMap;
    }

    public void addState(StateDesc desc, Byte[] transitionVector) {
        this.stateMap.put(desc, transitionVector);
    }

    public Byte[] getStateByDesc(StateDesc desc) {
        return stateMap.get(desc);
    }
}
