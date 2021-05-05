package com.covid19.covid19api.logging.aspect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ServiceLoggingAspect implements BaseLoggingAspect {

	public static final String FIND_ALL = ".findAll";

	@Around( "@within(org.springframework.stereotype.Service)&&" +
			"(execution(public * *(..)))" )
	public Object logAroundServicePublicMethod( ProceedingJoinPoint joinPoint ) throws Throwable {
		Method method = getMethod( joinPoint );
		String methodFullName = getMethodFullName( joinPoint );
		log.info( "Service method {} called with param {}",
				methodFullName,
				joinPoint.getArgs() );

		Object result = joinPoint.proceed();
		if ( method.getReturnType().equals( Void.TYPE ) ) {
			log.info( "Service method {} ended.", methodFullName );
		} else if ( methodFullName.endsWith( FIND_ALL ) ) {
			int count = Objects.isNull( result ) ? 0 : ( (List) result ).size();
			log.info( "Service method {} ended. Response: Counted: {}", methodFullName, count );
		} else {
			log.info( "Service method {} ended. Response: {}", methodFullName, result );

		}
		return result;
	}
}
