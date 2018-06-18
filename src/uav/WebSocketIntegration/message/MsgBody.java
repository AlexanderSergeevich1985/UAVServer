package uav.WebSocketIntegration.message;

import java.io.Serializable;
import java.util.HashMap;

public class MsgBody implements Serializable {
    private int counter = 0;
    private MsgBodyField field;
    private transient HashMap<String, MsgBodyField> fields;

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
