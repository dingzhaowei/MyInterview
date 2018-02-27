package com.zuora.test.exam;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ FIELD })
@Retention(RUNTIME)
public @interface NumRange {

    /**
     * The minimum value, included
     *
     * @return
     */
    double min() default Double.MIN_VALUE;

    /**
     * The maximum value, included
     *
     * @return
     */
    double max() default Double.MAX_VALUE;

}
