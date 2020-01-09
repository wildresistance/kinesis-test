package com.neklo.demo;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.neklo.demo.client.KinesisStreamFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class DemoPipingAmazonApplication {

	@Resource
	private KinesisStreamFactory kinesisStreamFactory;

	@Resource
	private KinesisClientLibConfiguration clientLibConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(DemoPipingAmazonApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		final Worker worker = new Worker.Builder()
				.recordProcessorFactory(kinesisStreamFactory)
				.config(clientLibConfiguration)
				.build();
		CompletableFuture.runAsync(worker);
	}
}
