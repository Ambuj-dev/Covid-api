package com.covid19.covid19api.logging.aspect;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.controller.CovidController;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.service.CovidSearchService;

@ExtendWith( MockitoExtension.class )
public class ControllerLoggingAspectTest {

	@Mock
	private CovidSearchService service;

	@InjectMocks
	private CovidController controller;

	@Test
	public void aroundGetByLocation() {
		// Given
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		given( service.getCovidDataByLocation( request ) ).willReturn( CovidProvider.getCovidDecisionResponses() );

		AspectJProxyFactory factory = new AspectJProxyFactory( controller );
		ControllerLoggingAspect aspect = new ControllerLoggingAspect();
		factory.addAspect( aspect );
		CovidController proxy = factory.getProxy();

		proxy.getCovidDataByLocation( request );

		then( service ).should( times( 1 ) ).getCovidDataByLocation( request );
	}

}
