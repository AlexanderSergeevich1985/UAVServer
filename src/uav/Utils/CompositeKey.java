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
