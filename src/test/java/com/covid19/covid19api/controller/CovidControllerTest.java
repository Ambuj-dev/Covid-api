package com.covid19.covid19api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.request.CovidDataRequest;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;
import com.covid19.covid19api.service.CovidSearchService;

@ExtendWith( MockitoExtension.class )
class CovidControllerTest {

	@Mock
	private CovidSearchService covidSearchService;

	@InjectMocks
	private CovidController covidController;

	@Test
	@DisplayName( "Get Covid Data by Location" )
	void getCovidDataByLocation() {
		// Given
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		given( covidSearchService.getCovidDataByLocation( request ) ).willReturn( CovidProvider.getCovidDecisionResponses() );

		// When
		ResponseEntity<List<CovidDecisionResponse>> response = covidController.getCovidDataByLocation( request );

		// Then
		then( covidSearchService ).should( times( 1 ) ).getCovidDataByLocation( request );
		assertEquals( 2, response.getBody().size() );
		assertEquals( HttpStatus.OK, response.getStatusCode() );
	}

	@Test
	@DisplayName( "Get Covid Data by All Filter" )
	void getByFilter() {
		// Given
		CovidDataRequest request = CovidProvider.getCovidDataRequest();
		given( covidSearchService.searchByProvinceStateAndCountryRegionAndCreateDate( request ) ).willReturn( CovidProvider.getCovidDataResponses() );

		// When
		ResponseEntity<List<CovidDataResponse>> response = covidController.getByFilter( request );

		// Then
		then( covidSearchService ).should( times( 1 ) ).searchByProvinceStateAndCountryRegionAndCreateDate( request );
		assertEquals( 2, response.getBody().size() );
		assertEquals( HttpStatus.OK, response.getStatusCode() );
	}

	@Test
	@DisplayName( "Get Covid Data by some threshold value" )
	void getByThreshold() {

		// Given
		Long threshold = 5L;
		given( covidSearchService.findByThreshold( threshold ) ).willReturn( CovidProvider.getCovidDataResponses() );

		// When
		ResponseEntity<List<CovidDataResponse>> response = covidController.getByThreshold( threshold );

		// Then
		then( covidSearchService ).should( times( 1 ) ).findByThreshold( threshold );
		assertEquals( 2, response.getBody().size() );
		assertEquals( HttpStatus.OK, response.getStatusCode() );
	}
}