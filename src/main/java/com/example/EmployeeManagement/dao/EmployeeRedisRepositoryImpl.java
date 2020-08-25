package com.example.EmployeeManagement.dao;

import com.example.EmployeeManagement.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class EmployeeRedisRepositoryImpl implements EmployeeRedisRepository {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeRedisRepositoryImpl.class);

    @Autowired
    private RedisTemplate<String, Employee> redisTemplate;

    int expirySeconds = 30;
    @Override
    public void save(Employee employee) {
        redisTemplate.opsForValue().set("employees_"+employee.getId(), employee);
        redisTemplate.expire("employees_"+employee.getId(), expirySeconds, TimeUnit.SECONDS);
    }

    @Override
    public Map<UUID, Employee> findAll() {
        Map<UUID, Employee> ans = new HashMap<>();
        Set<String> set = redisTemplate.keys("employees_*");
        for(String key: set) {
            ans.put(UUID.fromString(key.substring(11)), redisTemplate.opsForValue().get(key));
            redisTemplate.expire(key, expirySeconds, TimeUnit.SECONDS);
        }
        return ans;
    }

    @Override
    public Employee findById(UUID id) {
        Employee employee = redisTemplate.opsForValue().get("employees_"+ id.toString());

        if(employee!=null)
            redisTemplate.expire("employees_"+employee.getId(), expirySeconds, TimeUnit.SECONDS);
        return employee;
    }

    @Override
    public void update(Employee employee) {
        save(employee);
    }

    @Override
    public void delete(UUID id) {
        redisTemplate.delete("employees_"+id);
    }
}