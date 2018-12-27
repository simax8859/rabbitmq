package com.example.demo;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues="hello")
@Component
public class Receiver {

    @RabbitHandler
    public void process(String hello){
        System.out.println("consumer Receiver : "+ hello);
    }
}
