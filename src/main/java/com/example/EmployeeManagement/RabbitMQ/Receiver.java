package com.example.EmployeeManagement.RabbitMQ;

import com.example.EmployeeManagement.dao.EmployeeDao;
import com.example.EmployeeManagement.model.Employee;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Data
public class Receiver {
    Gson gson = new Gson();
    final EmployeeDao employeeDao;

    public void receiveMessage(String message) {
        try {
            System.out.println("Received <" + message + ">");
            String[] idNEmployee = message.split("\\$");
            int id = Integer.parseInt(idNEmployee[0]);
            Employee employee = gson.fromJson(idNEmployee[1], Employee.class);
            if(!employeeDao.findById(id).isPresent()) {
                return;
            }
            employee.setId(id);
            employeeDao.save(employee);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}