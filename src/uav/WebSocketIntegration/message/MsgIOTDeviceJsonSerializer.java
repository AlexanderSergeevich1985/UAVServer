package uav.WebSocketIntegration.message;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MsgIOTDeviceJsonSerializer implements JsonSerializer<MsgIOTDevice> {
    @Override
    public JsonElement serialize(MsgIOTDevice msg, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        if(msg == null) return result;
        MsgHeader header = msg.getHeader();
        if(header != null) {
            Type typetoken = new TypeToken<MsgHeader>(){}.getType();
            result.add("Header", context.serialize(header, typetoken));
        }
        MsgBody body = msg.getBody();
        if(body != null) {
            Type typetoken = new TypeToken<MsgBody>(){}.getType();
            result.add("Body", context.serialize(body, typetoken));
        }
        return result;
    }
}
