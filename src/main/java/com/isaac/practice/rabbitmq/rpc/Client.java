package com.isaac.practice.rabbitmq.rpc;

import com.isaac.practice.rabbitmq.RabbitMqTemplate;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Client {
//    private static final String URI = "amqp://test:test@192.168.31.204";
    private static final String RPC_QUEUE = "rpcQueue";

//    private Connection connection;
    private static final RabbitMqTemplate template = RabbitMqTemplate.getInstance();
    private Channel channel;
    private String replyQueueName;
    private QueueingConsumer consumer;

    public Client() throws Exception{
        channel = template.getChannel();
        consumer = new QueueingConsumer(channel);
        replyQueueName = channel.queueDeclare().getQueue();
        channel.basicConsume(replyQueueName, false, consumer);
    }

    public void close(){
        template.close();
    }

    public String call(String messge) throws Exception {
        String response = "";
        String correlationId = UUID.randomUUID().toString();
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .replyTo(replyQueueName)
                .correlationId(correlationId)
                .build();
        channel.basicPublish("", RPC_QUEUE, props, messge.getBytes(StandardCharsets.UTF_8));

        while(true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if(delivery.getProperties().getCorrelationId().equals(correlationId)){
                response = new String(delivery.getBody(), StandardCharsets.UTF_8);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                break;
            }
        }

        return response;
    }

    public static void main(String[] args) throws Exception{
        Client client = new Client();
        String response = client.call("5");
        System.out.println(" [.] Get response '" + response + "'");
        client.close();
    }
}
