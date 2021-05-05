package com.covid19.covid19api.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document( indexName = "covid19", createIndex = true )
public class CovidData {

	@Id
	private String id;
	@Field( type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd" )
	private LocalDate createdDate;
	private String FIPS;
	private String admin2;
	private String provinceState;
	private String countryRegion;
	private String lastUpdate;
	@GeoPointField
	private GeoPoint location;
	private Long confirmed;
	private Long deaths;
	private Long recovered;
	private Long active;
	private String combinedKey;
	private Double incidentRate;
	private Double caseFatalityRatio;

}
