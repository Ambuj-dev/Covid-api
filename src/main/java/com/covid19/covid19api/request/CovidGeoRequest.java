package com.covid19.covid19api.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CovidGeoRequest {

	@NotNull
	private Double latitude;
	@NotNull
	private Double longitude;
	@NotNull
	private Double distanceInKm;
	@NotNull
	private Long totalPopulation;
	@NotNull
	private Long threshHold;

}
