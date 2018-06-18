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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MsgBodyJsonSerializer implements JsonSerializer<MsgBody> {
    @Override
    public JsonElement serialize(MsgBody msg, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        msg.getFields().forEach((key, field) -> {
            Object value = field.getValue();
            StringBuilder name = new StringBuilder(key);
            name.append("_");
            name.append(field.getType());
            if(value instanceof String) {
                result.addProperty(name.toString(), (String) value);
            }
            else if(value instanceof Number) {
                result.addProperty(name.toString(), (Number) value);
            }
            else if(value instanceof Boolean) {
                result.addProperty(name.toString(), (Boolean) value);
            }
            else if(value instanceof Character) {
                result.addProperty(name.toString(), (Character) value);
            }
            else {
                result.addProperty(name.toString(), value.toString());
            }
        });
        return result;
    }
}
