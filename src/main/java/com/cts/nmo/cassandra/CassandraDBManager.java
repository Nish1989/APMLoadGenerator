package com.cts.nmo.cassandra;

import com.cts.nmo.rabbitmq.RabbitMQSendRequest;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraDBManager {

	RabbitMQSendRequest rmqSend = new RabbitMQSendRequest();

	/*
	 * public static void main(String[] args) { boolean flag = new
	 * CassandraDBManager().cassandraConnectionManager();
	 * 
	 * if (flag) { System.out.println("Load Genetated !!!! "); } else {
	 * 
	 * System.out.println("Something went Wrong !!!!!"); } }
	 */

	public Session cassandraConnectionManager() {

		// Connect to the cluster and keyspace "demo"
		// Cluster cluster =
		// Cluster.builder().withPort(9160).addContactPoint("localhost").build();
		Cluster cluster = Cluster.builder().addContactPoints("172.23.6.99").build();

		Session session = cluster.connect("nmotest");

		return session;
	}

	public void cassandraExecute(int value) {

		Session session = cassandraConnectionManager();

		insertData(session, value);

		printData(session, value);
		
	}

	private void printData(Session session, int value) {

		String query = "SELECT * FROM users WHERE u_id=" + value;
		// Use select to get the user we just entered
		ResultSet results = session.execute(query);
		for (Row row : results) {
			System.out.println("OutPut === > " + row.getString("firstname") + " <---> " + row.getString("team"));
		}

	}

	private void insertData(Session session, int value) {

		String query = "INSERT INTO users (u_id, lastname, firstname, team) VALUES (" + value + ",'Zlat_" + value
				+ "', 'Ibra_" + value + "', 'Man_" + value + "')";

		// Insert one record into the users table
		session.execute(query);

	}

	private boolean updateDate(Session session) {

		// Update the same user with a new age
		session.execute("update users set team = 'ManUtd' where u_id = 9");
		return rmqSend.sendMessage("Updated Successfully");
	}

	private boolean deleteData(Session session) {

		// Delete the user from the users table
		session.execute("DELETE FROM users WHERE u_id = 9");
		return rmqSend.sendMessage("Deleted Successfully");
	}

}
