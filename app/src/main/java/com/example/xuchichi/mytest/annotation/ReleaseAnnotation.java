package com.example.xuchichi.mytest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xuchichi on 2018/4/8.
 *
 * @Qualifier：主要作用是用来区分不同对象实例
 * @Name 其实是Qualifier的实现
 */
@Qualifier
@Singleton
@Documented
@Retention(RUNTIME)
public @interface ReleaseAnnotation {
}
