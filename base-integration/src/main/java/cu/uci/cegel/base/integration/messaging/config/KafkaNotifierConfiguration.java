package cu.uci.cegel.base.integration.messaging.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"cu.uci.cegel.base.integration.messaging"})
public class KafkaNotifierConfiguration {
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KafkaConfig.IP_SERVER.getDescripcion());
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        configProps.put(
                ProducerConfig.MAX_BLOCK_MS_CONFIG,
                2000);
        configProps.put(
                ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
                2000);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        KafkaTemplate<String, Object> defaultEventKafkaTemplate = new KafkaTemplate<>(producerFactory);
        defaultEventKafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return defaultEventKafkaTemplate;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KafkaConfig.IP_SERVER.getDescripcion());
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                KafkaConfig.TOPIC_TRAZA.getDescripcion());
        props.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

}
