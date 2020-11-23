/*
 * Clock.java
 * 2019年8月16日 上午10:20:58
 * Copyright 2017 Fosun Financial. All  Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit         
 * www.fosun.com 
 * if you need additional information or have any questions.
 * @author hengjb
 * @version 1.0
 */

package com.fosun.storekeeper.util;

import java.util.Date;

/**
 * 时钟工具类
 * @version   
 * @author hengjb 2019年8月16日上午10:20:58
 * @since 1.8
 */
public interface Clock {

    Clock DEFAULT = new Clock.DefaultClock();

    /**
     * get the current date
     * @return
     */
    Date getCurrentDate();

    /**
     * get the current time millis
     * @return
     */
    long getCurrentTimeInMillis();

    public static class MockClock implements Clock {
        private long time;

        public MockClock() {
            this(0L);
        }

        public MockClock(Date date) {
            this.time = date.getTime();
        }

        public MockClock(long time) {
            this.time = time;
        }

        @Override
        public Date getCurrentDate() {
            return new Date(this.time);
        }

        @Override
        public long getCurrentTimeInMillis() {
            return this.time;
        }

        public void update(Date newDate) {
            this.time = newDate.getTime();
        }

        public void update(long newTime) {
            this.time = newTime;
        }

        public void increaseTime(int millis) {
            this.time += (long) millis;
        }

        public void decreaseTime(int millis) {
            this.time -= (long) millis;
        }
    }

    public static class DefaultClock implements Clock {
        @Override
        public Date getCurrentDate() {
            return new Date();
        }

        @Override
        public long getCurrentTimeInMillis() {
            return System.currentTimeMillis();
        }
    }

}
