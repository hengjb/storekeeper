/*
 * Exceptions.java
 * 2019年8月15日 下午5:24:56
 * Copyright 2017 Fosun Financial. All  Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit         
 * www.fosun.com 
 * if you need additional information or have any questions.
 * @author hengjb
 * @version 1.0
 */

package com.fosun.storekeeper.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理
 * @version   
 * @author hengjb 2019年8月15日下午5:24:56
 * @since 1.8
 */
public class Exceptions {

    public static RuntimeException unchecked(Throwable ex) {
        return ex instanceof RuntimeException ? (RuntimeException) ex : new RuntimeException(ex);
    }

    public static String getStackTraceAsString(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String getErrorMessageWithNestedException(Throwable ex) {
        Throwable nestedException = ex.getCause();
        return ex.getMessage() + " nested exception is " + nestedException.getClass().getName() + ":"
                + nestedException.getMessage();
    }

    public static Throwable getRootCause(Throwable ex) {
        Throwable cause;
        while ((cause = ex.getCause()) != null) {
            ex = cause;
        }

        return ex;
    }

    public static boolean isCausedBy(Exception ex, Class... causeExceptionClasses) {
        for (Object cause = ex; cause != null; cause = ((Throwable) cause).getCause()) {
            Class[] arg2 = causeExceptionClasses;
            int arg3 = causeExceptionClasses.length;

            for (int arg4 = 0; arg4 < arg3; ++arg4) {
                Class causeClass = arg2[arg4];
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
        }
        return false;
    }

}
