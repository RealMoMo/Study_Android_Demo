package com.realmo.annotation.checkresult;

import android.support.annotation.CheckResult;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/7/12 10:28
 * @describe
 */
public class CheckResultAnnotation {


    /**
     * Denotes that the annotated method returns a result that it typically is
     * an error to ignore. This is usually used for methods that have no side effect,
     * so calling it without actually looking at the result usually means the developer
     * has misunderstood what the method does.
     * <p>
     * Example:
     * <pre>{@code
     *  public @CheckResult String trim(String s) { return s.trim(); }
     *  ...
     *  s.trim(); // this is probably an error
     *  s = s.trim(); // ok
     * }</pre>
     */
}
