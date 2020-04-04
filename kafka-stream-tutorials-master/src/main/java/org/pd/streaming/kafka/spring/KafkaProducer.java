package org.pd.streaming.kafka.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaProducer 
{
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private String topicName = "test";
	 
	public void sendMessage(String msg) 
	{
	    kafkaTemplate.send(topicName, msg);
	}
}
