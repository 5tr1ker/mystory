package com.team.mystory.preferences;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeStamp {
	
	@Pointcut("execution(* com.team.mystory.controller..*.*(..))")
	public void pointcut() {}
	
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		
		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		System.out.println("---------TimeStamp--------");
		long beforeTime = System.currentTimeMillis();
		

		Object obj = jp.proceed();
		long afterTime = System.currentTimeMillis();
		System.out.println("_______________________________________________________\n");
		System.out.println("대상 클래스 : " + className + " \n대상 메소드 : " + methodName + " \n측정 시간 : " + (afterTime - beforeTime) + "ms");
		System.out.println("_______________________________________________________");
		
		return obj;
	}
}
