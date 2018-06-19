package uav.WebSocketIntegration.message;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MsgIOTDeviceJsonDeserializer implements JsonDeserializer<MsgIOTDevice> {
    @Override
    public MsgIOTDevice deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        MsgIOTDevice msg = new MsgIOTDevice();
        JsonObject jsonObject = json.getAsJsonObject();
        if(jsonObject.isJsonNull() || !jsonObject.isJsonObject()) return msg;
        if(jsonObject.has("Header")) msg.setHeader((MsgHeader)context.deserialize(jsonObject.get("Header"), MsgHeader.class));
        if(jsonObject.has("Body")) msg.setBody((MsgBody)context.deserialize(jsonObject.get("Body"), MsgBody.class));
        return msg;
    }
}
