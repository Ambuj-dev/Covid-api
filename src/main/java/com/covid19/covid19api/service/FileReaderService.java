package com.covid19.covid19api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.covid19.covid19api.model.CovidData;
import com.covid19.covid19api.repository.CovidRepository;

import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class FileReaderService {

	private static final String COMMA = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

	private final CovidRepository covidRepository;

	public void readInWindow( String fileURL ) throws IOException {
		AtomicLong count = new AtomicLong();
		try ( Stream<String> lines = Files.lines( Paths.get( fileURL ) ) ) {
			Observable.fromIterable( lines::iterator )
					.skip( 1 )
					.filter( StringUtils::hasLength )
					.map( this::getCovidData )
					.filter( Optional::isPresent )
					.map( Optional::get )
					.window( 1000 )
					.map( covidDataObservable -> covidDataObservable.toList().subscribe( covidData -> {
						count.addAndGet( covidData.size() );
						log.info( "saving {} Covid Data, total {}", covidData.size(), count.get() );
						covidRepository.saveAll( covidData );
					} ) )
					.blockingSubscribe();
			log.info( "Finished loading Covid data." );
		}

	}

	public void readFile( String fileURL ) throws IOException {
		BufferedReader reader = Files.newBufferedReader( Paths.get( fileURL ) );
		reader.lines().skip( 1 ).map( this::mapCovidData ).forEach( covidRepository::save );
	}

	private CovidData mapCovidData( String line ) {
		String[] sourceArray = line.split( COMMA, -1 );
		return convertToCovidData( sourceArray );
	};

	private Optional<CovidData> getCovidData( String line ) {
		String[] sourceArray = line.split( COMMA, -1 );
		return Optional.of( convertToCovidData( sourceArray ) );
	}

	private CovidData convertToCovidData( String[] sourceArray ) {
		CovidData covidData = CovidData.builder().build();
		if ( sourceArray.length == 5 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.createdDate( LocalDate.now() ).build();
		} else if ( sourceArray.length == 6 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], "0.0" ) ).createdDate( LocalDate.now() )
					.build();
		} else if ( sourceArray.length == 7 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], sourceArray[6] ) ).createdDate( LocalDate.now() )
					.build();
		} else if ( sourceArray.length == 8 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], sourceArray[6] ) ).confirmed( toLong( sourceArray[7] ) ).createdDate( LocalDate.now() )
					.build();
		} else if ( sourceArray.length == 9 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], sourceArray[6] ) ).confirmed( toLong( sourceArray[7] ) ).deaths( toLong( sourceArray[8] ) ).createdDate( LocalDate.now() )
					.build();
		} else if ( sourceArray.length == 10 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], sourceArray[6] ) ).confirmed( toLong( sourceArray[7] ) ).deaths( toLong( sourceArray[8] ) ).recovered( toLong( sourceArray[9] ) ).createdDate( LocalDate.now() )
					.build();
		} else if ( sourceArray.length == 11 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], sourceArray[6] ) ).confirmed( toLong( sourceArray[7] ) ).deaths( toLong( sourceArray[8] ) ).recovered( toLong( sourceArray[9] ) ).active( toLong( sourceArray[10] ) )
					.createdDate( LocalDate.now() ).build();
		} else if ( sourceArray.length == 12 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], sourceArray[6] ) ).confirmed( toLong( sourceArray[7] ) ).deaths( toLong( sourceArray[8] ) ).recovered( toLong( sourceArray[9] ) ).active( toLong( sourceArray[10] ) )
					.combinedKey( sourceArray[11] ).createdDate( LocalDate.now() ).build();
		} else if ( sourceArray.length == 13 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], sourceArray[6] ) ).confirmed( toLong( sourceArray[7] ) ).deaths( toLong( sourceArray[8] ) ).recovered( toLong( sourceArray[9] ) ).active( toLong( sourceArray[10] ) )
					.combinedKey( sourceArray[11] ).incidentRate( toDouble( sourceArray[12] ) ).createdDate( LocalDate.now() ).build();
		} else if ( sourceArray.length == 14 ) {

			covidData = CovidData.builder().FIPS( sourceArray[0] ).admin2( sourceArray[1] ).provinceState( sourceArray[2] ).countryRegion( sourceArray[3] ).lastUpdate( sourceArray[4] )
					.location( getLocation( sourceArray[5], sourceArray[6] ) ).confirmed( toLong( sourceArray[7] ) ).deaths( toLong( sourceArray[8] ) ).recovered( toLong( sourceArray[9] ) ).active( toLong( sourceArray[10] ) )
					.combinedKey( sourceArray[11] ).incidentRate( toDouble( sourceArray[12] ) ).caseFatalityRatio( toDouble( sourceArray[13] ) ).createdDate( LocalDate.now() ).build();
		}
		return covidData;
	}

	private GeoPoint getLocation( String latitude, String longitude ) {
		return new GeoPoint( toDouble( latitude ), toDouble( longitude ) );
	}

	private Long toLong( String string ) {
		if ( Objects.isNull( string ) || string.isBlank() ) {
			string = "0";
		}
		return Long.parseLong( string );
	}

	private Double toDouble( String string ) {
		if ( Objects.isNull( string ) || string.isBlank() ) {
			string = "0.0";
		}
		return Double.parseDouble( string );
	}

}
