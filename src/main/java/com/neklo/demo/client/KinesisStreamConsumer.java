package com.neklo.demo.client;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import com.amazonaws.services.kinesis.model.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@Slf4j
public class KinesisStreamConsumer implements IRecordProcessor {
	@Override
	public void initialize(InitializationInput initializationInput) {

	}

	@Override
	public void processRecords(ProcessRecordsInput processRecordsInput) {
		IntStream intStream = processRecordsInput.getRecords().stream()
				.map(Record::getData)
				.map(el-> new String(el.array()))
				.mapToInt(Integer::valueOf);
		Double avg = intStream.average().orElse(0.0);
		IntStream intStream1 = processRecordsInput.getRecords().stream()
				.map(Record::getData)
				.map(el-> new String(el.array()))
				.mapToInt(Integer::valueOf);
		Long count = intStream1.count();
		log.info("Average is {}, count of records is {}", avg, count);

	}

	@Override
	public void shutdown(ShutdownInput shutdownInput) {

	}
}
