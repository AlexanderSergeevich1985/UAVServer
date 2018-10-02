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

import java.util.Map;

public class DataScheme {
    private Map<String, Object> dataScheme;

    public void setDataScheme(Map<String, Object> dataScheme) {
        this.dataScheme = dataScheme;
    }

    public Map<String, Object> getDataScheme() {
        return dataScheme;
    }

    public void setObjectKey(String key, Object object) {
        dataScheme.put(key, object);
    }

    public Object getObjectByKey(String key) {
        return dataScheme.containsKey(key) ? dataScheme.get(key) : null;
    }

    public <T extends Object> T getDataByKey(String key) {
        return dataScheme.containsKey(key) ? (T)dataScheme.get(key) : null;
    }
}
