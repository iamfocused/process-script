package com.isaac.practice.rabbitmq.rpc;

import com.isaac.practice.rabbitmq.RabbitMqTemplate;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Server {
//    private static final String URI = "amqp://test:test@192.168.31.204";
    private static final RabbitMqTemplate template = RabbitMqTemplate.getInstance();
    private static final String RPC_QUEUE = "rpcQueue";

    public static void main(String[] args) throws Exception{
        Channel channel = template.getChannel();

        channel.queueDeclare(RPC_QUEUE, true, false, false, null);
        System.out.println("[.] Waiting consumer");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String response = "";
                AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();
                try {
                    String msg = new String(body, StandardCharsets.UTF_8);
                    int n = Integer.parseInt(msg);
                    System.out.println(" [.] flib(" + msg + ")");
                    response += flib(n);
                } catch (Exception e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    channel.basicPublish("", properties.getReplyTo(), props, response.getBytes(StandardCharsets.UTF_8));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    template.close();
                }
            }
        };

        channel.basicConsume(RPC_QUEUE, false, consumer);
    }

    private static int flib(int n) {
        if(n == 0) return 0;
        if(n == 1) return 1;
        return flib(n-1) + flib(n-2);
    }

}
