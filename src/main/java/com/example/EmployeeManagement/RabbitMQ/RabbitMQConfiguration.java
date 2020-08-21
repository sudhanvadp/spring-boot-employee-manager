package com.example.EmployeeManagement.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String topicExchangeName = "employee-exchange";
    public static final String queueUpdate = "queueUpdate";
    public static final String queueAdd = "queueAdd";
    public static final String queueDelete = "queueDelete";

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

        return BindingBuilder.bind(queue).to(exchange).with("employee.add");
    }

    @Bean
    Binding bindingUpdate(@Qualifier("queueUpdate") Queue queue, TopicExchange exchange) {
        System.out.println("bindingUpdate"+ queue.getName());
        return BindingBuilder.bind(queue).to(exchange).with("employee.update");
    }

    @Bean
    Binding bindingDelete(@Qualifier("queueDelete") Queue queue, TopicExchange exchange) {
        System.out.println("bindingDelete"+ queue.getName());
        return BindingBuilder.bind(queue).to(exchange).with("employee.delete");
    }

    @Bean
    TopicExchange exchangeDelete() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    SimpleMessageListenerContainer containerAdd(ConnectionFactory connectionFactory,
                                             @Qualifier("listenerAdapterAdd") MessageListenerAdapter listenerAdapter) {
        System.out.println("listenerAdapterAdd");
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueAdd);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerUpdate(ConnectionFactory connectionFactory,
                                             @Qualifier("listenerAdapterUpdate") MessageListenerAdapter listenerAdapter) {
        System.out.println("listenerAdapterUpdate");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueUpdate);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    @Bean
    SimpleMessageListenerContainer containerDelete(ConnectionFactory connectionFactory,
                                             @Qualifier("listenerAdapterDelete") MessageListenerAdapter listenerAdapter) {
        System.out.println("listenerAdapterDelete");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueDelete);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterAdd(Receiver receiver) {
        System.out.println("listenerAdapterAdd");
        return new MessageListenerAdapter(receiver, "receiveMessageAdd");
    }

    @Bean
    MessageListenerAdapter listenerAdapterUpdate(Receiver receiver) {
        System.out.println("listenerAdapterUpdate");
        return new MessageListenerAdapter(receiver, "receiveMessageUpdate");
    }

    @Bean
    MessageListenerAdapter listenerAdapterDelete(Receiver receiver) {
        System.out.println("listenerAdapterDelete");
        return new MessageListenerAdapter(receiver, "receiveMessageDelete");
    }
}
