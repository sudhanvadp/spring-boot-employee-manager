package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dao.EmployeeRepository;
import com.example.EmployeeManagement.dao.RedisRepository;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.entity.Employee;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RedisRepository redisRepository;

    public void addEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeDto.convertToEmployee();
        employeeRepository.save(employee);

        redisRepository.save(employee);
    }

    public void updateEmployee(UUID id, EmployeeDto employeeDto) {
        Employee employee = employeeDto.convertToEmployee();
        
        if(!employeeRepository.findById(id).isPresent()) {
            return;
        }
        employee.setId(id);
        employeeRepository.save(employee);

        redisRepository.save(employee);
    }

    public void deleteEmployee(UUID id) {
        if(!employeeRepository.findById(id).isPresent()) {
            return;
        }
        employeeRepository.deleteById(id);

        redisRepository.delete(id);
    }

    public List<EmployeeDto> getAllEmployeesDB() {
        logger.info("Access db for all employees");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employeeRepository.findAll().forEach( (employee) ->  employeeDtos.add(employee.convertToEmployeeDto()));
        return employeeDtos;
    }

    public List<EmployeeDto> getAllEmployeesCache() {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        redisRepository.findAll().values().forEach( (employee) ->  employeeDtos.add(employee.convertToEmployeeDto()));
        return employeeDtos;
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employeeDtosCache= getAllEmployeesCache();
        return employeeDtosCache.isEmpty()? getAllEmployeesDB(): employeeDtosCache;
    }

    public EmployeeDto getEmployeeDB(UUID id) {
        logger.info("Access db for all employee "+ id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Optional<Employee> employee= employeeRepository.findById(id);
        return employee.map(Employee::convertToEmployeeDto).orElse(null);
    }

    public EmployeeDto getEmployeeCache(UUID id) {
        Employee employee = redisRepository.findById(id);
        return employee==null? null : employee.convertToEmployeeDto();
    }

    public EmployeeDto getEmployee(UUID id) {
        EmployeeDto employeeDtoCache = getEmployeeCache(id);
        return employeeDtoCache==null?  getEmployeeDB(id): employeeDtoCache;
    }

}
