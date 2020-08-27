package com.example.EmployeeManagement.kafka;

import com.example.EmployeeManagement.RabbitMQ.Receiver;
import com.example.EmployeeManagement.config.RabbitMQConfiguration;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class KafkaReciever {
    private static final Logger logger = LoggerFactory.getLogger(KafkaReciever.class);
    @Autowired
    private EmployeeService employeeService;

    @KafkaListener(id = "add", topics = "employeeAdd")
    public void listenAdd(EmployeeDto employeeDto) {
        logger.info("Received: " + employeeDto.toString());
        try {
            employeeService.addEmployee(employeeDto);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @KafkaListener(id = "update", topics = "employeeUpdate")
    public void listenUpdate(Object[] objects) {
        try {
            logger.info("Received: " + Arrays.toString(objects));
            employeeService.updateEmployee((UUID) objects[0], (EmployeeDto) objects[1]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @KafkaListener(id = "delete", topics = "employeeDelete")
    public void listenDelete(UUID id) {
        try {
            logger.info("Received: " + id);
            employeeService.deleteEmployee(id);//UUID.fromString(id.replaceAll("\"", "")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
