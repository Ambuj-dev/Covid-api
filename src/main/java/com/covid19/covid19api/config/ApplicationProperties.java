package com.covid19.covid19api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties( prefix = "com.covid19" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationProperties {

	private String elasticsearchHost;
	private String dataUrl;
}
