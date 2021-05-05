package com.covid19.covid19api.response;

import java.time.LocalDate;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CovidDataResponse {

	private LocalDate createdDate;
	private String FIPS;
	private String admin2;
	private String provinceState;
	private String countryRegion;

	private GeoPoint location;
	private Long confirmed;
	private Long deaths;
	private Long recovered;
	private Long active;
	private String combinedKey;
	private Double incidentRate;
	private Double caseFatalityRatio;
}
