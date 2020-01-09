package com.neklo.demo.client;

import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.IntStream;

@Component
@Slf4j
public class KinesisStreamClient {
	private final KinesisProducer kinesisProducer;
	private final String streamName;

	public KinesisStreamClient(KinesisProducer kinesisProducer, @Value("${kinesis.stream.name}") String streamName) {
		this.kinesisProducer = kinesisProducer;
		this.streamName = streamName;
	}

	@Scheduled(fixedDelay = 1000L)
	public void addUserRecord() {
		IntStream.range(1, new Random().nextInt(100)).forEach(el -> {
			UserRecord userRecord = new UserRecord();
			String data = String.valueOf(new Random().nextInt());
			userRecord.setData(ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8)));
			userRecord.setStreamName(streamName);
			userRecord.setPartitionKey("int");
			log.debug("Putting record with data {}", data);
			kinesisProducer.addUserRecord(userRecord);
		});

	}
}
