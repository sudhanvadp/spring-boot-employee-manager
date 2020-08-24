package com.example.EmployeeManagement.model;

import com.example.EmployeeManagement.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;
    String name;
    String designation;

    public EmployeeDto convertToEmployeeDto() {
        return new EmployeeDto(id, name, designation);
    }
}
