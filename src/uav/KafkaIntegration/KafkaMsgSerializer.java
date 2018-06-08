package uav.KafkaIntegration;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by administrator on 08.06.18.
 */
public class KafkaMsgSerializer extends com.esotericsoftware.kryo.Serializer<KafkaMsg> {
    private static Logger logger = Logger.getLogger(KafkaMsgSerializer.class.getName());
    private KafkaMsgBuilder kafkaMsgBuilder = new KafkaMsgBuilder();

    public void write(Kryo kryo, Output output, KafkaMsg kafkaMsg) {
        try {
            kafkaMsgBuilder.setMsg(kafkaMsg);
            if(kafkaMsgBuilder.hasNext()) {
                Field filedClass = kafkaMsgBuilder.getFiled();
                if(filedClass.getClass().isInstance(Boolean.TYPE)) {
                    output.writeBoolean(kafkaMsgBuilder.getNext());
                }
                else if(filedClass.getClass().isInstance(Short.TYPE)) {
                    output.writeShort(kafkaMsgBuilder.getNext());
                }
                else if(filedClass.getClass().isInstance(Integer.TYPE)) {
                    output.writeInt(kafkaMsgBuilder.getNext());
                }
                else if(filedClass.getClass().isInstance(Long.TYPE)) {
                    output.writeLong(kafkaMsgBuilder.getNext());
                }
                else if(filedClass.getClass().isInstance(Float.TYPE)) {
                    output.writeFloat(kafkaMsgBuilder.getNext());
                }
                else if(filedClass.getClass().isInstance(Double.TYPE)) {
                    output.writeDouble(kafkaMsgBuilder.getNext());
                }
                else if(filedClass.getClass().isInstance("")) {
                    output.writeString(kafkaMsgBuilder.getNext());
                }
                else {
                    kafkaMsgBuilder.skipNext();
                }
            }
        }
        catch(Exception ex) {
            if(logger.isLoggable(Level.ALL)) {
                logger.log(Level.INFO, "Exception occur : ", ex);
            }
        }
    }
    public KafkaMsg read(Kryo kryo, Input input, Class<KafkaMsg> msgClass) {
        KafkaMsg kafkaMsg = null;
        try {
            kafkaMsg = msgClass.newInstance();
            kafkaMsgBuilder.setMsg(kafkaMsg);
            if(kafkaMsgBuilder.hasNext() && input.available() > 0) {
                Field filedClass = kafkaMsgBuilder.getFiled();
                if(filedClass.getClass().isInstance(Boolean.TYPE)) {
                    kafkaMsgBuilder.setFieldValue(input.readBoolean());
                }
                else if(filedClass.getClass().isInstance(Short.TYPE)) {
                    kafkaMsgBuilder.setFieldValue(input.readShort());
                }
                else if(filedClass.getClass().isInstance(Integer.TYPE)) {
                    kafkaMsgBuilder.setFieldValue(input.readInt());
                }
                else if(filedClass.getClass().isInstance(Long.TYPE)) {
                    kafkaMsgBuilder.setFieldValue(input.readLong());
                }
                else if(filedClass.getClass().isInstance(Float.TYPE)) {
                    kafkaMsgBuilder.setFieldValue(input.readFloat());
                }
                else if(filedClass.getClass().isInstance(Double.TYPE)) {
                    kafkaMsgBuilder.setFieldValue(input.readDouble());
                }
                else if(filedClass.getClass().isInstance("")) {
                    kafkaMsgBuilder.setFieldValue(input.readString());
                }
                else {
                    kafkaMsgBuilder.skipNext();
                }
            }
        }
        catch(Exception ex) {
            if(logger.isLoggable(Level.ALL)) {
                logger.log(Level.INFO, "Exception occur : ", ex);
            }
        }
        return kafkaMsg;
    }
}
