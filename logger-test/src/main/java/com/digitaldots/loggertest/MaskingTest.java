package com.digitaldots.loggertest;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MaskingTest {
    private static final Logger logger = LogManager.getLogger(MaskingTest.class);

    public static void main(String[] args) {
        Map<String, String> user = new HashMap<>();
        user.put("user_id", "87656");
        user.put("SSN", "786445563");
        user.put("address", "22 Street");
        user.put("city", "Raipur");
        user.put("Country", "India");
        user.put("ip_address", "192.168.1.1");
        user.put("email_id", "gitanjali@gmail.com");

        JSONObject userDetails = new JSONObject(user);
        logger.info("User JSON: {}", userDetails);
    }
}