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

import java.util.List;

public class RelatedTransaction {
    private ObjectState watchedObjectState;
    private List<ObjectState> relatedStates;

    public ObjectState getWatchedObjectState() {
        return watchedObjectState;
    }

    public void setWatchedObjectState(ObjectState watchedObjectState) {
        this.watchedObjectState = watchedObjectState;
    }

    public List<ObjectState> getRelatedStates() {
        return relatedStates;
    }

    public void setRelatedStates(List<ObjectState> relatedStates) {
        this.relatedStates = relatedStates;
    }

    public boolean isValidRelatedStates(StateTransitionMap map) {
        StateDesc watchedStateDesc = new StateDesc(watchedObjectState.getTimestamp(), watchedObjectState.getObject().toString());
        for(ObjectState os: relatedStates) {
            Byte[] transVector = calcTransitionVector(watchedObjectState.getBytesImageHash(), os.getBytesImageHash());
            watchedStateDesc.timeTo = os.getTimestamp();
            watchedStateDesc.objectIdTo = os.getObject().toString();
            if(!map.isValidStateTransVector(watchedStateDesc, transVector)) return false;
        }
        return true;
    }

    static protected Byte[] calcTransitionVector(final byte[] first, final byte[] second) {
        Byte[] result = new Byte[first.length];
        for(int i = 0; i < first.length; ++i) {
            result[i] = (byte) (first[i] ^ second[i]);
        }
        return result;
    }
}
