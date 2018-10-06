package uav.Common.ObjectState;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

class StateDesc {
    public Timestamp timestamp;
    public String objectId;
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
