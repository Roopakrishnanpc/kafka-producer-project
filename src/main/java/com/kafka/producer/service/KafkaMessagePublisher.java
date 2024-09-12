package com.kafka.producer.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.kafka.dto.Customer;

@Service
public class KafkaMessagePublisher {
	
	@Value("${app.topic.name}")
	private String topicName;
	
	@Autowired
private KafkaTemplate<String, Object> kafkaTemplate;
public void sendMessageToTopic(String message)
{
	CompletableFuture<SendResult<String, Object>> completableFuture=kafkaTemplate.send("Java-New-Next-topiec", message);

	//CompletableFuture<SendResult<String, Object>> completableFuture=kafkaTemplate.send("Learning-Topic", message);
	//completableFuture.get(); but this method will slow down, kafka is asyncornoys so immediately we have to send this will slow down
	completableFuture.whenComplete((result,exception)-> {
		//result.getRecordMetadata().partition();
		if(exception==null)
		{
			System.out.println("Send Message=["+message+"] with offset=["+result.getRecordMetadata().offset()+"]");
		}
		else
		{
			System.out.println("Unable to send message=["+message+"] due to : "+exception.getMessage());
		}
	});
}
public void sendEventsMessageToTopic(Customer message)
{
	try {
		//Java-CustomerTopic-New-Next-topic"
   // CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send("Java-CustomerTopic-New-Next-topic", message);
    CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(topicName, message);
    // Handling the result or exception
    completableFuture.whenComplete((result, exception) -> {
        if (exception == null) {
            // Successfully sent the message
            System.out.println("Sent Message=[" + message.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        } else {
            // Handling send failure
            System.out.println("Unable to send message=[" + message.toString() + "] due to: " + exception.getMessage());
        }
    });

} catch (Exception e) {
    // Handle any exceptions thrown during the setup of the CompletableFuture
    System.out.println("Error occurred while sending message=[" + message.toString() + "] due to: " + e.getMessage());
}
}
public void sendoneEventMessageToTopic(Customer message)
{
	try {
		//Java-CustomerTopic-New-Next-topic"
    CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send("Java-CustomerTopic-New-Next-topic", message);
   // CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(topicName, message);
    // Handling the result or exception
    completableFuture.whenComplete((result, exception) -> {
        if (exception == null) {
            // Successfully sent the message
            System.out.println("Sent Message=[" + message.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        } else {
            // Handling send failure
            System.out.println("Unable to send message=[" + message.toString() + "] due to: " + exception.getMessage());
        }
    });

} catch (Exception e) {
    // Handle any exceptions thrown during the setup of the CompletableFuture
    System.out.println("Error occurred while sending message=[" + message.toString() + "] due to: " + e.getMessage());
}
}
public void sendPartitionControl(String message)
{
	try {
		kafkaTemplate.send("New-Learn-Topic-Partitions", 2,null, "hi");
		kafkaTemplate.send("New-Learn-Topic-Partitions", 2,null, "hello");
    CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send("New-Learn-Topic-Partitions", 2,null, message);

    // Handling the result or exception
    completableFuture.whenComplete((result, exception) -> {
        if (exception == null) {
            // Successfully sent the message
            System.out.println("Sent Message=[" + message.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        } else {
            // Handling send failure
            System.out.println("Unable to send message=[" + message.toString() + "] due to: " + exception.getMessage());
        }
    });

} catch (Exception e) {
    // Handle any exceptions thrown during the setup of the CompletableFuture
    System.out.println("Error occurred while sending message=[" + message.toString() + "] due to: " + e.getMessage());
}
}

public void sendEvents(Customer user) {
    try {
    	 CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("helloeg", user); //is not working may be then check if ip address is null
    //    CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, user);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + user.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        user.toString() + "] due to : " + ex.getMessage());
            }
        });
    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }
}
}
