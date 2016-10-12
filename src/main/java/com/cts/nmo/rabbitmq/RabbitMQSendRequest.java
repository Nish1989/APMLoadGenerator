package com.cts.nmo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSendRequest {

	private final static String QUEUE_NAME = "test";

	/*
	 * public static void main(String[] args) {
	 * 
	 * initMessage(); }
	 * 
	 * public static String initMessage() {
	 * 
	 * try { for (int i = 0; i < 1000; i++) { sendMessage(); }
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); return "* * * Failed * * * "; } return
	 * " * * * Success * * * "; }
	 */

	public boolean sendMessage(String message) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("52.10.190.217");
		/*
		 * factory.setVirtualHost("nmo"); 
		 * factory.setUsername("admin");
		 * factory.setPassword("admin");
		 */

		Connection connection = null;
		Channel channel = null;

		try {
			// factory.setHost("172.23.0.13");
			factory.setPort(5672);
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
			System.out.println("Sent '" + message + "'");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				channel.close();
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

		}
		return true;

	}

}