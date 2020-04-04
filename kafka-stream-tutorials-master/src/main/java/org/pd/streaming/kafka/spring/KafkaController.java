package org.pd.streaming.kafka.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class KafkaController 
{
	@Autowired
	private KafkaProducer producer;
	
	@PostMapping("message")
	public void sendMessage(@RequestBody String body)
	{
		producer.sendMessage(body);
	}
}
