package com.digitaldots.demo;

import java.net.URI;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

    @GetMapping
    public Employees getEmployees() {
        return employeeDAO.getEmployee();
    }

    @GetMapping(path = "/{id}")
    public Employees getSelectedEmployees(@PathVariable Integer id, @RequestParam(required = false) String name) {
        return employeeDAO.getAllEmployees();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {

        // Generate resource id
        Integer id = employeeDAO.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);

        // add resource
        employeeDAO.addEmployee(employee);

        // Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(employee.getId()).toUri();
        // Send location in response
        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/json")
    public JSONObject json(@RequestBody Map<String, String> user) {
        JSONObject userDetails = new JSONObject(user);
        logger.info("User JSON: {}", userDetails);
        return userDetails;
    }

}
