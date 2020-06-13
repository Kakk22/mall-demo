package com.cyf.malldemo.aspect;

import com.cyf.malldemo.annotation.CacheException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/** redis 缓存切面类，防止redis宕机影响正常业务
 * @author by cyf
 * @date 2020/5/25.
 */
@Aspect
@Component
@Order(2)
public class RedisCacheAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Pointcut("execution(public * com.cyf.malldemo.service.*CacheService.*(..))")
    public void cacheAspect(){
    }
    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //拿到method 可以进行反射
        Method method = methodSignature.getMethod();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            //判断方法是否有CacheException注解，有就抛出
            if (method.isAnnotationPresent(CacheException.class)){
                throw  throwable;
            }else {
                LOGGER.error(throwable.getMessage());
            }
        }
        return result;
    }
}
