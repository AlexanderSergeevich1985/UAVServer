package uav.KafkaIntegration;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KafkaMsgEncoderDecoder<T> implements Encoder<T>, Decoder<T> {
    private final Kryo kryo;
    private Class<T> msg;

    public KafkaMsgEncoderDecoder(Serializer<T> serializer, Class<T> msg) {
        this.kryo = new Kryo();
        this.kryo.register(msg, serializer);
        this.msg = msg;
    }
    @Override
    public byte[] toBytes(T datapoint) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Output output = new Output(stream);
        kryo.writeObject(output, datapoint);
        output.close();
        return stream.toByteArray();
    }
    @Override
    public T fromBytes(byte[] bytes) {
        return kryo.readObject(new Input(new ByteArrayInputStream(bytes)), msg);
    }
}
