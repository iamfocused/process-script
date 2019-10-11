package com.isaac.practice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.HashMap;
import java.util.Map;

public class DLXTest {
    public static void main(String[] args) throws Exception{
        final RabbitMqTemplate template = RabbitMqTemplate.getInstance();

        Channel channel = template.getChannel();

        channel.exchangeDeclare("DLX", "direct", true, false, null);
        channel.queueDeclare("DLQ", true, false, false, null);
        channel.queueBind("DLQ", "DLX", "routing1");

        Map<String, Object> arg = new HashMap<>();
        arg.put("x-dead-letter-exchange", "DLX");
        arg.put("x-dead-letter-routing-key", "routing1");
        arg.put("x-message-ttl", 60000);

        channel.queueDeclare("ttlQueue", true, false, false, arg);

        channel.basicPublish("", "ttlQueue", MessageProperties.PERSISTENT_TEXT_PLAIN, "this message should go to DLQ".getBytes());

        template.close();
    }
}
