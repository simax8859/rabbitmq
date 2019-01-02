package com.ling.log;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//该类用于设置连接并调用具体的处理方法
public class ReceiveLog {
    //连接设置
    private static final String IP_ADDRESS="192.168.230.128";
    private static final int PORT=5672;

    public static void main(String[] args) {
        try {
            Address[] addresses = new Address[]{
                    new Address(IP_ADDRESS, PORT)
            };
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("ling");
            factory.setPassword("123456");
            Connection connection = factory.newConnection(addresses);//创建连接
            //创建信道
            Channel channelDebug = connection.createChannel();
            Channel channelInfo = connection.createChannel();
            Channel channelWarn = connection.createChannel();
            Channel channelError = connection.createChannel();
            channelDebug.basicQos(64);//设置客户端最多接收未被ack的消息的个数
            channelInfo.basicQos(64);
            channelWarn.basicQos(64);
            channelError.basicQos(64);
            channelDebug.basicConsume("queue.debug", false, "DEBUG", new ConsumerThread(channelDebug));
            channelDebug.basicConsume("queue.info", false, "INFO", new ConsumerThread(channelInfo));
            channelDebug.basicConsume("queue.warning", false, "WARNING", new ConsumerThread(channelWarn));
            channelDebug.basicConsume("queue.error", false, "ERROR", new ConsumerThread(channelError));
        }catch (IOException e){
            e.printStackTrace();
        }catch (TimeoutException e){
            e.printStackTrace();
        }
    }
}
