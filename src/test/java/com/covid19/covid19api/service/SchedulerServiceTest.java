package com.covid19.covid19api.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.covid19.covid19api.client.FileDownloadClient;
import com.covid19.covid19api.config.ApplicationProperties;

@ExtendWith( MockitoExtension.class )
class SchedulerServiceTest {

	@Mock
	private FileDownloadClient fileDownloadClient;
	@Mock
	private FileReaderService fileReaderService;
	@Mock
	private ApplicationProperties applicationProperties;
	@InjectMocks
	private SchedulerService schedulerService;

	@Test
	void schedule() throws Exception {

		String fileUrl = "/abc/";
		String expectedFileName = "01-01-2021.csv";
		given( applicationProperties.getDataUrl() ).willReturn( fileUrl );
		willDoNothing().given( fileDownloadClient ).downloadFile( fileUrl + expectedFileName, expectedFileName );
		willDoNothing().given( fileReaderService ).readInWindow( expectedFileName );

		schedulerService.schedule();

		then( applicationProperties ).should( times( 1 ) ).getDataUrl();
		then( fileDownloadClient ).should().downloadFile( fileUrl + expectedFileName, expectedFileName );
		then( fileReaderService ).should().readInWindow( expectedFileName );

	}

}