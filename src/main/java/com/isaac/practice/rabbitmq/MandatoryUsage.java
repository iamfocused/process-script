package com.isaac.practice.rabbitmq;

import com.rabbitmq.client.*;

import java.util.concurrent.TimeUnit;

public class MandatoryUsage {

    public static void main(String[] args) throws Exception{
        final RabbitMqTemplate template = RabbitMqTemplate.getInstance();
        Channel channel = template.getChannel();
        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> System.out.println("cant publish this message '" + new String(body) + "',\nand exchange is '" + exchange + "' and routingKey is '" + routingKey + "'"));

        channel.basicPublish("test_direct_exchange", "noSuchRoutingKey", true, MessageProperties.PERSISTENT_TEXT_PLAIN, "hello".getBytes());

        TimeUnit.SECONDS.sleep(1);
        template.close();
    }
}
