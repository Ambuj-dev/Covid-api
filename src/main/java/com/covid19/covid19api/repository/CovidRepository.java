package com.covid19.covid19api.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.covid19.covid19api.model.CovidData;

public interface CovidRepository extends ElasticsearchRepository<CovidData, String>, CovidRepositoryCustom {

	List<CovidData> findByActiveGreaterThan( Long active );
}
