package com.covid19.covid19api.repository;

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
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Query;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.request.CovidDataRequest;
import com.covid19.covid19api.request.Frequency;

@ExtendWith( MockitoExtension.class )
class CovidRepositoryCustomImplTest {

	@Mock
	private ElasticsearchOperations operations;

	@Mock
	private SearchHits<Object> data;
	@Mock
	private List<SearchHit<CovidData>> data1;

	@InjectMocks
	private CovidRepositoryCustomImpl covidRepositoryCustomImpl;

	@Test
	void searchWithin() {

		// Given
		GeoPoint geoPoint = new GeoPoint( 12.22, 64.65 );
		Double distance = 100.00;
		String unit = "km";
		given( operations.search( any( Query.class ), any( Class.class ) ) ).willReturn( data );
		SearchHits<CovidData> covidData = (SearchHits<CovidData>) (Object) data;
		given( covidData.getSearchHits() ).willReturn( data1 );

		// When
		covidRepositoryCustomImpl.searchWithin( geoPoint, distance, unit );

		// Then
		then( operations ).should( times( 1 ) ).search( any( Query.class ), any( Class.class ) );
		then( data ).should( times( 1 ) ).getSearchHits();

	}

	@Test
	void searchByProvinceStateAndCountryRegionAndCreateDate() {

		// Given
		CovidDataRequest request = CovidProvider.getCovidDataRequest();
		given( operations.search( any( Query.class ), any( Class.class ) ) ).willReturn( data );
		SearchHits<CovidData> covidData = (SearchHits<CovidData>) (Object) data;
		given( covidData.getSearchHits() ).willReturn( data1 );

		// When
		covidRepositoryCustomImpl.searchByProvinceStateAndCountryRegionAndCreateDate( request );

		// Then
		then( operations ).should( times( 1 ) ).search( any( Query.class ), any( Class.class ) );
		then( data ).should( times( 1 ) ).getSearchHits();

	}

	@Test
	void searchByProvinceStateAndCountryRegionAndCreateDateInFrequencyDaily() {

		// Given
		CovidDataRequest request = CovidProvider.getCovidDataRequestWithFrequency();
		given( operations.search( any( Query.class ), any( Class.class ) ) ).willReturn( data );
		SearchHits<CovidData> covidData = (SearchHits<CovidData>) (Object) data;
		given( covidData.getSearchHits() ).willReturn( data1 );

		// When
		covidRepositoryCustomImpl.searchByProvinceStateAndCountryRegionAndCreateDate( request );

		// Then
		then( operations ).should( times( 1 ) ).search( any( Query.class ), any( Class.class ) );
		then( data ).should( times( 1 ) ).getSearchHits();

	}

	@Test
	void searchByProvinceStateAndCountryRegionAndCreateDateInFrequencyWeekly() {

		// Given
		CovidDataRequest request = CovidProvider.getCovidDataRequestWithFrequency();
		request.setFrequency( Frequency.Weekly );
		given( operations.search( any( Query.class ), any( Class.class ) ) ).willReturn( data );
		SearchHits<CovidData> covidData = (SearchHits<CovidData>) (Object) data;
		given( covidData.getSearchHits() ).willReturn( data1 );

		// When
		covidRepositoryCustomImpl.searchByProvinceStateAndCountryRegionAndCreateDate( request );

		// Then
		then( operations ).should( times( 1 ) ).search( any( Query.class ), any( Class.class ) );
		then( data ).should( times( 1 ) ).getSearchHits();

	}

	@Test
	void searchByProvinceStateAndCountryRegionAndCreateDateInFrequencyMonthly() {

		// Given
		CovidDataRequest request = CovidProvider.getCovidDataRequestWithFrequency();
		request.setFrequency( Frequency.Monthly );
		given( operations.search( any( Query.class ), any( Class.class ) ) ).willReturn( data );
		SearchHits<CovidData> covidData = (SearchHits<CovidData>) (Object) data;
		given( covidData.getSearchHits() ).willReturn( data1 );

		// When
		covidRepositoryCustomImpl.searchByProvinceStateAndCountryRegionAndCreateDate( request );

		// Then
		then( operations ).should( times( 1 ) ).search( any( Query.class ), any( Class.class ) );
		then( data ).should( times( 1 ) ).getSearchHits();

	}
}