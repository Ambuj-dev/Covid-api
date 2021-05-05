package com.covid19.covid19api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.covid19api.request.CovidDataRequest;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;
import com.covid19.covid19api.service.CovidSearchService;

import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping( "/api/covid" )
@AllArgsConstructor
public class CovidController {

	private final CovidSearchService covidSearchService;

	@GetMapping( "/getByLocation" )
	public ResponseEntity<List<CovidDecisionResponse>> getCovidDataByLocation( @Valid CovidGeoRequest request ) {

		return ResponseEntity.ok()
				.body( covidSearchService.getCovidDataByLocation( request ) );

	}

	@GetMapping( "/getByFilter" )
	public ResponseEntity<List<CovidDataResponse>> getByFilter( CovidDataRequest request ) {

		return ResponseEntity.ok()
				.body( covidSearchService.searchByProvinceStateAndCountryRegionAndCreateDate( request ) );

	}

	@GetMapping( "/getByThreshold" )
	public ResponseEntity<List<CovidDataResponse>> getByThreshold( @Valid @NotNull Long threshold ) {

		return ResponseEntity.ok()
				.body( covidSearchService.findByThreshold( threshold ) );

	}

}
