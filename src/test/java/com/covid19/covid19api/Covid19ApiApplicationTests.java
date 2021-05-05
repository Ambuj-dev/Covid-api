package com.covid19.covid19api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Covid19ApiApplicationTests {

	/*
	 * Constants
	 */
	public static final String ARG_SERVER_PORT_RANDOM = "--server.port=0";

	/**
	 * Test web application mode.
	 */
	@DisplayName( "Run in web application mode" )
	@Test
	void main_Web() {
		// Note: Use a random port to avoid collision with running application when local development.
		Covid19ApiApplication.main( new String[] {
				ARG_SERVER_PORT_RANDOM
		} );

		// Ran successfully if it gets here...
		Assertions.assertTrue( true );
	}
}
