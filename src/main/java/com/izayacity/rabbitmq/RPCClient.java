package com.izayacity.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient implements AutoCloseable {

	private Connection connection;
	private Channel channel;
	private String requestQueueRoutingKey = "rpc_queue";
	private final static String QUEUE_HOST = "localhost";

	public RPCClient() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(QUEUE_HOST);

		connection = factory.newConnection();
		channel = connection.createChannel();
	}

	public static void main(String[] argv) {
		try (RPCClient fibonacciRpc = new RPCClient()) {
			for (int i = 0; i < 32; i++) {
				String i_str = Integer.toString(i);
				System.out.println(LocalTime.now().toString() + " - [x] Requesting fib(" + i_str + ")");
				String response = fibonacciRpc.call(i_str);
				System.out.println(LocalTime.now().toString() + " - [.] Got '" + response + "'");
			}
		} catch (IOException | TimeoutException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String call(String message) throws IOException, InterruptedException {
		final String corrId = UUID.randomUUID().toString();
		System.out.println(LocalTime.now().toString() + " - channel id: " + channel.getChannelNumber() + " and corrId: " + corrId);

		String replayQueueRoutingKey = channel.queueDeclare().getQueue();
		AMQP.BasicProperties props = new AMQP.BasicProperties
				.Builder()
				.correlationId(corrId)
				.replyTo(replayQueueRoutingKey)
				.build();

		channel.basicPublish("", requestQueueRoutingKey, props, message.getBytes("UTF-8"));

		final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

		String ctag = channel.basicConsume(replayQueueRoutingKey, true, (consumerTag, delivery) -> {
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response.offer(new String(delivery.getBody(), "UTF-8"));
			}
		}, consumerTag -> {
		});

		String result = response.take();
		System.out.println(LocalTime.now().toString() + " - queue name of message  " + message + ": " + replayQueueRoutingKey);
		System.out.println(LocalTime.now().toString() + " - consumer tag of message  " + message + ": " + ctag);
		// cancel the consumer declared above
		channel.basicCancel(ctag);
		return result;
	}

	public void close() throws IOException {
		connection.close();
	}
}
