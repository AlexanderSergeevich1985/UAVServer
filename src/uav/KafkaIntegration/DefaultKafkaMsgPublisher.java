package uav.KafkaIntegration;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DefaultKafkaMsgPublisher {
    private long nextBookId = 0;
    private final static String nameStr = "MessageId_%.";
    Random rand = new Random();

    KafkaMsg getNextMsg() {
        KafkaMsg msg = new DefaultKafkaMsg(String.format(nameStr, nextBookId++), rand.nextDouble());
        return msg;
    }
}
