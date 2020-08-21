package com.example.EmployeeManagement.RabbitMQ;

import com.example.EmployeeManagement.dao.EmployeeDao;
import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.service.EmployeeService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Receiver {
    Gson gson = new Gson();
    final EmployeeDao employeeDao;
    final EmployeeService employeeService;

    public void receiveMessageUpdate(String message) {
        try {
            System.out.println("Received <" + message + ">");
            String[] idNEmployee = message.split("\\$");
            int id = Integer.parseInt(idNEmployee[0]);
            Employee employee = gson.fromJson(idNEmployee[1], Employee.class);
            employeeService.updateEmployee(id, employee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void receiveMessageAdd(String message) {
        try {
            System.out.println("Received <" + message + ">");
            Employee employee = gson.fromJson(message, Employee.class);
            employeeService.addEmployee(employee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void receiveMessageDelete(String message) {
        try {
            System.out.println("Received <" + message + ">");
            int id = Integer.parseInt(message);
            employeeService.deleteEmployee(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}