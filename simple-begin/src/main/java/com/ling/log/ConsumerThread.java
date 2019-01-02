package com.ling.log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;


//具体的处理方法，因为是测试，所以实际并未开启线程，只是执行一次
public class ConsumerThread extends DefaultConsumer {
    public ConsumerThread(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
        String log =new String(body);
        System.out.println("="+consumerTag+"REPORT====\n"+log);
        //这里可以写对日志进行相应的处理代码

        getChannel().basicAck(envelope.getDeliveryTag(),false);
    }
}
