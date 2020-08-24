package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.RabbitMQ.Publisher;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private Publisher publisher;

    @PostMapping("/add")
    public String addEmployee(@RequestBody EmployeeDto employeeDto) {
        publisher.addEmployee(employeeDto);
        return "Requested for addition";
    }

    @GetMapping
    public List<EmployeeDto> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @PutMapping("/{id}")
    public String updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable int id) {
        publisher.updateEmployee(id, employeeDto);
        return "Requested for updation";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        publisher.deleteEmployee(id);
        return "Requested for deletion";
    }

}
