package com.covid19.covid19api.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.covid19.covid19api.client.FileDownloadClient;
import com.covid19.covid19api.config.ApplicationProperties;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class SchedulerService {

	private final FileDownloadClient fileDownloadClient;
	private final FileReaderService fileReaderService;
	private final ApplicationProperties applicationProperties;

	@Scheduled( cron = "0 * * ? * *" )
	public void schedule() throws Exception {
		// Datas not available for all day
		// String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy") );
		String date = "01-01-2021";
		String fileName = String.format( "%s%s", date, ".csv" );
		String dataUrl = String.format( "%s%s", applicationProperties.getDataUrl(), fileName );

		long startTime = System.currentTimeMillis();
		fileDownloadClient.downloadFile( dataUrl, fileName );
		log.info( "File Downloaded in " + ( System.currentTimeMillis() - startTime ) );

		startTime = System.currentTimeMillis();
		fileReaderService.readInWindow( fileName );
		log.info( "File Read in " + ( System.currentTimeMillis() - startTime ) );
	}
}
