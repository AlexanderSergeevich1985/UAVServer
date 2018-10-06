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
package uav.Common;

import uav.Utils.DataScheme;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataModel<T> {
    private static final Logger logger = Logger.getLogger(DataModel.class.getName());

    private DataScheme attributes;
    private T object;
    private Class<T> type;

    public void setAttributes(DataScheme attributes) {
        this.attributes = attributes;
    }

    public DataScheme getAttributes() {
        return attributes;
    }

    public boolean setObjectWithCheck(Object object) {
        if(!type.isInstance(object)) return false;
        try {
            this.object = (T)object;
        }
        catch(ClassCastException cce) {
            if(logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "ClassCastException occur: ", cce);
            }
        }
        return true;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public <TA> TA getAttributeValue(String attrId) {
        if(!attributes.getDataScheme().containsKey(attrId)) return null;
        TA attributeValue = null;
        try {
            attributeValue = (TA)attributes.getDataScheme().get(attrId);
        }
        catch(ClassCastException cce) {
            if(logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "ClassCastException occur: ", cce);
            }
        }
        return attributeValue;
    }

    public boolean convertObjectToDM(IDataConverter converter) {
        if(attributes == null || object == null) return false;
        converter.setParsedObject(this.object);
        while(converter.hasNextField()) {
            attributes.setObjectKey(converter.getNextFieldName(), converter.getNextFieldAttribute());
        }
        return true;
    }

    public <T> boolean convertDMToObject(IDataConverter converter) {
        if(attributes == null || object == null) return false;
        Map<String, Object> dataModel = attributes.getDataScheme();
        dataModel.forEach((key, value) -> {
            if(converter.isExistFieldWithName(key) != -1) converter.setObjectField(key, value);
        });
        return true;
    }
}
