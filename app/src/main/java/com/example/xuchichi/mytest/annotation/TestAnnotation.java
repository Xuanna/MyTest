package com.example.xuchichi.mytest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xuchichi on 2018/4/8.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface TestAnnotation {
}
