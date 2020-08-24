package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dao.EmployeeDao;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.entity.Employee;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public void addEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeDto.convertToEmployee();
        employeeDao.save(employee);
    }

    public void updateEmployee(UUID id, EmployeeDto employeeDto) {
        Employee employee = employeeDto.convertToEmployee();
        
        if(!employeeDao.findById(id).isPresent()) {
            return;
        }
        employee.setId(id);
        employeeDao.save(employee);
    }

    public void deleteEmployee(UUID id) {
        if(!employeeDao.findById(id).isPresent()) {
            return;
        }
        employeeDao.deleteById(id);
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employeeDao.findAll().forEach( (employee) ->  employeeDtos.add(employee.convertToEmployeeDto()));
        return employeeDtos;
    }

    public EmployeeDto getEmployee(UUID id) {
        Optional<Employee> employee= employeeDao.findById(id);
        return employee.map(Employee::convertToEmployeeDto).orElse(null);
    }

}
