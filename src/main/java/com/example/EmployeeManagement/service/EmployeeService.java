package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.RabbitMQ.RabbitMQConfiguration;
import com.example.EmployeeManagement.dao.EmployeeDao;
import com.example.EmployeeManagement.model.Employee;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class EmployeeService {
    private final EmployeeDao employeeDao;
    private final RabbitTemplate rabbitTemplate;
    Gson gson = new Gson();

    public void addEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    public void updateEmployee(int id, Employee employee) {
        String message = id + "$"+gson.toJson(employee);
        System.out.println("Sending message for employee updation : "+ message);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName, "sb.employee.update", message);
    }

    public boolean deleteEmployee(int id) {
        if(!employeeDao.findById(id).isPresent()) {
            return false;
        }
        employeeDao.deleteById(id);
        return true;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeeDao.findAll().forEach(employees::add);
        return employees;
    }

    public Employee getEmployee(int id) {
        Optional<Employee> employee= employeeDao.findById(id);
        return employee.orElse(null);
    }

}
