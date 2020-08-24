package com.example.EmployeeManagement.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String topicExchangeName = "employee-exchange";
    public static final String queueAdd = "queueAdd";
    public static final String queueUpdate = "queueUpdate";
    public static final String queueDelete = "queueDelete";
    public static final String keyAdd = "employee.add";
    public static final String keyUpdate = "employee.update";
    public static final String keyDelete = "employee.delete";

    @Bean
    Queue queueUpdate() {
        return new Queue(queueUpdate, false);
    }

    @Bean
    Queue queueAdd() {
        return new Queue(queueAdd, false);
    }

    @Bean
    Queue queueDelete() {
        return new Queue(queueDelete, false);
    }
    @Bean
    Binding bindingAdd(@Qualifier("queueAdd") Queue queue, TopicExchange exchange) {
        System.out.println("bindingAdd"+queue.getName());

        return BindingBuilder.bind(queue).to(exchange).with(keyAdd);
    }

    @Bean
    Binding bindingUpdate(@Qualifier("queueUpdate") Queue queue, TopicExchange exchange) {
        System.out.println("bindingUpdate"+ queue.getName());
        return BindingBuilder.bind(queue).to(exchange).with(keyUpdate);
    }

    @Bean
    Binding bindingDelete(@Qualifier("queueDelete") Queue queue, TopicExchange exchange) {
        System.out.println("bindingDelete"+ queue.getName());
        return BindingBuilder.bind(queue).to(exchange).with(keyDelete);
    }

    @Bean
    TopicExchange exchangeDelete() {
        return new TopicExchange(topicExchangeName);
    }
}
