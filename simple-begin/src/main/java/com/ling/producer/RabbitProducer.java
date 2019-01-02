package com.ling.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME="exchange_demo";
    private static final String ROUTING_KEY="routingkey_demo";
    private static final String QUEUE_NAME="queue_demo";
    private static final String IP_ADDRESS="192.168.230.128";
    private static final int PORT=5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("ling");
        factory.setPassword("123456");
        Connection connection=factory.newConnection();//创建连接
        Channel channel=connection.createChannel();//创建信道
        //创建一个type="direct"、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        //创建一个持久化、非排他的、非自动删除的队列，不带其他的参数
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        //发送一条持久化的信息：hello world！
        String message="test p2p 中文";
        //MessageProperties.PERSISTENT_TEXT_PLAIN是一个默认的参数
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        //关闭资源，其实关闭connection信道也会自动关闭，但是分两步写结构更好
        channel.close();
        connection.close();

    }
}
