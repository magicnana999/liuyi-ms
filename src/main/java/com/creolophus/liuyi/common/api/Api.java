package com.creolophus.liuyi.common.api;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 * @author magicnana
 * @date 2019/9/18 下午2:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Api {


    @AliasFor("scope") Scope value() default Scope.OPEN;

    @AliasFor("value") Scope scope() default Scope.OPEN;

    Limit limit() default Limit.NONE;


    enum Scope {
        INTERNAL,   //Header 中需要一个特殊的值
        OPEN,       //无任何限制的可以访问
        PUBLIC,     //header 中需要 token
        ;
    }

    enum Limit {
        NONE,       //无任何限制
        ;
    }
}