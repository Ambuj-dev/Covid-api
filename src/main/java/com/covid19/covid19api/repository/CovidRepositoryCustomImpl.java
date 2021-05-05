package com.covid19.covid19api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.request.CovidDataRequest;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class CovidRepositoryCustomImpl implements CovidRepositoryCustom {

	private final ElasticsearchOperations operations;

	@Override
	public List<SearchHit<CovidData>> searchWithin( GeoPoint geoPoint, Double distance, String unit ) {
		Query query = new CriteriaQuery(
				new Criteria( "location" ).within( geoPoint, distance.toString() + unit ) );
		Sort sort = Sort.by( new GeoDistanceOrder( "location", geoPoint ).withUnit( unit ) );
		query.addSort( sort );
		return operations.search( query, CovidData.class ).getSearchHits();
	}

	@Override
	public List<SearchHit<CovidData>> searchByProvinceStateAndCountryRegionAndCreateDate( CovidDataRequest request ) {

		Criteria criteria = new Criteria();

		if ( Objects.nonNull( request.getProvinceState() ) && !request.getProvinceState().isBlank() ) {
			criteria.and( new Criteria( "provinceState" ).is( request.getProvinceState() ) );
		}
		if ( Objects.nonNull( request.getCountryRegion() ) && !request.getCountryRegion().isBlank() ) {
			criteria.and( new Criteria( "countryRegion" ).is( request.getCountryRegion() ) );
		}
		if ( Objects.nonNull( request.getStartDate() ) ) {
			criteria.and( new Criteria( "createdDate" ).greaterThanEqual( request.getStartDate() ) );
		}
		if ( Objects.nonNull( request.getEndDate() ) ) {
			criteria.and( new Criteria( "createdDate" ).lessThanEqual( request.getEndDate() ) );
		}
		if ( Objects.nonNull( request.getStartDate() ) && Objects.nonNull( request.getEndDate() ) ) {
			criteria.and( new Criteria( "createdDate" ).greaterThanEqual( request.getStartDate() ).lessThanEqual( request.getEndDate() ) );
		}
		if ( Objects.nonNull( request.getFrequency() ) ) {
			LocalDate currentDate = LocalDate.now();
			switch ( request.getFrequency() ) {
				case Daily:
					criteria.and( new Criteria( "createdDate" ).is( currentDate ) );
					break;
				case Weekly:
					criteria.and( new Criteria( "createdDate" ).between( currentDate.minusWeeks( 1 ), currentDate ) );
					break;
				case Monthly:
					criteria.and( new Criteria( "createdDate" ).between( currentDate.minusMonths( 1 ), currentDate ) );
					break;
			}
		}
		CriteriaQuery query = new CriteriaQuery( criteria );
		return operations.search( query, CovidData.class ).getSearchHits();
	}
}
