package com.isaac.practice.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ConsumeMessage {

    public static void main(String[] args) throws Exception{
        final RabbitMqTemplate template = RabbitMqTemplate.getInstance();
        Channel channel = template.getChannel();
        channel.basicQos(64);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("获取到消息： " + new String(body));

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("otherqueue", consumer);

        TimeUnit.SECONDS.sleep(5);
        template.close();
    }
}
