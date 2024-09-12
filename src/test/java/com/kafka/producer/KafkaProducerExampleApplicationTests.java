package com.kafka.producer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.awaitility.*;
import org.awaitility.core.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.awaitility.constraint.*;
import com.kafka.dto.Customer;
import com.kafka.producer.service.KafkaMessagePublisher;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = {"com.kafka.producer","com.kafka.dto"})
@Testcontainers
class KafkaProducerExampleApplicationTests {

//	@Test
//	void contextLoads() {
//	}
	@Container
	static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

	@DynamicPropertySource
	public static void bootstrapIntKafkaProperties(DynamicPropertyRegistry registry)
	{
		registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
	}
	@Autowired
	private KafkaMessagePublisher publisher;
	@Test
	public void testSendEventMessageToTopic()
	{
		//publisher.sendEventsMessageToTopic(new Customer(1,"roopa sri","roopa.sri@gmail.com","Female",26,809768909, "84.9.96.253"));
		Awaitility.await()
        .pollInterval(Duration.ofSeconds(3))
        .atMost(10,TimeUnit.SECONDS)
        //.atMost(Duration.ofSeconds(10))
        .untilAsserted(() -> {

            // assertTrue(messageWasReceived(), "Message was not received within the timeout");
        });
	}
}
