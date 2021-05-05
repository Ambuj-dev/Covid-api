package com.covid19.covid19api.client;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class FileDownloadClientTest {

	private FileDownloadClient fileDownloadClient = new FileDownloadClient();

	@Test
	void downloadFile() throws IOException {

		String fileUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
		String expectedFileName = "01-01-2021.csv";

		fileDownloadClient.downloadFile( fileUrl + expectedFileName, expectedFileName );

		assertTrue( true );
	}
}