package uav.WebSocketIntegration.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MsgBodyJsonSerializer implements JsonSerializer<MsgBody> {
    @Override
    public JsonElement serialize(MsgBody msg, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        msg.getFields().forEach((key, field) -> {
            Object value = field.getValue();
            if(value instanceof String) {
                result.addProperty(key, (String) value);
            }
            else if(value instanceof Number) {
                result.addProperty(key, (Number) value);
            }
            else if(value instanceof Boolean) {
                result.addProperty(key, (Boolean) value);
            }
            else if(value instanceof Character) {
                result.addProperty(key, (Character) value);
            }
            else {
                result.addProperty(key, value.toString());
            }
        });
        return result;
    }
}
