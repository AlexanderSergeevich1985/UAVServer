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
