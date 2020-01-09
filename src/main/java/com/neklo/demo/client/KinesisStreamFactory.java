package com.neklo.demo.client;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import org.springframework.stereotype.Component;

@Component
public class KinesisStreamFactory implements IRecordProcessorFactory {
	@Override
	public IRecordProcessor createProcessor() {
		return new KinesisStreamConsumer();
	}
}
