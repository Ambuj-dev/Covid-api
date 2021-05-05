package com.covid19.covid19api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.request.CovidGeoRequest;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;
import com.covid19.covid19api.service.CovidSearchService;

@WebMvcTest( controllers = CovidController.class )
public class CovidControllerIT {

	@MockBean
	private CovidSearchService covidSearchService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName( "Get Covid Data by Location" )
	void getCovidDataByLocation() throws Exception {
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		List<CovidDecisionResponse> response = CovidProvider.getCovidDecisionResponses();
		given( covidSearchService.getCovidDataByLocation( request ) ).willReturn( response );

		expectCovidDecisionResponse( response, mockMvc.perform( get( "/api/covid/getByLocation?latitude=64.90320724&longitude=-164.0353804&distanceInKm=1000&totalPopulation=1000&threshHold=20" ) ) );
	}

	@Test
	@DisplayName( "Get Covid Data by given no latitude" )
	void getCovidDataByNoLatitude() throws Exception {
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		List<CovidDecisionResponse> response = CovidProvider.getCovidDecisionResponses();
		given( covidSearchService.getCovidDataByLocation( request ) ).willReturn( response );

		mockMvc.perform( get( "/api/covid/getByLocation?longitude=-164.0353804&distanceInKm=1000&totalPopulation=1000&threshHold=20" ) ).andExpect( status().isBadRequest() );
	}

	@Test
	@DisplayName( "Get Covid Data by given no longitude" )
	void getCovidDataByNoLongitude() throws Exception {
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		List<CovidDecisionResponse> response = CovidProvider.getCovidDecisionResponses();
		given( covidSearchService.getCovidDataByLocation( request ) ).willReturn( response );

		mockMvc.perform( get( "/api/covid/getByLocation?latitude=64.90320724&distanceInKm=1000&totalPopulation=1000&threshHold=20" ) ).andExpect( status().isBadRequest() );
	}

	@Test
	@DisplayName( "Get Covid Data by given no distanceInKm" )
	void getCovidDataByNoDistanceInKm() throws Exception {
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		List<CovidDecisionResponse> response = CovidProvider.getCovidDecisionResponses();
		given( covidSearchService.getCovidDataByLocation( request ) ).willReturn( response );

		mockMvc.perform( get( "/api/covid/getByLocation?latitude=64.90320724&longitude=-164.0353804&totalPopulation=1000&threshHold=20" ) ).andExpect( status().isBadRequest() );
	}

	@Test
	@DisplayName( "Get Covid Data by no totalPopulation" )
	void getCovidDataByNoTotalPopulation() throws Exception {
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		List<CovidDecisionResponse> response = CovidProvider.getCovidDecisionResponses();
		given( covidSearchService.getCovidDataByLocation( request ) ).willReturn( response );

		mockMvc.perform( get( "/api/covid/getByLocation?latitude=64.90320724&longitude=-164.0353804&distanceInKm=1000&threshHold=20" ) ).andExpect( status().isBadRequest() );
	}

	@Test
	@DisplayName( "Get Covid Data by no threshHold" )
	void getCovidDataByNoThreshHold() throws Exception {
		CovidGeoRequest request = CovidProvider.getCovidGeoRequest();
		List<CovidDecisionResponse> response = CovidProvider.getCovidDecisionResponses();
		given( covidSearchService.getCovidDataByLocation( request ) ).willReturn( response );

		mockMvc.perform( get( "/api/covid/getByLocation?latitude=64.90320724&longitude=-164.0353804&distanceInKm=1000&totalPopulation=1000" ) ).andExpect( status().isBadRequest() );
	}

	@Test
	@DisplayName( "Get Covid Data by No Filter" )
	void getByFilter() throws Exception {
		List<CovidDataResponse> response = CovidProvider.getCovidDataResponses();
		given( covidSearchService.searchByProvinceStateAndCountryRegionAndCreateDate( any() ) ).willReturn( response );

		expectCovidDataResponse( response, mockMvc.perform( get( "/api/covid/getByFilter" ) ) );
	}

	@Test
	@DisplayName( "Get Covid Data by All Filter" )
	void getByAllFilter() throws Exception {
		List<CovidDataResponse> response = CovidProvider.getFilteredCovidDataResponses();
		given( covidSearchService.searchByProvinceStateAndCountryRegionAndCreateDate( any() ) ).willReturn( response );

		expectFilteredCovidDataResponse( response, mockMvc.perform( get( "/api/covid/getByFilter?provinceState=North Slope&countryRegion=Alaska&frequency=Daily" ) ) );
	}

	@Test
	@DisplayName( "Get Covid Data by some threshold value" )
	void getByThreshold() throws Exception {
		Long threshold = 5L;
		List<CovidDataResponse> response = CovidProvider.getCovidDataResponses();
		given( covidSearchService.findByThreshold( threshold ) ).willReturn( response );

		expectCovidDataResponse( response, mockMvc.perform( get( "/api/covid/getByThreshold?threshold=5" ) ) );
	}

	@Test
	@DisplayName( "Get Covid Data by no threshold value" )
	void getByNoThreshold() throws Exception {
		Long threshold = 5L;
		List<CovidDataResponse> response = CovidProvider.getCovidDataResponses();
		given( covidSearchService.findByThreshold( threshold ) ).willReturn( response );

		mockMvc.perform( get( "/api/covid/getByThreshold" ) ).andExpect( status().isBadRequest() );
		;
	}

	private void expectCovidDecisionResponse( List<CovidDecisionResponse> responseContent, ResultActions perform )
			throws Exception {
		perform.andExpect( status().is( OK.value() ) )
				.andExpect( jsonPath( "$[0].createdDate" ).value( responseContent.get( 0 ).getCreatedDate().toString() ) )
				.andExpect( jsonPath( "$[0].fips" ).value( responseContent.get( 0 ).getFIPS() ) )
				.andExpect( jsonPath( "$[0].admin2" ).value( responseContent.get( 0 ).getAdmin2() ) )
				.andExpect( jsonPath( "$[0].provinceState" ).value( responseContent.get( 0 ).getProvinceState() ) )
				.andExpect( jsonPath( "$[0].countryRegion" ).value( responseContent.get( 0 ).getCountryRegion() ) )
				.andExpect( jsonPath( "$[0].confirmed" ).value( responseContent.get( 0 ).getConfirmed() ) )
				.andExpect( jsonPath( "$[0].deaths" ).value( responseContent.get( 0 ).getDeaths() ) )
				.andExpect( jsonPath( "$[0].recovered" ).value( responseContent.get( 0 ).getRecovered() ) )
				.andExpect( jsonPath( "$[0].active" ).value( responseContent.get( 0 ).getActive() ) )
				.andExpect( jsonPath( "$[0].combinedKey" ).value( responseContent.get( 0 ).getCombinedKey() ) )
				.andExpect( jsonPath( "$[0].incidentRate" ).value( responseContent.get( 0 ).getIncidentRate() ) )
				.andExpect( jsonPath( "$[0].caseFatalityRatio" ).value( responseContent.get( 0 ).getCaseFatalityRatio() ) )
				.andExpect( jsonPath( "$[0].readyForWorkFromOffice" ).value( responseContent.get( 0 ).getReadyForWorkFromOffice() ) )
				.andExpect( jsonPath( "$[1].createdDate" ).value( responseContent.get( 1 ).getCreatedDate().toString() ) )
				.andExpect( jsonPath( "$[1].fips" ).value( responseContent.get( 1 ).getFIPS() ) )
				.andExpect( jsonPath( "$[1].admin2" ).value( responseContent.get( 1 ).getAdmin2() ) )
				.andExpect( jsonPath( "$[1].provinceState" ).value( responseContent.get( 1 ).getProvinceState() ) )
				.andExpect( jsonPath( "$[1].countryRegion" ).value( responseContent.get( 1 ).getCountryRegion() ) )
				.andExpect( jsonPath( "$[1].confirmed" ).value( responseContent.get( 1 ).getConfirmed() ) )
				.andExpect( jsonPath( "$[1].deaths" ).value( responseContent.get( 1 ).getDeaths() ) )
				.andExpect( jsonPath( "$[1].recovered" ).value( responseContent.get( 1 ).getRecovered() ) )
				.andExpect( jsonPath( "$[1].active" ).value( responseContent.get( 1 ).getActive() ) )
				.andExpect( jsonPath( "$[1].combinedKey" ).value( responseContent.get( 1 ).getCombinedKey() ) )
				.andExpect( jsonPath( "$[1].incidentRate" ).value( responseContent.get( 1 ).getIncidentRate() ) )
				.andExpect( jsonPath( "$[1].caseFatalityRatio" ).value( responseContent.get( 1 ).getCaseFatalityRatio() ) )
				.andExpect( jsonPath( "$[1].readyForWorkFromOffice" ).value( responseContent.get( 1 ).getReadyForWorkFromOffice() ) );
	}

	private void expectCovidDataResponse( List<CovidDataResponse> responseContent, ResultActions perform )
			throws Exception {
		perform.andExpect( status().is( OK.value() ) )
				.andExpect( jsonPath( "$[0].createdDate" ).value( responseContent.get( 0 ).getCreatedDate().toString() ) )
				.andExpect( jsonPath( "$[0].fips" ).value( responseContent.get( 0 ).getFIPS() ) )
				.andExpect( jsonPath( "$[0].admin2" ).value( responseContent.get( 0 ).getAdmin2() ) )
				.andExpect( jsonPath( "$[0].provinceState" ).value( responseContent.get( 0 ).getProvinceState() ) )
				.andExpect( jsonPath( "$[0].countryRegion" ).value( responseContent.get( 0 ).getCountryRegion() ) )
				.andExpect( jsonPath( "$[0].confirmed" ).value( responseContent.get( 0 ).getConfirmed() ) )
				.andExpect( jsonPath( "$[0].deaths" ).value( responseContent.get( 0 ).getDeaths() ) )
				.andExpect( jsonPath( "$[0].recovered" ).value( responseContent.get( 0 ).getRecovered() ) )
				.andExpect( jsonPath( "$[0].active" ).value( responseContent.get( 0 ).getActive() ) )
				.andExpect( jsonPath( "$[0].combinedKey" ).value( responseContent.get( 0 ).getCombinedKey() ) )
				.andExpect( jsonPath( "$[0].incidentRate" ).value( responseContent.get( 0 ).getIncidentRate() ) )
				.andExpect( jsonPath( "$[0].caseFatalityRatio" ).value( responseContent.get( 0 ).getCaseFatalityRatio() ) )
				.andExpect( jsonPath( "$[1].createdDate" ).value( responseContent.get( 1 ).getCreatedDate().toString() ) )
				.andExpect( jsonPath( "$[1].fips" ).value( responseContent.get( 1 ).getFIPS() ) )
				.andExpect( jsonPath( "$[1].admin2" ).value( responseContent.get( 1 ).getAdmin2() ) )
				.andExpect( jsonPath( "$[1].provinceState" ).value( responseContent.get( 1 ).getProvinceState() ) )
				.andExpect( jsonPath( "$[1].countryRegion" ).value( responseContent.get( 1 ).getCountryRegion() ) )
				.andExpect( jsonPath( "$[1].confirmed" ).value( responseContent.get( 1 ).getConfirmed() ) )
				.andExpect( jsonPath( "$[1].deaths" ).value( responseContent.get( 1 ).getDeaths() ) )
				.andExpect( jsonPath( "$[1].recovered" ).value( responseContent.get( 1 ).getRecovered() ) )
				.andExpect( jsonPath( "$[1].active" ).value( responseContent.get( 1 ).getActive() ) )
				.andExpect( jsonPath( "$[1].combinedKey" ).value( responseContent.get( 1 ).getCombinedKey() ) )
				.andExpect( jsonPath( "$[1].incidentRate" ).value( responseContent.get( 1 ).getIncidentRate() ) )
				.andExpect( jsonPath( "$[1].caseFatalityRatio" ).value( responseContent.get( 1 ).getCaseFatalityRatio() ) );
	}

	private void expectFilteredCovidDataResponse( List<CovidDataResponse> responseContent, ResultActions perform )
			throws Exception {
		perform.andExpect( status().is( OK.value() ) )
				.andExpect( jsonPath( "$[0].createdDate" ).value( responseContent.get( 0 ).getCreatedDate().toString() ) )
				.andExpect( jsonPath( "$[0].fips" ).value( responseContent.get( 0 ).getFIPS() ) )
				.andExpect( jsonPath( "$[0].admin2" ).value( responseContent.get( 0 ).getAdmin2() ) )
				.andExpect( jsonPath( "$[0].provinceState" ).value( responseContent.get( 0 ).getProvinceState() ) )
				.andExpect( jsonPath( "$[0].countryRegion" ).value( responseContent.get( 0 ).getCountryRegion() ) )
				.andExpect( jsonPath( "$[0].confirmed" ).value( responseContent.get( 0 ).getConfirmed() ) )
				.andExpect( jsonPath( "$[0].deaths" ).value( responseContent.get( 0 ).getDeaths() ) )
				.andExpect( jsonPath( "$[0].recovered" ).value( responseContent.get( 0 ).getRecovered() ) )
				.andExpect( jsonPath( "$[0].active" ).value( responseContent.get( 0 ).getActive() ) )
				.andExpect( jsonPath( "$[0].combinedKey" ).value( responseContent.get( 0 ).getCombinedKey() ) )
				.andExpect( jsonPath( "$[0].incidentRate" ).value( responseContent.get( 0 ).getIncidentRate() ) )
				.andExpect( jsonPath( "$[0].caseFatalityRatio" ).value( responseContent.get( 0 ).getCaseFatalityRatio() ) );
	}
}
