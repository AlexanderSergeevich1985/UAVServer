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

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDataConverter<T> implements IDataConverter {
    private static final Logger logger = Logger.getLogger(DataModel.class.getName());

    private Field[] fieldsSource;
    private int counter;
    private Object objectSource;
    private Object objectDestination;

    @Override
    public boolean hasNextField() {
        return counter < fieldsSource.length ? true : false;
    }

    @Override
    public String getNextFieldName() {
        if(!fieldsSource[counter].isAccessible()) fieldsSource[counter].setAccessible(true);
        return fieldsSource[counter].getName();
    }

    @Override
    public Object getNextFieldAttribute() {
        if(!fieldsSource[counter].isAccessible()) fieldsSource[counter].setAccessible(true);
        try {
            return fieldsSource[counter].get(objectSource);
        }
        catch(IllegalAccessException iae) {
            if(logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "ClassCastException occur: ", iae);
            }
        }
        return null;
    }

    @Override
    public void setParsedObject(Object object) {
        if(object == null) return;
        this.fieldsSource = object.getClass().getFields();
        this.objectSource = object;
    }

    @Override
    public Object getParsedObject() {
        return this.objectSource;
    }

    @Override
    public <T> T getParsedDataObject() {
        try {
            return (T)objectSource;
        }
        catch(ClassCastException cce) {
            if(logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "ClassCastException occur: ", cce);
            }
        }
        return null;
    }

    public void setObjectDestination(Object object) {
        if(object == null) return;
        this.objectDestination = objectDestination;
    }

    @Override
    public int isExistFieldWithName(String fieldId) {
        int index = 0;
        for(Field field: fieldsSource) {
            if(field.getName().equals(fieldId)) return index;
            ++index;
        }
        index = -1;
        return index;
    }
}
