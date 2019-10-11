package com.isaac.practice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.practice.fastxml.pojo.UserInfo;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;

public class RabbitMqTemplateTest {
    private RabbitMqTemplate template = RabbitMqTemplate.getInstance();
    private ObjectMapper mapper = new ObjectMapper();

    @After
    public void after() {
        template.close();
    }

    @Test
    public void sendMessage() throws Exception{
        Channel channel = template.getChannel();
        UserInfo u = new UserInfo("isaac", 12, "112");
        String json = mapper.writeValueAsString(u);
        channel.basicPublish("backUpExchange", "", null, json.getBytes());
    }

    @Test
    public void testConsumeWithoutAck() throws Exception {
        Channel channel = template.getChannel();
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consuming... message is '" + new String(body) + "' but no ack");
            }
        };
        channel.basicConsume("backUpQueue", false, consumer);
    }
}
