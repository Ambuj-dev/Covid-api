package com.covid19.covid19api.logging.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public interface BaseLoggingAspect {

	default String getMethodFullName( ProceedingJoinPoint joinPoint ) {
		Method method = ( (MethodSignature) joinPoint.getStaticPart().getSignature() ).getMethod();
		return method.getDeclaringClass().getCanonicalName() + "." + method.getName();
	}

	default Method getMethod( ProceedingJoinPoint joinPoint ) {
		return ( (MethodSignature) joinPoint.getStaticPart().getSignature() ).getMethod();
	}
}
