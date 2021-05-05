package com.covid19.covid19api.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CovidDataRequest {

	private String provinceState;
	private String countryRegion;

	@DateTimeFormat( iso = DateTimeFormat.ISO.DATE )
	private LocalDate startDate;
	@DateTimeFormat( iso = DateTimeFormat.ISO.DATE )
	private LocalDate endDate;
	private Frequency frequency;

}
