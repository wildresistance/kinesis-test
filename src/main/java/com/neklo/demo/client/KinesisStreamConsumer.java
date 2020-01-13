package com.neklo.demo.client;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import com.neklo.demo.dao.CoordsRepository;
import com.neklo.demo.model.ObjectPosition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KinesisStreamConsumer implements IRecordProcessor {


	private final CoordsRepository coordsRepository;

	public KinesisStreamConsumer(CoordsRepository coordsRepository) {
		this.coordsRepository = coordsRepository;
	}

	@Override
	public void initialize(InitializationInput initializationInput) {

	}

	@Override
	public void processRecords(ProcessRecordsInput processRecordsInput) {
		processRecordsInput
				.getRecords().forEach(record -> {
			ObjectPosition position = SerializationUtils.deserialize(record.getData().array());
			coordsRepository.addCords(position);
		});
	}

	@Override
	public void shutdown(ShutdownInput shutdownInput) {

	}
}
