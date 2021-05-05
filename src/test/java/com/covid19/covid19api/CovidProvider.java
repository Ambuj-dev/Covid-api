package com.covid19.covid19api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.request.CovidDataRequest;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.request.Frequency;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;

public class CovidProvider {

	public static List<CovidDecisionResponse> getCovidDecisionResponses() {
		List<CovidDecisionResponse> listOfCovidDecisionResponse = new ArrayList<>();

		CovidDecisionResponse response1 = CovidDecisionResponse.builder().createdDate( LocalDate.now() )
				.FIPS( "2195" )
				.provinceState( "Nome" )
				.countryRegion( "Alaska" )
				.location( new GeoPoint( 64.90320724, -164.0353804 ) )
				.confirmed( 279L )
				.deaths( 0L )
				.recovered( 0L )
				.active( 279L )
				.combinedKey( "Nome, Alaska, US" )
				.incidentRate( 2788.884446221512 )
				.caseFatalityRatio( 0.0 )
				.readyForWorkFromOffice( true )
				.build();
		CovidDecisionResponse response2 = CovidDecisionResponse.builder().createdDate( LocalDate.now() )
				.FIPS( "2185" )
				.provinceState( "North Slope" )
				.countryRegion( "Alaska" )
				.location( new GeoPoint( 69.31479216, -153.4836093 ) )
				.confirmed( 819L )
				.deaths( 3L )
				.recovered( 0L )
				.active( 816L )
				.combinedKey( "North Slope, Alaska, US" )
				.incidentRate( 8329.943043124491 )
				.caseFatalityRatio( 0.3663003663003663 )
				.readyForWorkFromOffice( true )
				.build();
		listOfCovidDecisionResponse.add( response1 );
		listOfCovidDecisionResponse.add( response2 );
		return listOfCovidDecisionResponse;

	}

	public static CovidGeoRequest getCovidGeoRequest() {
		return CovidGeoRequest.builder().latitude( 64.90320724 )
				.longitude( -164.0353804 )
				.distanceInKm( 1.0 )
				.totalPopulation( 1000L )
				.threshHold( 20L ).build();
	}

	public static List<CovidDataResponse> getCovidDataResponses() {
		List<CovidDataResponse> listOfCovidDataResponse = getFilteredCovidDataResponses();

		CovidDataResponse response1 = CovidDataResponse.builder().createdDate( LocalDate.now() )
				.FIPS( "2195" )
				.provinceState( "Nome" )
				.countryRegion( "Alaska" )
				.location( new GeoPoint( 64.90320724, -164.0353804 ) )
				.confirmed( 279L )
				.deaths( 0L )
				.recovered( 0L )
				.active( 279L )
				.combinedKey( "Nome, Alaska, US" )
				.incidentRate( 2788.884446221512 )
				.caseFatalityRatio( 0.0 )
				.build();
		listOfCovidDataResponse.add( response1 );
		return listOfCovidDataResponse;

	}

	public static List<CovidDataResponse> getFilteredCovidDataResponses() {
		List<CovidDataResponse> listOfCovidDataResponse = new ArrayList<>();

		CovidDataResponse response1 = CovidDataResponse.builder().createdDate( LocalDate.now() )
				.FIPS( "2185" )
				.provinceState( "North Slope" )
				.countryRegion( "Alaska" )
				.location( new GeoPoint( 69.31479216, -153.4836093 ) )
				.confirmed( 819L )
				.deaths( 3L )
				.recovered( 0L )
				.active( 816L )
				.combinedKey( "North Slope, Alaska, US" )
				.incidentRate( 8329.943043124491 )
				.caseFatalityRatio( 0.3663003663003663 )
				.build();
		listOfCovidDataResponse.add( response1 );
		return listOfCovidDataResponse;

	}

	public static CovidDataRequest getCovidDataRequest() {
		return CovidDataRequest.builder()
				.provinceState( "North Slope" )
				.countryRegion( "Alaska" )
				.startDate( LocalDate.now().minusDays( 1 ) )
				.endDate( LocalDate.now() )
				.build();
	}

	public static CovidDataRequest getCovidDataRequestWithFrequency() {
		return CovidDataRequest.builder()
				.provinceState( "North Slope" )
				.countryRegion( "Alaska" )
				.frequency( Frequency.Daily )
				.build();

	}

	public static List<CovidData> getCovidDatas() {
		List<CovidData> covidDataList = new ArrayList<>();
		covidDataList.add( getCovidData() );
		return covidDataList;
	}

	public static CovidData getCovidData() {
		return CovidData.builder().createdDate( LocalDate.now() )
				.FIPS( "2185" )
				.provinceState( "North Slope" )
				.countryRegion( "Alaska" )
				.location( new GeoPoint( 64.90320724, -164.0353804 ) )
				.confirmed( 819L )
				.deaths( 3L )
				.recovered( 0L )
				.active( 816L )
				.combinedKey( "North Slope, Alaska, US" )
				.incidentRate( 8329.943043124491 )
				.caseFatalityRatio( 0.3663003663003663 )
				.build();
	}

}
