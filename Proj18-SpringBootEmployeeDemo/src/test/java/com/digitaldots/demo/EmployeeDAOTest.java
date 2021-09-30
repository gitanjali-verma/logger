package com.digitaldots.demo;

import org.junit.jupiter.api.Test;

class EmployeeDAOTest {

    @Test
    void test() {
        EmployeeDAO log4J2AsyncLogger = new EmployeeDAO();
        log4J2AsyncLogger.getAllEmployees();
    }

}
