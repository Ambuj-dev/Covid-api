package com.covid19.covid19api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.covid19.covid19api.CovidProvider;
import com.covid19.covid19api.repository.CovidRepository;

@ExtendWith( MockitoExtension.class )
class FileReaderServiceTest {

	@Mock
	private CovidRepository covidRepository;

	@InjectMocks
	private FileReaderService fileReaderService;

	@Test
	void readInWindow() throws IOException {
		given( covidRepository.saveAll( any() ) ).willReturn( CovidProvider.getCovidDatas() );

		fileReaderService.readInWindow( "01-03-2021.csv" );

		then( covidRepository ).should( times( 4 ) ).saveAll( any() );
	}

	@Test
	void readFile() throws IOException {
		given( covidRepository.save( any() ) ).willReturn( CovidProvider.getCovidData() );

		fileReaderService.readFile( "01-02-2021.csv" );

		then( covidRepository ).should( times( 10 ) ).save( any() );
	}
}