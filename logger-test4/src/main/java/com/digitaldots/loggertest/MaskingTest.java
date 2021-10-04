package com.digitaldots.loggertest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaskingTest {
    private static final Logger logger = LogManager.getLogger(MaskingTest.class);

    public static void main(String[] args) {

        logger.info(LoggingMarkers.JSON, "{'SSN': '123456789'}");
        logger.info(LoggingMarkers.XML, "{'SSN': '987654321'}");

        logger.info(LoggingMarkers.JSON, "{'CREDIT_CARD': '1234567891111111'}");
        logger.info(LoggingMarkers.XML, "{'CREDIT_CARD': '1234567891111111'}");

        logger.info(LoggingMarkers.JSON, "{'CVV': '111'}");
        logger.info(LoggingMarkers.XML, "{'CVV': '111'}");

    }
}