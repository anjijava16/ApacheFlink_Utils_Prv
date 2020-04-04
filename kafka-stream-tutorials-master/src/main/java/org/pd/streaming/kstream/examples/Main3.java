package org.pd.streaming.kstream.examples;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.WindowedSerdes;

public class Main3 
{
	static String TOPIC_IN = "Topic3-IN";
	static String TOPIC_OUT = "Topic3-OUT";
	static String BOOTSTRAP_SERVER = "localhost:9092";
	
	public static void main(String[] args)
	{
		Producer<String> p = new Producer<String>(BOOTSTRAP_SERVER, StringSerializer.class.getName());
		
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "example3");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		final StreamsBuilder builder = new StreamsBuilder();

		builder
		.stream(TOPIC_IN, Consumed.with(Serdes.String(), Serdes.String()))
		.filter((key, value) -> value != null && !value.isEmpty())
		.groupByKey()
		.windowedBy(TimeWindows.of(Duration.ofSeconds(5)).grace(Duration.ofMillis(500)))
		.aggregate(() -> "", (key, newValue, aggValue) -> aggValue + newValue.length())
		.toStream()
		.to(TOPIC_OUT, Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), Serdes.String()));

		// produce a number as string every second
		new NumberGenerator(p, TOPIC_IN).start();
				
		final KafkaStreams streams = new KafkaStreams(builder.build(), props);
		streams.start();
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}
}
