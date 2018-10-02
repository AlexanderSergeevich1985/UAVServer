package TestTask;

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
