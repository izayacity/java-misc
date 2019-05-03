package com.izayacity.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.time.LocalTime;

public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";
    private final static String QUEUE_HOST = "localhost";
    private static Gson gson = new Gson();

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_HOST);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.queuePurge(RPC_QUEUE_NAME);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Object monitor = new Object();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";
                String message = new String(delivery.getBody(), "UTF-8");

                try {
                    int n = Integer.parseInt(message);

                    System.out.println(LocalTime.now().toString() + " - [.] fib(" + message + ")");
                    response += fib(n);
                } catch (RuntimeException e) {
                    System.out.println(LocalTime.now().toString() + " - [.] " + e.toString());
                } finally {
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                    // because we set autoAck when invoking the basic consume method
                    channel.basicAck(deliveryTag, false);

                    System.out.println(LocalTime.now().toString() + " - consumer tag of message  " + message + ": " + consumerTag);
                    System.out.println(LocalTime.now().toString() + " - delivery envelop of message  " + message + ": " + gson.toJson(delivery.getEnvelope()));
                    System.out.println(LocalTime.now().toString() + " - delivery tag of message  " + message + ": " + deliveryTag);
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> {
            }));
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}