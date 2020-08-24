package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EmployeeDto implements Serializable {
    int id;
    String name;
    String designation;

    public Employee convertToEmployee() {
        return new Employee(id, name, designation);
    }
}
