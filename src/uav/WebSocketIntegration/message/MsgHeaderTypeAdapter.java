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

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class MsgHeaderTypeAdapter extends TypeAdapter<MsgHeader> {
    @Override
    public void write(JsonWriter out, MsgHeader msg) throws IOException {
        if(msg == null) return;
        out.beginObject();
        out.name("messageId").value(msg.getMessageId());
        out.name("messageType").value(msg.getMessageType());
        out.name("timestamp").value(msg.getTimeStamp());
        out.name("bodySize").value(msg.getBodySize());
        out.name("deviceUID").value(msg.getDeviceUID());
        out.endObject();
    }
    @Override
    public MsgHeader read(JsonReader in) throws IOException {
        MsgHeader msg = new MsgHeader();
        in.beginObject();
        while(in.hasNext()) {
            switch(in.nextName()) {
                case "messageId":
                    msg.setMessageId(in.nextString());
                    break;
                case "messageType":
                    msg.setMessageType(in.nextString());
                    break;
                case "timestamp":
                    msg.setTimeStamp(in.nextString());
                    break;
                case "bodySize":
                    msg.setBodySize(Long.getLong(in.nextString()));
                    break;
                case "deviceUID":
                    msg.setDeviceUID(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return msg;
    }
}
