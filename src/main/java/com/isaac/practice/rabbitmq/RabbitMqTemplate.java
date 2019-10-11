package com.isaac.practice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Optional;

public class RabbitMqTemplate {
    private static final String URI = "amqp://test:test@192.168.31.204";
    private static final ConnectionFactory factory = new ConnectionFactory();

    private static final RabbitMqTemplate template = new RabbitMqTemplate();
    private Connection connection;
    private Channel channel;

    public static RabbitMqTemplate getInstance() {
        return template;
    }

    private RabbitMqTemplate(){
        try {
            factory.setUri(URI);
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            System.out.println("Construct error " + e.getMessage());
        }
    }
//
//    public Connection getConnection() {
//        return connection;
//    }

    public Channel getChannel() {
        return channel;
    }

    public void close(){
        Optional.ofNullable(connection).ifPresent(conn -> {
            try {
                conn.close();
            } catch (IOException e) {
                System.out.println("Close error " + e.getMessage());
            }
        });
    }
}
