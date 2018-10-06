package uav.Common;

public interface IDataConverter {
    boolean hasNextField();
    String getNextFieldName();
    Object getNextFieldAttribute();
    void setParsedObject(Object object);
    Object getParsedObject();
    <T> T getParsedDataObject();
    boolean isExistFieldWithName(String fieldId);
    void setObjectField(String fieldId, Object fieldValue);
}
