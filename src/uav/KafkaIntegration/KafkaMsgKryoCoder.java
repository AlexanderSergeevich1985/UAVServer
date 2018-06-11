package uav.KafkaIntegration;

import java.io.Serializable;
import java.util.Map;

public class KafkaMsgKryoCoder extends MsgKryoEncoderDecoder<KafkaMsg> implements Serializable {
    private String encoding = "UTF8";

    public KafkaMsgKryoCoder(){
        super(new KafkaMsgSerializer(), KafkaMsg.class);
    }
    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.serializer.encoding" : "value.serializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if(encodingValue == null) {
            encodingValue = configs.get("serializer.encoding");
        }
        if(encodingValue != null && encodingValue instanceof String) {
            this.encoding = (String)encodingValue;
        }
    }
    public byte[] serialize(String topic, KafkaMsg msg) {
        return msg == null ? null : this.toBytes(msg);
    }
    public void close() {
    }
}
