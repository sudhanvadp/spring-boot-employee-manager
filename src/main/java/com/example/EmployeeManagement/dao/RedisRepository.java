package com.example.EmployeeManagement.dao;

import com.example.EmployeeManagement.entity.Employee;

import java.util.Map;
import java.util.UUID;

public interface RedisRepository {
    void save(Employee employee);
    Map<UUID, Employee> findAll();
    Employee findById(UUID id);
    void update(Employee employee);
    void delete(UUID id);
}
