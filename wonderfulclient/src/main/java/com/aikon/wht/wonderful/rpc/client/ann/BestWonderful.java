package com.aikon.wht.wonderful.rpc.client.ann;

import com.aikon.wht.wonderful.rpc.client.http.HttpMethod;

import java.lang.annotation.*;

/**
 * @author haitao.wang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BestWonderful {
    String value() default "";

    HttpMethod verb() default HttpMethod.POST;

    boolean json() default false;
}
