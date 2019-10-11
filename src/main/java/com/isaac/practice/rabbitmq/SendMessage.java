package com.isaac.practice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class SendMessage {
    public static void main(String[] args) throws Exception{
        final RabbitMqTemplate template = RabbitMqTemplate.getInstance();
        Channel channel = template.getChannel();
        //channel.exchangeDeclare("bao.test.exchange", "direct", true, false, null);
        //channel.queueDeclare("bao.test.queue", true, false, false, null);
        //channel.queueBind("bao.test.queue", "bao.test.exchange", "routingKey_bindingKey");
        channel.basicPublish("amq.fanout", "routingKey_bindingKey", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello World".getBytes());

        template.close();

    }
}
