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
package uav.KafkaIntegration;

import java.lang.reflect.Field;

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
    public Field getField() {
        if(!fields[counter].isAccessible()) fields[counter].setAccessible(true);
        return fields[counter];
    }
    public String getFieldName() {
        if(!fields[counter].isAccessible()) fields[counter].setAccessible(true);
        return fields[counter].getName();
    }
    public boolean skipNext() {
        ++counter;
        return hasNext();
    }
    public <T> T getNext() {
        T fieldValue = null;
        try {
            if(!fields[counter].isAccessible()) fields[counter].setAccessible(true);
            fieldValue = (T) fields[counter].get(kafkaMsg);
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
        return fieldValue;
    }
    public <T> void setFieldValue(T fieldValue) {
        try {
            if(!fields[counter].isAccessible()) fields[counter].setAccessible(true);
            fields[counter].set(kafkaMsg, fieldValue);
            ++counter;
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
