package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dao.EmployeeDao;
import com.example.EmployeeManagement.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeDao employeeDao;

    public void addEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    public boolean updateEmployee(int id, Employee employee) {
        if(!employeeDao.findById(id).isPresent()) {
            return false;
        }
        employee.setId(id);
        employeeDao.save(employee);
        return true;
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

}
