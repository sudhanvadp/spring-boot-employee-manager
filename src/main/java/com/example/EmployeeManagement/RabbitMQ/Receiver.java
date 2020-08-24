package com.example.EmployeeManagement.RabbitMQ;

import com.example.EmployeeManagement.config.RabbitMQConfiguration;
import com.example.EmployeeManagement.dao.EmployeeRepository;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @RabbitListener(queues = RabbitMQConfiguration.queueAdd)
    public void receiveMessageAdd(EmployeeDto employeeDto) {
        try {
            logger.info("Received <" + employeeDto + ">");
            employeeService.addEmployee(employeeDto);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.queueUpdate)
    public void receiveMessageUpdate(Object[] objects) {
        try {
            logger.info("Received <" + Arrays.toString(objects) + ">");
            employeeService.updateEmployee((UUID) objects[0], (EmployeeDto) objects[1]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.queueDelete)
    public void receiveMessageDelete(UUID id) {
        try {
            logger.info("Received <" + id + ">");
            employeeService.deleteEmployee(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}