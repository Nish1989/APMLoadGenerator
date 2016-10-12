package com.cts.nmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.nmo.cassandra.CassandraDBManager;
import com.cts.nmo.rabbitmq.RabbitMQReciveRequest;
import com.cts.nmo.rabbitmq.RabbitMQSendRequest;
import com.cts.nmo.redis.RedisManager;

@RestController
@EnableAutoConfiguration
public class AppDynamicPOC {

	@RequestMapping("/appDynamicsOpr")
	String appDynamicsOpr() {

		performCassandraOpr();
		performRabbitMQSend();
		performRedisOpr();

		return "Load Genetated !!!! ";
	}

	@RequestMapping("/rabbitMQSend")
	String rabbitMQSend() {

		return performRabbitMQSend();

	}

	@RequestMapping("/rabbitMQRec")
	String rabbitMQRec() {
		return performRabbitMQRec();
	
	}

	@RequestMapping("/redisOpr")
	String redisOpr() {

		return performRedisOpr();

	}

	@RequestMapping("/cassandraDBOpr")
	String cassandraDBOpr() {

		return performCassandraOpr();

	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppDynamicPOC.class, args);
	}

	private String performRedisOpr() {

		RedisManager redisManager = new RedisManager();

		for (int i = 0; i < 100; i++) {

			String value = Integer.toString(i);

			redisManager.runRedis(value);

		}

		return "Load Genetated in Redis!!!! ";
	}

	private String performCassandraOpr() {

		CassandraDBManager cassandraDBManager = new CassandraDBManager();

		for (int i = 0; i < 100; i++) {

			cassandraDBManager.cassandraExecute(i);
		}

		return "Load Genetated in Cassandra DB!!!! ";
	}

	private String performRabbitMQSend() {
		RabbitMQSendRequest rmqSend = new RabbitMQSendRequest();

		for (int i = 0; i < 100; i++) {
			
			String value = Integer.toString(i);

			rmqSend.sendMessage(value);
		}

		return "Message Sent to RabbitMQ !!!! ";
	}
	
	private String performRabbitMQRec() {
		
		RabbitMQReciveRequest rabbitMQReciveRequest = new RabbitMQReciveRequest();
		try {
			rabbitMQReciveRequest.recive();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Rec Sent to RabbitMQ !!!! ";
	}
	
}