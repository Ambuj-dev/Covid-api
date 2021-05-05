package com.covid19.covid19api.logging.aspect;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ControllerLoggingAspect implements BaseLoggingAspect {

	@Around( "@annotation(org.springframework.web.bind.annotation.GetMapping)" )
	public Object logAroundAnyGetMapping( ProceedingJoinPoint joinPoint ) throws Throwable {
		return logRequestAndResponse( joinPoint, "GET" );
	}

	private Object logRequestAndResponse( ProceedingJoinPoint joinPoint, String methodName ) throws Throwable {
		Object[] args = joinPoint.getArgs();

		String methodFullName = getMethodFullName( joinPoint );
		log.info( methodName + " Request {}, with parameters: {}",
				methodFullName, getMethodArgValues( args ) );

		Object result = joinPoint.proceed();
		if ( result == null ) {
			log.info( methodName + " Request {} has been completed", methodFullName );
		} else if ( ArrayList.class.equals( result.getClass() ) ) {
			log.info( methodName + " Request {}, response body counted {}",
					methodFullName, ( (List) result ).size() );
		} else if ( ResponseEntity.class.equals( result.getClass() ) ) {
			log.info( methodName + " Request {} result status: {}, response body {}",
					methodFullName, ( (ResponseEntity) result ).getStatusCode(), getResponseBody( result ) );
		} else {
			log.warn( "Invalid response entity. Response: {}", result );
		}

		return result;
	}

	private String getMethodArgValues( Object[] args ) {
		List<Object> argList = asList( args );
		if ( argList == null || argList.isEmpty() ) {
			return "<empty>";
		}
		return argList.stream().map( arg -> arg != null ? arg.toString() : "" ).collect( Collectors.joining( "," ) );
	}

	private String getResponseBody( Object result ) {
		return ( (ResponseEntity) result ).getBody() == null ? "" : ( (ResponseEntity) result ).getBody().toString();
	}
}