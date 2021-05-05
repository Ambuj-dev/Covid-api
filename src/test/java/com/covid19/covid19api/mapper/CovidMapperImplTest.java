package com.covid19.covid19api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.response.CovidDataResponse;
import com.covid19.covid19api.response.CovidDecisionResponse;

//@ExtendWith( MockitoExtension.class )
class CovidMapperImplTest {

	private CovidMapperImpl mapper = new CovidMapperImpl();

	@Test
	void covidDataToCovidDecisionResponse() {
		// Given
		CovidData covidData = CovidProvider.getCovidData();
		Boolean readyForWorkFromOffice = true;

		// When
		CovidDecisionResponse response = mapper.covidDataToCovidDecisionResponse( covidData, readyForWorkFromOffice );

		assertNotNull( response );
		assertEquals( readyForWorkFromOffice, response.getReadyForWorkFromOffice() );
	}

	@Test
	void covidDataToCovidDecisionResponseGivenNullData() {
		// Given
		CovidData covidData = null;
		Boolean readyForWorkFromOffice = null;

		// When
		CovidDecisionResponse response = mapper.covidDataToCovidDecisionResponse( covidData, readyForWorkFromOffice );

		assertNull( response );
	}

	@Test
	void covidDataToCovidDataResponse() {

		// Given
		CovidData covidData = CovidProvider.getCovidData();

		// When
		CovidDataResponse response = mapper.covidDataToCovidDataResponse( covidData );

		assertNotNull( response );
	}

	@Test
	void covidDataToCovidDataResponseGivenNullData() {

		// Given
		CovidData covidData = null;

		// When
		CovidDataResponse response = mapper.covidDataToCovidDataResponse( covidData );

		assertNull( response );
	}

	@Test
	void CovidDataToCovidDataResponseList() {

		// Given
		List<CovidData> covidDataList = CovidProvider.getCovidDatas();

		// When
		List<CovidDataResponse> response = mapper.covidDataToCovidDataResponse( covidDataList );

		assertNotNull( response );
	}

	@Test
	void CovidDataToCovidDataResponseListGiveNullRequest() {

		// Given
		List<CovidData> covidDataList = null;

		// When
		List<CovidDataResponse> response = mapper.covidDataToCovidDataResponse( covidDataList );

		assertNull( response );
	}
}