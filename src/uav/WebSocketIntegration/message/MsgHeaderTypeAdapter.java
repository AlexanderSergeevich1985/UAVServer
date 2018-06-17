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
