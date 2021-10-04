package com.digitaldots.loggertest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaskingTest {
    private static final Logger logger = LogManager.getLogger(MaskingTest.class);

    public static void main(String[] args) {

        logger.info("This is an info message.");
        logger.warn("This is a warn message.");
        logger.error("This is an error message.");
        logger.fatal("This is a fatal message.");
        logger.debug("This is a debug message.");
        logger.trace("This is a trace message.");

        logger.error("user : DummyPassword@123");
        logger.error("password : DummyPassword@123");
        logger.error("card context set for creditCard={}", "1111111111111");
//        logger.error("{'SSN': '123456789'}"); // Will NOT mask
        logger.error(LoggingMarkers.JSON, "{'SSN': '123456789'}"); // Will mask
        logger.error(LoggingMarkers.XML, "{'SSN': '123456789'}"); // Will try to mask, but probably won't work for this message

    }
}