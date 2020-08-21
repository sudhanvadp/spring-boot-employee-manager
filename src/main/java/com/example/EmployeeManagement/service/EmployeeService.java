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
    Gson gson = new Gson();

    public void addEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    public void updateEmployee(int id, Employee employee) {
        if(!employeeDao.findById(id).isPresent()) {
            return;
        }
        employee.setId(id);
        employeeDao.save(employee);
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
