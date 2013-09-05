package com.redsun.platf;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/9/27
 * Time: 上午 11:03
 * To change this template use File | Settings | File Templates.
 */
public class TestSlf4jLogger {

        private static final Logger logger =  LoggerFactory.getLogger(TestSlf4jLogger.class);

        public static void main(String[] args) {
//            logger.info("Hello {}","SLF4J");
            logger.info("Hello ");
            logger.debug("debug ");
            logger.warn("warn ");
        }
}
