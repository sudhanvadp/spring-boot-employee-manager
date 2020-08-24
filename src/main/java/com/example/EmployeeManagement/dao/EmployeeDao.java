package com.example.EmployeeManagement.dao;

import com.example.EmployeeManagement.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, UUID> {

}
