package com.neklo.demo.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationConfig {

	@Bean
	public KinesisProducer kinesisProducer(AWSCredentialsProvider credentialsProvider) {
		KinesisProducerConfiguration configuration = new KinesisProducerConfiguration();
		configuration.setCredentialsProvider(credentialsProvider);
		configuration.setVerifyCertificate(false);
		configuration.setRegion(Regions.DEFAULT_REGION.getName());
		return new KinesisProducer(configuration);
	}

	@Bean
	public AWSCredentialsProvider credentialsProvider() {
		return new ProfileCredentialsProvider();
	}

	@Bean
	public KinesisClientLibConfiguration clientLibConfiguration(AWSCredentialsProvider credentialsProvider,
	                                                            @Value("${kinesis.stream.name}") String streamName) {
		return new KinesisClientLibConfiguration("app", streamName,
				credentialsProvider, "ID").withRegionName(Regions.DEFAULT_REGION.getName());
	}
}
