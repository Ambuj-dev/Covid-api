package com.covid19.covid19api.config;

import java.time.Duration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

	private final ApplicationProperties applicationProperties;

	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo( applicationProperties.getElasticsearchHost() )
				.withSocketTimeout( Duration.ofSeconds( 60 ) )
				.build();

		return RestClients.create( clientConfiguration )
				.rest();
	}

}