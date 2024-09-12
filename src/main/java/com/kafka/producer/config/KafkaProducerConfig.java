package com.kafka.producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

//import com.kafka.consumer.config.DeadLetterPublishingRecoverer;
import com.kafka.dto.Customer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaProducerConfig {
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000); // Increase timeout
        configs.put(AdminClientConfig.RETRY_BACKOFF_MS_CONFIG, 500);
        return new KafkaAdmin(configs);
    }
	@Value("${app.topic.name}")
	private String topicName;
	@Bean
public NewTopic createTopic()
{
	return new NewTopic(topicName, 5,(short)1);
}
	@Bean
	public NewTopic createTopic1()
	{
		return new NewTopic("New-Learn-Topic-Partitions", 5,(short)1);
	}


//	@Bean
//	public ProducerFactory<String, Customer> producerFactory() {
//	    Map<String, Object> configProps = new HashMap<>();
//	    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//	    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//	    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Use JsonSerializer for JSON conversion
//	    return new DefaultKafkaProducerFactory<>(configProps);
//	}
//
//	@Bean
//	public KafkaTemplate<String, Customer> kafkaTemplate() {
//	    return new KafkaTemplate<>(producerFactory());
//	}
//
//	@Bean
//	public DefaultErrorHandler errorHandler(KafkaTemplate<String, Customer> kafkaTemplate)  {
//	    DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
//	        (r, e) -> new TopicPartition("kafka-error-handling-eg-new-again-refreshidle-new-list-retry-0", r.partition() % 5));
//	    return new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 3));
//	}


	
//    @Bean
//    public Map<String, Object> mapFactoryConsumerConfig() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        //configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        //return new DefaultKafkaProducerFactory<>(configProps);
//       // configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
//        return configProps;
//    }
//    
//    @Bean
//   public ProducerFactory<String, Object> producerFactory() {
//    //public ProducerFactory<String, Customer> producerFactory() {
//        // Use JsonSerializer for Customer class
//        return new DefaultKafkaProducerFactory<>(mapFactoryConsumerConfig());//, new StringSerializer(), new JsonSerializer<>());
//    }
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//    //public KafkaTemplate<String, Customer> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
}
