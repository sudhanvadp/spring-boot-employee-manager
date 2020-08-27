package com.example.EmployeeManagement.kafka;

import com.example.EmployeeManagement.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void kafkaAddEmployee(EmployeeDto employeeDto) {
        String kafkaTopic = "employeeAdd";
        kafkaTemplate.send(kafkaTopic, employeeDto);
    }

    public void kafkaUpdateEmployee(UUID id, EmployeeDto employeeDto) {
        String kafkaTopic = "employeeUpdate";
        kafkaTemplate.send(kafkaTopic, new Object[]{id, employeeDto});
    }

    public void kafkaDeleteEmployee(UUID id) {
        String kafkaTopic = "employeeDelete";
        kafkaTemplate.send(kafkaTopic, id);
    }
}
