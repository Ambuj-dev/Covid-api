package com.covid19.covid19api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;

@Mapper( componentModel = "spring" )
public abstract class CovidMapper {

	public abstract CovidDecisionResponse covidDataToCovidDecisionResponse( CovidData covidData, Boolean readyForWorkFromOffice );

	public abstract CovidDataResponse covidDataToCovidDataResponse( CovidData covidData );

	public abstract List<CovidDataResponse> covidDataToCovidDataResponse( List<CovidData> covidData );
}
