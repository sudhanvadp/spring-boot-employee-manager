package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.RabbitMQ.Publisher;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.kafka.KafkaSender;
import com.example.EmployeeManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private KafkaSender kafkaSender;

    @PostMapping("/add")
    public String addEmployee(@RequestBody EmployeeDto employeeDto) {
        kafkaSender.kafkaAddEmployee(employeeDto);
        return "Requested for addition";
    }

    @GetMapping
    public List<EmployeeDto> getEmployees() {
        logger.info("Get all Employees");
        return employeeService.getAllEmployeesDB();
    }

    @GetMapping("/{id}")
//    @Cacheable(value = "empl", key = "#id")
    public EmployeeDto getEmployee(@PathVariable UUID id) {
        logger.info("Get Employee : "+ id);
        EmployeeDto result = employeeService.getEmployee(id);
        logger.info("Get Employee result : "+ result);
        return result;
    }

    @PutMapping("/{id}")
//    @CachePut(value = "empl", key="#id")
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
        employeeDto.setId(id);
        logger.info("Update Employee : "+ id +"\n" +employeeDto.toString());
        kafkaSender.kafkaUpdateEmployee(id, employeeDto);
        return employeeDto;
    }

    @DeleteMapping("/{id}")
//    @CacheEvict(value = "empl", key="#id")
    public String deleteEmployee(@PathVariable UUID id) {
        logger.info("Delete Employee : "+ id);
        kafkaSender.kafkaDeleteEmployee(id);
        return "Requested for deletion";
    }

}
