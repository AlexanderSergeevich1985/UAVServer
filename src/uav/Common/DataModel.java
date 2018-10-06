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
            if(converter.isExistFieldWithName(key)) converter.setObjectField(key, value);
        });
        return true;
    }
}
