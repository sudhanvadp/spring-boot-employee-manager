package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dao.EmployeeDao;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.model.Employee;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class EmployeeService {
    private final EmployeeDao employeeDao;

    public void addEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeDto.convertToEmployee();
        employeeDao.save(employee);
    }

    public void updateEmployee(int id, EmployeeDto employeeDto) {
        Employee employee = employeeDto.convertToEmployee();
        
        if(!employeeDao.findById(id).isPresent()) {
            return;
        }
        employee.setId(id);
        employeeDao.save(employee);
    }

    public void deleteEmployee(int id) {
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

    public EmployeeDto getEmployee(int id) {
        Optional<Employee> employee= employeeDao.findById(id);
        return employee.map(Employee::convertToEmployeeDto).orElse(null);
    }

}
