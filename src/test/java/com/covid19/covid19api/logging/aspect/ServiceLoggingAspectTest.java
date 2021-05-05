package com.covid19.covid19api.logging.aspect;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.data.elasticsearch.core.SearchHit;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.mapper.CovidMapper;
import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.repository.CovidRepository;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.service.CovidSearchService;

@ExtendWith( MockitoExtension.class )
public class ServiceLoggingAspectTest {

	@Mock
	private CovidRepository repository;

	@Mock
	private CovidMapper mapper;

	@Mock
	private List<SearchHit<CovidData>> data;

	@InjectMocks
	private CovidSearchService service;

	@Test
	public void aroundGetByLocation() {
		// Given
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		given( repository.searchWithin( any(), any(), any() ) ).willReturn( data );

		AspectJProxyFactory factory = new AspectJProxyFactory( service );
		ServiceLoggingAspect aspect = new ServiceLoggingAspect();
		factory.addAspect( aspect );
		CovidSearchService proxy = factory.getProxy();

		// When
		proxy.getCovidDataByLocation( request );

		// Then
		then( repository ).should( times( 1 ) ).searchWithin( any(), any(), any() );
		then( mapper ).should( times( 0 ) ).covidDataToCovidDecisionResponse( any(), any() );
	}

}
