package com.jeecms.core.fastjson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jeecms.core.fastjson.serializer.SerializerFeature;

/**
 * @author wenshao<szujobs@hotmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface JSONType {

    boolean asm() default true;

    String[] orders() default {};
    
    String[] ignores() default {};

    SerializerFeature[] serialzeFeatures() default {};
    
    boolean alphabetic() default true;
    
    Class<?> mappingTo() default Void.class;
}
