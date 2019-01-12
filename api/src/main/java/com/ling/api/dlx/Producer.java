package com.ling.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.230.128");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setUsername("ling");
		connectionFactory.setPassword("123456");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_dlx_exchange";
		String routingKey = "dlx.save";
		String msg = "Hello RabbitMQ DLX Message";
		for(int i =0; i<1; i ++){
			//设置消息持久化，编码格式为UTF-8，过期时间为10秒
			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.expiration("10000")
					.build();
			//mandatory：设置为true后，则监听器可以接收到路由不可达的消息，从而进行后续的处理
			channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
		}
	}
}
