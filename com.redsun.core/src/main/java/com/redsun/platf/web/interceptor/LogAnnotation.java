package com.redsun.platf.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: joker pan
 * Date: 13-6-4
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 * <pre>
 * -----------------------------------------------------------------------------
 * Program ID   : com.redsun.platf.web.interceptor
 * Program Name :
 * ----------------------------------------------------------------------------- <pre/>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------
 * 1.0     13-6-4    joker pan       created
 *
 * </pre>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation {
    String operateModelNm();

    String operateFuncNm();

    String operateDescribe();
}

