package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto implements Serializable {
    private UUID id;
    private String name;
    private String designation;

    public Employee convertToEmployee() {
        return new Employee(id, name, designation);
    }
}
