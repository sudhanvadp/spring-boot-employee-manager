package com.example.EmployeeManagement.RabbitMQ;

import com.example.EmployeeManagement.config.RabbitMQConfiguration;
import com.example.EmployeeManagement.dao.EmployeeDao;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Receiver {
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeService employeeService;

    @RabbitListener(queues = RabbitMQConfiguration.queueAdd)
    public void receiveMessageAdd(EmployeeDto employeeDto) {
        try {
            System.out.println("Received <" + employeeDto.toString() + ">");
            employeeService.addEmployee(employeeDto);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.queueUpdate)
    public void receiveMessageUpdate(Object[] objects) {
        System.out.println("updating");
        try {
            employeeService.updateEmployee((UUID) objects[0], (EmployeeDto) objects[1]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.queueDelete)
    public void receiveMessageDelete(UUID id) {
        try {
            System.out.println("Received <" + id + ">");
            employeeService.deleteEmployee(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}