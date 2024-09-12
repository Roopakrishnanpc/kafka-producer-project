package com.kafka.producer.controller;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.dto.Customer;
import com.kafka.producer.service.KafkaMessagePublisher;
import com.kafka.producer.utils.CsvReaderUtils;

@RestController
@RequestMapping("/producer-app")
public class EventController {
	@Autowired
private KafkaMessagePublisher  kafkaMessagePublisher;
	@GetMapping("/publish/{message}")
public ResponseEntity<?> publishMessage(@PathVariable String message)
{
		try
		{
			for(int i=0;i<=100000;i++)
			{
				kafkaMessagePublisher.sendMessageToTopic(message+" : "+i);
			}
		
		return ResponseEntity.ok("Message published successfully");
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
}
	@PostMapping("/publish/onevent")
	public ResponseEntity<?> sendoneEvent(@RequestBody Customer customer)
	{
		//List<Customer> customers = new ArrayList<>();
        try {
					//
        	kafkaMessagePublisher.sendoneEventMessageToTopic(customer);
			
			
			return ResponseEntity.ok("Message published successfully");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
	}
	@PostMapping("/publish")
	public ResponseEntity<?> sendEvents(@RequestBody Customer customer)
	{
		//List<Customer> customers = new ArrayList<>();
        try (FileReader reader = new FileReader("src/main/resources/users.csv");
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
//            for (CSVRecord record : parser) {
//                Customer customer1 = new Customer();
//                customer1.setId(Integer.parseInt(record.get("Id")));
//                customer1.setFstname(record.get("Fstname"));
//                customer1.setLstname(record.get("Lstname"));
//                customer1.setEmail(record.get("Email"));
//                customer1.setGender(record.get("Gender"));
//                customer1.setIpAddress(record.get("IpAddress"));
//                customers.add(customer1);
//            }
            List<Customer> customers = StreamSupport.stream(parser.spliterator(), false)
                    .map(this::mapToCustomer)
                    .collect(Collectors.toList());
				//List<Customer> customers=CsvReaderUtils.readDataFromCsv();
				customers.forEach(cust-> kafkaMessagePublisher.sendEventsMessageToTopic(cust));
				//System.out.println(customers.toString());
				//customers.forEach(cust-> System.out.print(cust.getEmail()));
				//for(int i=0;i<=100000;i++)
				//{
					//kafkaMessagePublisher.sendEventsMessageToTopic(customer);
				//}
			
			return ResponseEntity.ok("Message published successfully");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
	}
	
	
    private Customer mapToCustomer(CSVRecord record) {
        Customer customer = new Customer();
        customer.setId(Integer.parseInt(record.get("id"))); // Use lowercase 'id'
        customer.setFirstName(record.get("firstName")); // Use 'firstName'
        customer.setLastName(record.get("lastName")); // Use 'lastName'
        customer.setEmail(record.get("email")); // Use 'email'
        customer.setGender(record.get("gender")); // Use 'gender'
        customer.setIpAddress(record.get("ip_address")); // Use 'ip_address'
        return customer;
      //  return customer;
    }
	@GetMapping("/publish/part/{message}")
	public ResponseEntity<?> publishMessagepPartition(@PathVariable String message)
	{
			try
			{
				for(int i=0;i<=10;i++)
			//	for(int i=0;i<=100000;i++)
				{
					kafkaMessagePublisher.sendPartitionControl(message+" : "+i);
					
				}
			
			return ResponseEntity.ok("Message published successfully");
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
	}
    @PostMapping("/publishNew")
    public ResponseEntity<?> publishEvent(@RequestBody Customer customer) {
        try {
            List<Customer> users = CsvReaderUtils.readDataFromCsv();
            users.forEach(usr -> kafkaMessagePublisher.sendEvents(usr));
            return ResponseEntity.ok("Message published successfully");
        } catch (Exception exception) {
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
	/*{
    "id":2,
    "name":"Roopa Sri",
    "email":"roopa@gmail.com",
     "gender":"Female",
  "age":26,
" contactNo":809768909, 
"ipAddress":"81.9.95.253"
}*/
}
