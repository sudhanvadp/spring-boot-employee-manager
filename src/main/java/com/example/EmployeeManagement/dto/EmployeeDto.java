package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EmployeeDto implements Serializable {
    UUID id;
    String name;
    String designation;

    public Employee convertToEmployee() {
        return new Employee(id, name, designation);
    }
}
