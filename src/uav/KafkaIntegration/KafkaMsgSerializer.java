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
package uav.KafkaIntegration;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KafkaMsgSerializer extends com.esotericsoftware.kryo.Serializer<KafkaMsg> {
    private static Logger logger = Logger.getLogger(KafkaMsgSerializer.class.getName());
    private KafkaMsgBuilder kafkaMsgBuilder = new KafkaMsgBuilder();

    public void write(Kryo kryo, Output output, KafkaMsg kafkaMsg) {
        try {
            kafkaMsgBuilder.setMsg(kafkaMsg);
            if(kafkaMsgBuilder.hasNext()) {
                Field filedClass = kafkaMsgBuilder.getField();
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
                Field filedClass = kafkaMsgBuilder.getField();
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
