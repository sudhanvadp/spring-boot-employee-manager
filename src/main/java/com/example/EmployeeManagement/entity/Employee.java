package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    private String designation;

    public EmployeeDto convertToEmployeeDto() {
        return new EmployeeDto(id, name, designation);
    }
}
