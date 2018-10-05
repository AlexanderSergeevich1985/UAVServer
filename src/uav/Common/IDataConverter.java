package uav.Common;

public interface IDataConverter {
    boolean hasNextField();
    String getNextFieldName();
    Object getNextFieldAttribute();
    void setParsedObject(Object object);
}
