package com.example.EmployeeManagement.dao;

import com.example.EmployeeManagement.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class RedisRepositoryImpl implements  RedisRepository {
    @Autowired
    private final RedisTemplate<String, Employee> redisTemplate;
    private final HashOperations<String, UUID, Employee> hashOperations;

    public RedisRepositoryImpl(RedisTemplate<String, Employee> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(Employee employee) {
        hashOperations.put("EMPLOYEE",employee.getId(),employee);
    }
    @Override
    public Map<UUID, Employee> findAll() {
        return hashOperations.entries("EMPLOYEE");
    }
    @Override
    public Employee findById(UUID id) {
        return hashOperations.get("EMPLOYEE",id);
    }
    @Override
    public void update(Employee employee) {
        save(employee);
    }

    @Override
    public void delete(UUID id) {
        hashOperations.delete("EMPLOYEE",id);
    }
}