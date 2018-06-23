package uav.JMSIntegration;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class Document implements Serializable {
    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:dd z", Locale.ENGLISH);

    private String class_name;
    private String creation_time;
    private String last_modification_time;
    private Conditions conditions;

    Document() {
        this.creation_time = LocalDateTime.now().format(formatter);
    }
    Document(String class_name) {
        this.class_name = class_name;
        this.creation_time = LocalDateTime.now().format(formatter);
    }
    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
    public String getClass_name() {
        return this.class_name;
    }
    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }
    public Conditions getConditions() {
        return this.conditions;
    }
    public void setLast_modification_time(LocalDateTime last_modification_time) {
        this.last_modification_time = last_modification_time.format(formatter);
    }
    public String getLast_modification_time() {
        return this.last_modification_time;
    }

    protected class Conditions implements Serializable {
        private transient Map<String, Object> conditions = new HashMap<>();

        public Boolean isSatisfied() {
            return true;
        }
    }
}
