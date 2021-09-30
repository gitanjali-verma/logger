package com.digitaldots.loggertest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaskingTest {
    private static final Logger logger = LogManager.getLogger(MaskingTest.class);

    public static void main(String[] args) {
        logger.info("Received sign-up request, password = DummyPassword@123");
        logger.info("card context set for creditCard={}", "1111111111111");
    }
}