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
