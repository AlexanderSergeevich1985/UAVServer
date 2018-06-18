package uav.WebSocketIntegration.message;

import java.io.Serializable;
import java.util.HashMap;

public class MsgBody implements Serializable {
    private MsgBodyField first_field = null;
    private MsgBodyField end_field = null;
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
