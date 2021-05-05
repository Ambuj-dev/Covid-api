package com.covid19.covid19api.repository;

import java.util.List;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.request.CovidDataRequest;

public interface CovidRepositoryCustom {

	List<SearchHit<CovidData>> searchWithin( GeoPoint geoPoint, Double distance, String unit );

	List<SearchHit<CovidData>> searchByProvinceStateAndCountryRegionAndCreateDate( CovidDataRequest request );

};