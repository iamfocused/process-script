package com.isaac.practice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AlternateExchange {

    public static void main(String[] args) throws Exception{
        final RabbitMqTemplate template = RabbitMqTemplate.getInstance();
        Channel channel = template.getChannel();
        channel.exchangeDeclare("backUpExchange", "fanout", true, false ,null);
        channel.queueDeclare("backUpQueue", true, false, false, null);
        channel.queueBind("backUpQueue", "backUpExchange", "test");

        Map<String, Object> arg = new HashMap<>();
        arg.put("alternate-exchange", "backUpExchange");
        channel.exchangeDelete("testExchange");
        channel.exchangeDeclare("testExchange", "direct", true, false, arg);

        channel.basicPublish("testExchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, "test message".getBytes());

        TimeUnit.SECONDS.sleep(1);

        template.close();
    }
}
