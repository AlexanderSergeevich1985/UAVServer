package uav.Utils;

public class CompositeKey<T> {
    private T firstObject;
    private T secondObject;
    private int hashCode = -1;

    public CompositeKey(T firstObject, T secondObject) {
        setObjects(firstObject, secondObject);
    }

    public void setObjects(T firstObject, T secondObject) {
        if(firstObject.getClass().isPrimitive() || !(firstObject.getClass().equals(secondObject.getClass()))) return;
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }
    public T getFirstObject() {
        return this.firstObject;
    }
    public T getSecondObject() {
        return this.secondObject;
    }
    @Override
    public int hashCode() {
        if(hashCode == -1) hashCode = ((firstObject.hashCode()+1)*(secondObject.hashCode()+1))/(firstObject.hashCode()+secondObject.hashCode());
        return hashCode;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof CompositeKey)) return false;
        CompositeKey other = (CompositeKey)obj;
        if(this.firstObject == null || this.secondObject == null || other.firstObject == null || other.secondObject == null) return false;
        if(!(this.firstObject.equals(other.firstObject) && this.secondObject.equals(other.secondObject))) {
            if(!(this.firstObject.equals(other.secondObject) && this.secondObject.equals(other.firstObject))) return false;
        }
        return true;
    }
}