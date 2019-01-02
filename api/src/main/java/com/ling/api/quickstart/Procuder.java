package com.ling.api.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Procuder {

	
	public static void main(String[] args) throws Exception {
		//1 创建一个ConnectionFactory, 并进行配置,包括rabbit broker地址，端口号，虚拟地址，登录账号密码
		// 这里是远程登录，默认guest是不可用的
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.230.128");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setUsername("ling");
		connectionFactory.setPassword("123456");
		
		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();
		
		//3 通过connection创建一个Channel，创建信道
		Channel channel = connection.createChannel();
		
		//4 通过Channel发送数据
		for(int i=0; i < 5; i++){
			String msg = "Hello RabbitMQ!";
			//1 exchange   2 routingKey
			channel.basicPublish("", "test001", null, msg.getBytes());
		}

		//5 记得要关闭相关的连接
		channel.close();
		connection.close();
	}
}
