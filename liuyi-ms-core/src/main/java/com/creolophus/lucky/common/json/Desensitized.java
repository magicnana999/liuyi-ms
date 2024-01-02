package com.creolophus.lucky.common.json;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author magicnana
 * @date 2022/3/10 14:17
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Desensitized {

  Sensitive type();


  enum Sensitive {
    BETWEEN1,
    BETWEEN2,
    BETWEEN3,
    BETWEEN4,
    AFTER1,
    AFTER2,
    AFTER3,
    AFTER4,
    BEFORE1,
    BEFORE2,
    BEFORE3,
    BEFORE4,
    ALL,
    SHORTEN,
    ;
  }
}
