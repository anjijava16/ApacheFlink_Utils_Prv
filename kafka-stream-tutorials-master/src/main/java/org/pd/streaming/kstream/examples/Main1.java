package org.pd.streaming.kstream.examples;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Reducer;
import org.apache.kafka.streams.kstream.TimeWindows;

public class Main1 
{
	static String TOPIC_IN = "Topic1-IN";
	static String BOOTSTRAP_SERVER = "localhost:9092";
	
	public static void main(String[] args) 
	{
		Producer<String> p = new Producer<String>(BOOTSTRAP_SERVER, StringSerializer.class.getName());
		
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "example1");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		final StreamsBuilder builder = new StreamsBuilder();

		builder
		.stream(TOPIC_IN, Consumed.with(Serdes.String(), Serdes.String()))
		.groupByKey()
		.windowedBy(TimeWindows.of(Duration.ofSeconds(5)))  // can't use window without groupByKey
		.reduce(new Reducer<String>() 
		{
			@Override 
			public String apply(String value1, String value2) 
			{ 
				System.out.println(LocalTime.now() + " -> " + value1 + "   " + value2);
				return value1+value2;
			} 
		})
		.toStream()
		.print(Printed.toSysOut());
		
		// produce a number as string every second
		new NumberGenerator(p, TOPIC_IN).start();
				
		Topology topology = builder.build();
		System.out.println(topology.describe());
		
		final KafkaStreams streams = new KafkaStreams(topology, props);
		
		streams.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}
}
