package com.example.EmployeeManagement.RabbitMQ;

import com.example.EmployeeManagement.config.RabbitMQConfiguration;
import com.example.EmployeeManagement.controller.EmployeeController;
import com.example.EmployeeManagement.dto.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Publisher {
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);
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
        logger.info("Sending message : routing key: "+routingKey+" body : "+ body.toString());
        rabbitTemplate.convertAndSend(topic, routingKey, body);
    }
}
