package com.creolophus.liuyi.common.api;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 * @author magicnana
 * @date 2019/9/18 下午2:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Api {

    @AliasFor("scope") Scope[] value() default {};

    @AliasFor("value") Scope[] scope() default {Scope.PUBLIC};

//    String limit() default "";


    enum Scope {
        PARTNER,
        INTERNAL,
        OPEN,
        PUBLIC,
        ;
    }


    public static final Api DEFAULT_INSTANCE = new Api() {
        @Override
        public Class<? extends Annotation> annotationType() {
            return Api.class;
        }

        @Override
        public Scope[] value() {
            return new Scope[];
        }

        @Override
        public Scope[] scope() {
            return new Scope[]{Scope.OPEN};
        }
    };


}