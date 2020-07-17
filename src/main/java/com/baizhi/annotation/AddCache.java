package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  //切的是方法
@Retention(RetentionPolicy.RUNTIME)  //何时生效
public @interface AddCache {
}
