package com.mustgorestaurant.must_go_restaurant.common.aop;

import com.mustgorestaurant.must_go_restaurant.common.constant.ResponseMessage;
import com.mustgorestaurant.must_go_restaurant.common.exception.DBException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DBExceptionAspect {

    @Around("execution(* com.mustgorestaurant.must_go_restaurant.repository..*(..))")
    public Object handleDBException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DataAccessException e) {
            throw new DBException(ResponseMessage.DB_OPERATION_FAILED.getMessage() + e.getMessage());
        }
    }
}
