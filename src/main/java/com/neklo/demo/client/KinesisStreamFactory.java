package com.neklo.demo.client;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KinesisStreamFactory implements IRecordProcessorFactory {

	@Resource
	private KinesisStreamConsumer kinesisStreamConsumer;

	@Override
	public IRecordProcessor createProcessor() {
		return kinesisStreamConsumer;
	}
}
