package com.digitaldots.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAO {
    private static Employees list = new Employees();
    private static final Logger LOGGER = LogManager.getLogger(EmployeeDAO.class);
    
    static {
        list.getEmployeeList().add(new Employee(101, "Gitanjali", "Verma", "gverma@digitaldots.io"));
        list.getEmployeeList().add(new Employee(102, "Vibhuti", "Agrawal", "vibhuti@gmail.com"));
        list.getEmployeeList().add(new Employee(103, "Ruchi", "Verma", "ruchi@gmail.com"));
    }

    public Employees getAllEmployees() {
        return list;
    }

    public Employees getEmployee() {
        LOGGER.debug("This is a debug message.");
        LOGGER.info("This is an info message.");
        LOGGER.warn("This is a warn message.");
        LOGGER.error("This is an error message.");
        LOGGER.fatal("This is a fatal message.");
        return list;
    }

//	public boolean getEmployee(Integer id) {
//		
//		Iterator<Employee> itr=((List<Employee>) list).iterator();
//		while(itr.hasNext()) {
//			Employee value=itr.next();
//			if(value==id) {
//				return 
//			}
//		}
//		return list.getEmployeeList().contains(id);
//	}

    public void addEmployee(Employee employee) {
        list.getEmployeeList().add(employee);
    }
}
