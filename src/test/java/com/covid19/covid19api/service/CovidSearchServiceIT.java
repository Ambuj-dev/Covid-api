package com.covid19.covid19api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.repository.CovidRepository;
import com.covid19.covid19api.request.CovidDataRequest;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;

@SpringBootTest( properties = { "db.setup.enabled=false" }, webEnvironment = SpringBootTest.WebEnvironment.MOCK )
public class CovidSearchServiceIT {

	@Autowired
	private CovidSearchService covidSearchService;;

	@Autowired
	private CovidRepository covidRepository;

	@Test
	void getCovidDataByLocation() {

		CovidData covidData = CovidProvider.getCovidData();
		covidRepository.save( covidData );
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();

		List<CovidDecisionResponse> response = covidSearchService.getCovidDataByLocation( request );
		CovidDecisionResponse expectedResponse = response.iterator().next();

		assertNotNull( expectedResponse );
		assertEquals( 1, response.size() );
	}

	@Test
	void searchByProvinceStateAndCountryRegionAndCreateDate() {
		CovidData covidData = CovidProvider.getCovidData();
		covidRepository.save( covidData );
		CovidDataRequest request = CovidProvider.getCovidDataRequest();

		List<CovidDataResponse> response = covidSearchService.searchByProvinceStateAndCountryRegionAndCreateDate( request );
		CovidDataResponse expectedResponse = response.iterator().next();

		assertNotNull( expectedResponse );
		assertEquals( 1, response.size() );
	}

	@Test
	void findByThreshold() {

		CovidData covidData = CovidProvider.getCovidData();
		covidRepository.save( covidData );

		List<CovidDataResponse> response = covidSearchService.findByThreshold( 10L );
		CovidDataResponse expectedResponse = response.iterator().next();

		assertNotNull( expectedResponse );
		assertEquals( 1, response.size() );
	}

}
