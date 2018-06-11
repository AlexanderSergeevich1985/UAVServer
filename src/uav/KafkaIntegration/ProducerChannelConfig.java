package uav.KafkaIntegration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.MessageHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@PropertySource("classpath:Hive.properties")
public class ProducerChannelConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    private static final int ratio = 1024;
    private static final int count = 12;
    private String batchSize = String.valueOf(count);
    private String bufMem = String.valueOf(ratio*count);
    private String clientID = String.valueOf(0);
    @Value("${spring.kafka.topic_name:dataMsg}")
    private String topic;

    @Bean
    public DirectChannel getDirectChannel() {
        return new DirectChannel();
    }
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaMsgKryoCoder.class);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, this.clientID); //"client.id"
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, this.batchSize); //size of sending message packet
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, this.bufMem);
        properties.put(ProducerConfig.RETRIES_CONFIG, 0 + "");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, String.valueOf(1));  //the time interval between finishing of sending packet and staring of assembling next packet
        return properties;
    }
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    @Bean
    @ServiceActivator(inputChannel = "producerChannel")
    public MessageHandler kafkaMessageHandler() {
        KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(kafkaTemplate());
        handler.setMessageKeyExpression(new LiteralExpression("kafka-integration"));
        return handler;
    }
}
