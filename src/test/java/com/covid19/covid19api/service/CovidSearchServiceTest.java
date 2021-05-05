package com.covid19.covid19api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.mapper.CovidMapper;
import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.repository.CovidRepository;
import com.covid19.covid19api.request.CovidDataRequest;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;

@ExtendWith( MockitoExtension.class )
class CovidSearchServiceTest {

	@Mock
	private CovidRepository covidRepository;

	@Mock
	private CovidMapper mapper;

	@Mock
	private List<SearchHit<CovidData>> data;

	@InjectMocks
	private CovidSearchService covidSearchService;

	@Test
	void getCovidDataByLocation() {
		// Given
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		given( covidRepository.searchWithin( new GeoPoint( request.getLatitude(), request.getLongitude() ), request.getDistanceInKm(), "km" ) ).willReturn( data );

		// When
		List<CovidDecisionResponse> response = covidSearchService.getCovidDataByLocation( request );

		// Then
		then( covidRepository ).should( times( 1 ) ).searchWithin( any(), any(), any() );
		then( mapper ).should( times( 0 ) ).covidDataToCovidDecisionResponse( any(), any() );
		assertNotNull( response );
	}

	@Test
	void searchByProvinceStateAndCountryRegionAndCreateDate() {

		// Given
		CovidDataRequest request = CovidProvider.getCovidDataRequest();
		given( covidRepository.searchByProvinceStateAndCountryRegionAndCreateDate( request ) ).willReturn( data );

		// When
		List<CovidDataResponse> response = covidSearchService.searchByProvinceStateAndCountryRegionAndCreateDate( request );

		// Then
		then( covidRepository ).should( times( 1 ) ).searchByProvinceStateAndCountryRegionAndCreateDate( request );
		assertNotNull( response );
	}

	@Test
	void findByThreshold() {

		// Given
		Long threshold = 10L;
		List<CovidData> covidDataList = CovidProvider.getCovidDatas();
		List<CovidDataResponse> expectedResponse = CovidProvider.getCovidDataResponses();
		given( covidRepository.findByActiveGreaterThan( threshold ) ).willReturn( covidDataList );
		given( mapper.covidDataToCovidDataResponse( covidDataList ) ).willReturn( expectedResponse );

		// When
		List<CovidDataResponse> response = covidSearchService.findByThreshold( threshold );

		// Then
		then( covidRepository ).should( times( 1 ) ).findByActiveGreaterThan( threshold );
		then( mapper ).should( times( 1 ) ).covidDataToCovidDataResponse( covidDataList );
		assertNotNull( response );
	}
}