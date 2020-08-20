package com.example.EmployeeManagement.dao;

import com.example.EmployeeManagement.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {

}
