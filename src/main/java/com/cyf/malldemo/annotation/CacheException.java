package com.cyf.malldemo.annotation;

import java.lang.annotation.*;

/**
 * @author by cyf
 * @date 2020/5/27.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
