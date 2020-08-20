package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/add")
    public String addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return "Success";
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public String updateEmployee(@RequestBody Employee employee, @PathVariable int id) {
        boolean res = employeeService.updateEmployee(id, employee);
        return res? "Success": "Failure";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        boolean res = employeeService.deleteEmployee(id);
        return res? "Success": "Failure";
    }

}
