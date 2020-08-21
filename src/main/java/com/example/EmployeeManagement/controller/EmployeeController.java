package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.RabbitMQ.RabbitMQConfiguration;
import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.service.EmployeeService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final RabbitTemplate rabbitTemplate;
    Gson gson = new Gson();

    @PostMapping("/add")
    public String addEmployee(@RequestBody Employee employee) {
        String message = gson.toJson(employee);
        System.out.println("Sending message for employee addition : "+ message);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName, "employee.add", message);
        return "Requested for addition";
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @PutMapping("/{id}")
    public String updateEmployee(@RequestBody Employee employee, @PathVariable int id) {
        String message = id + "$"+gson.toJson(employee);
        System.out.println("Sending message for employee updation : "+ message);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName, "employee.update", message);
        return "Requested for updation";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        String message = id+"";
        System.out.println("Sending message for employee deletion : "+ message);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName, "employee.delete", message);
        return "Requested for deletion";
    }

}
