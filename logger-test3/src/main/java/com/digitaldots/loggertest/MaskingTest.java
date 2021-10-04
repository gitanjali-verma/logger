package com.digitaldots.loggertest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaskingTest {
//    private static final Logger logger = LogManager.getLogger(MaskingTest.class);

    private static final Logger logger = LogManager.getLogger(MaskingTest.class);

    public static void myTest() {
        logger.error("this is my info message");
        logger.error("This is debug message");
        logger.error(
            "Passed to server::0084USER:17603,IP:0:0:0:0:0:0:0:1,3425,Credit Card 1:1000002367844224,3425,Credit Card2:1000002367844224 , CVV:234,SSN:123456789");

    }

    public static void main(String[] args) {
        myTest();
    }
}