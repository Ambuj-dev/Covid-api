package com.covid19.covid19api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import com.covid19.covid19api.mapper.CovidMapper;
import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.repository.CovidRepository;
import com.covid19.covid19api.request.CovidDataRequest;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CovidSearchService {

	public static final String KM = "km";
	private final CovidRepository covidRepository;
	private final CovidMapper covidMapper;

	public List<CovidDecisionResponse> getCovidDataByLocation( CovidGeoRequest request ) {

		GeoPoint location = new GeoPoint( request.getLatitude(), request.getLongitude() );

		List<SearchHit<CovidData>> searchHits = covidRepository.searchWithin( location, request.getDistanceInKm(), KM );

		List<CovidDecisionResponse> responses = new ArrayList<>();

		searchHits.stream().forEach( searchHit -> {
			CovidData covidData = searchHit.getContent();
			Double activePercentage = covidData.getActive() * 100.0 / request.getTotalPopulation();
			boolean readyForWorkFromOffice = activePercentage < request.getThreshHold();
			responses.add( covidMapper.covidDataToCovidDecisionResponse( covidData, readyForWorkFromOffice ) );
		} );
		return responses;

	}

	public List<CovidDataResponse> searchByProvinceStateAndCountryRegionAndCreateDate( CovidDataRequest request ) {
		List<SearchHit<CovidData>> searchHits = covidRepository.searchByProvinceStateAndCountryRegionAndCreateDate( request );
		return searchHits.stream().map( searchHit -> covidMapper.covidDataToCovidDataResponse( searchHit.getContent() ) ).collect( Collectors.toList() );
	}

	public List<CovidDataResponse> findByThreshold( Long threshold ) {
		return covidMapper.covidDataToCovidDataResponse( covidRepository.findByActiveGreaterThan( threshold ) );
	}

}
