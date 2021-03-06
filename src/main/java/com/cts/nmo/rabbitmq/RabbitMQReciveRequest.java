package com.cts.nmo.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitMQReciveRequest {

	private final static String QUEUE_NAME = "test";

	public void recive() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("52.10.190.217");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
/*	public static void main(String[] args) {
		try {
			recive();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}