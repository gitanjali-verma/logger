package com.digitaldots.loggertest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaskingTest {
    private static final Logger logger = LogManager.getLogger(MaskingTest.class);

    public static void main(String[] args) {

        logger.error("user : gili@123");
        logger.error("password : gili@123");
        logger.error("pin : 123");
        logger.info("userid={}, password='{}'", "gili", "gili");
    }
}