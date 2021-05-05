package com.covid19.covid19api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
public class Covid19ApiApplication {

	public static void main( String[] args ) {
		SpringApplication.run( Covid19ApiApplication.class, args );
	}

}
