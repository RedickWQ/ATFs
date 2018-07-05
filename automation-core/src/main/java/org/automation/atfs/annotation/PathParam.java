package org.automation.atfs.annotation;

import java.lang.annotation.*;

/**
 * Created by wangqiang on 2018/6/25
 */

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathParam {
    String value();
}
