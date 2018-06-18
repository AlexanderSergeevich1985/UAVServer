package uav.WebSocketIntegration.message;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgBodyTypeAdapter extends TypeAdapter<MsgBody> {
    private static Logger logger = Logger.getLogger(MsgBodyTypeAdapter.class.getName());

    @Override
    public void write(JsonWriter out, MsgBody msg) throws IOException {
        if(msg == null) return;
        out.beginObject();
        msg.getFields().forEach((key, field) -> {
            try {
                Object value = field.getValue();
                StringBuilder name = new StringBuilder(key);
                name.append("_");
                name.append(field.getType());
                if (value instanceof String) {
                    out.name(name.toString()).value((String) value);
                } else if (value instanceof Number) {
                    out.name(name.toString()).value((Number) value);
                } else if (value instanceof Boolean) {
                    out.name(name.toString()).value((Boolean) value);
                } else if (value instanceof Character) {
                    out.name(name.toString()).value((Character) value);
                } else {
                    out.name(name.toString()).value(value.toString());
                }
            }
            catch(IOException ex) {
                if(logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, "Exception occur : ", ex);
                }
            }
        });
        out.endObject();
    }
    @Override
    public MsgBody read(JsonReader in) throws IOException {
        MsgBody msg = new MsgBody();
        in.beginObject();
        while(in.hasNext()) {
            final String[] info = in.nextName().split("_");
            switch(info[1]) {
                case "Double": {
                    msg.addField(msg.new MsgBodyField<>(info[0], info[1], in.nextDouble(), null));
                    break;
                }
                case "Integer": {
                    msg.addField(msg.new MsgBodyField<>(info[0], info[1], in.nextInt(), null));
                    break;
                }
                case "Long": {
                    msg.addField(msg.new MsgBodyField<>(info[0], info[1], in.nextLong(), null));
                    break;
                }
                case "Boolean": {
                    msg.addField(msg.new MsgBodyField<>(info[0], info[1], in.nextBoolean(), null));
                    break;
                }
                case "String": {
                    msg.addField(msg.new MsgBodyField<>(info[0], info[1], in.nextString(), null));
                    break;
                }
                default: {
                    in.skipValue();
                    break;
                }
            }
        }
        in.endObject();
        return msg;
    }
}
