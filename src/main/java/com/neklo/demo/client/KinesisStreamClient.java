package com.neklo.demo.client;

import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.UserRecord;
import com.neklo.demo.csv.CoordsCsvParser;
import com.neklo.demo.model.ObjectPosition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Emulates a stream of data which is obtained from csv dataset
 */
@Component
@Slf4j
public class KinesisStreamClient {
	private final KinesisProducer kinesisProducer;
	private final String streamName;
	private final CoordsCsvParser coordsCsvParser;
	private AtomicInteger counter = new AtomicInteger(0);

	public KinesisStreamClient(KinesisProducer kinesisProducer, @Value("${kinesis.stream.name}") String streamName,
	                           CoordsCsvParser coordsCsvParser) {
		this.kinesisProducer = kinesisProducer;
		this.streamName = streamName;
		this.coordsCsvParser = coordsCsvParser;
	}

	@Scheduled(fixedDelay = 1000L)
	public void addUserRecord() {
		log.info("Processing batch {}", counter.get());
		List<ObjectPosition> coords = coordsCsvParser.getData(counter.get(), 10);
		coords.forEach(el -> {
			UserRecord userRecord = new UserRecord();
			userRecord.setData(ByteBuffer.wrap(SerializationUtils.serialize(el)));
			userRecord.setStreamName(streamName);
			userRecord.setPartitionKey(el.getObject());
			log.debug("Putting record with data {}", userRecord.getData());
			kinesisProducer.addUserRecord(userRecord);
		});
		counter.incrementAndGet();

	}
}
