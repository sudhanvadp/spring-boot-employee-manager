package com.example.EmployeeManagement.RabbitMQ;

import com.example.EmployeeManagement.config.RabbitMQConfiguration;
import com.example.EmployeeManagement.dao.EmployeeDao;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Receiver {
    final EmployeeDao employeeDao;
    final EmployeeService employeeService;

    @RabbitListener(queues = RabbitMQConfiguration.keyAdd)
    public void receiveMessageAdd(EmployeeDto employeeDto) {
        try {
            System.out.println("Received <" + employeeDto.toString() + ">");
            employeeService.addEmployee(employeeDto);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.keyUpdate)
    public void receiveMessageUpdate(Object[] objects) {
        System.out.println("updating");
        try {
            employeeService.updateEmployee((Integer) objects[0], (EmployeeDto) objects[1]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.keyDelete)
    public void receiveMessageDelete(int id) {
        try {
            System.out.println("Received <" + id + ">");
            employeeService.deleteEmployee(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}