package com.ling.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer4DirectExchange {

	
	public static void main(String[] args) throws Exception {
		
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.230.128");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setUsername("ling");
		connectionFactory.setPassword("123456");
		
		//2 创建Connection
		Connection connection = connectionFactory.newConnection();
		//3 创建Channel
		Channel channel = connection.createChannel();  
		//4 声明
		String exchangeName = "test_direct_exchange";
		String routingKey = "test.direct";
		//5 发送消息
		String msg = "Hello World RabbitMQ 4  Direct Exchange Message 111 ... ";
		//发送消息时，指定了exchangeName和routinKey
		channel.basicPublish(exchangeName, routingKey , null , msg.getBytes());

		//6.记得要关闭相关的连接
		channel.close();
		connection.close();
		
	}
	
}
