package com.example.xuchichi.mytest.annotation;

/**
 * Created by xuchichi on 2018/4/8.
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope  // 注名是生命周期，一个作用域范围
@Documented //标记在文档
@Retention(RUNTIME) //运行时级别
public @interface ActivityScope {
}
