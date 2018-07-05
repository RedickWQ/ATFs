package org.automation.atfs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangqiang on 2018/7/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AutomationTestCase {
    String name() default  "";
    String author() default "";
    String description() default "";
//    TestcaseType testcaseType() default  TestcaseType.WEB_API_TEST;
    String[] tags() default {};
}
