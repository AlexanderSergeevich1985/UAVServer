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
