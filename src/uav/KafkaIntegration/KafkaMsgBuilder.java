package uav.KafkaIntegration;

import java.lang.reflect.Field;

/**
 * Created by administrator on 08.06.18.
 */
public class KafkaMsgBuilder {
    private KafkaMsg kafkaMsg;
    private Field[] fields;
    private int counter;

    public void setMsg(KafkaMsg msg) {
        kafkaMsg = msg;
        fields = kafkaMsg.getClass().getFields();
        counter = fields.length;
    }
    public boolean hasNext() {
        return counter < fields.length ? true : false;
    }
    public Field getFiled() {
        if(!fields[counter].isAccessible()) fields[counter].setAccessible(true);
        return fields[counter];
    }
    public String getFiledName() {
        if(!fields[counter].isAccessible()) fields[counter].setAccessible(true);
        return fields[counter].getName();
    }
    public boolean skipNext() {
        ++counter;
        return hasNext();
    }
    public <T> T getNext() {
        T field = null;
        try {
            if(!fields[counter].isAccessible()) fields[counter].setAccessible(true);
            Object obj = fields[counter].getClass().newInstance();
            field = (T) fields[counter].get(obj);
            ++counter;
        }
        catch(SecurityException ex) {
            System.out.println(ex);
        }
        catch(IllegalAccessException ex) {
            System.out.println(ex);
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
        return field;
    }
    public <T> void setFieldValue(T fieldValue) {
        try {
            if(!fields[counter].isAccessible()) fields[counter].setAccessible(true);
            fields[counter].set(fieldValue, fieldValue);
            ++counter;
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
