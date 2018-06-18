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
package uav.WebSocketIntegration.message;

import java.io.Serializable;
import java.util.HashMap;

public class MsgBody implements Serializable {
    private MsgBodyField first_field = null;
    private transient MsgBodyField end_field = null;
    private transient HashMap<String, MsgBodyField> fields = new HashMap<>();
    
    public void addField(MsgBodyField field) {
        if(first_field == null) {
            first_field = field;
            end_field = field;
            fields.put(field.getName(), field);
            return;
        }
        fields.put(field.getName(), field);
        this.end_field.setNextField(field);
        this.end_field = field;
    }
    public HashMap<String, MsgBodyField> getFields() {
        return this.fields;
    }
    public MsgBodyField getFirst_field() {
        return this.first_field;
    }
    public MsgBodyField getEnd_field() {
        return this.end_field;
    }
    protected class MsgBodyField<T> implements Serializable {
        private String name;
        private String type;
        private T value;
        private MsgBodyField nextField;

        public MsgBodyField() {}
        public MsgBodyField(String name, String type, T value, MsgBodyField nextField) {
            this.name = name;
            this.type = type;
            this.value = value;
            this.nextField = nextField;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public void setType(String type) {
            this.type = type;
        }
        public  String getType() {
            return this.type;
        }
        public void setValue(T value) {
            this.value = value;
        }
        public T getValue() {
            return this.value;
        }
        public Boolean hasNext() {
            return (this.nextField != null) ? true : false;
        }
        public void setNextField(MsgBodyField nextField) {
            this.nextField = nextField;
        }
        public MsgBodyField getNextField() {
            return this.nextField;
        }
    }
}
