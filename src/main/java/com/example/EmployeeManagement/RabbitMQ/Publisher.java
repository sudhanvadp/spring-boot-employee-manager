package com.example.EmployeeManagement.RabbitMQ;

import com.example.EmployeeManagement.config.RabbitMQConfiguration;
import com.example.EmployeeManagement.dto.EmployeeDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void addEmployee(EmployeeDto employeeDto) {
        send(RabbitMQConfiguration.topicExchangeName, RabbitMQConfiguration.keyAdd, employeeDto);
    }

    public void updateEmployee(UUID id, EmployeeDto employeeDto) {
        send(RabbitMQConfiguration.topicExchangeName, RabbitMQConfiguration.keyUpdate,  new Object[]{id, employeeDto});
    }

    public void deleteEmployee(UUID id) {
        send(RabbitMQConfiguration.topicExchangeName, RabbitMQConfiguration.keyDelete, id);
    }

    public void send(String topic, String routingKey, Object body) {
        System.out.println("Sending message : routing key: "+routingKey+" body : "+ body.toString());
        rabbitTemplate.convertAndSend(topic, routingKey, body);
    }
}
