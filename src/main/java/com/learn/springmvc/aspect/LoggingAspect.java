package com.learn.springmvc.aspect;

import java.util.Collection;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("@annotation(Loggable)")
  public void executeLogging() {
    //method for using joinPoint
  }

  @AfterReturning(value = "executeLogging()", returning = "returnValue")
  public void logMethodCall(JoinPoint joinPoint, Object returnValue) {

    if (returnValue instanceof Collection) {
      LOGGER.info("MethodName={} {}, returns: {} elements",
          joinPoint.getSignature().getName(), joinPoint.getArgs(), ((Collection) returnValue).size());
    } else {
      LOGGER.info("MethodName={} {}, returns: {}",
          joinPoint.getSignature().getName(), joinPoint.getArgs(), returnValue);
    }
  }
}
