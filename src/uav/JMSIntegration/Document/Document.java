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
package uav.JMSIntegration.Document;

import java.io.Serializable;
import java.time.ZonedDateTime;
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
        this.creation_time = ZonedDateTime.now().format(formatter);
    }
    Document(String class_name) {
        this.class_name = class_name;
        this.creation_time = ZonedDateTime.now().format(formatter);
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
    public void setLast_modification_time(ZonedDateTime last_modification_time) {
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
