package com.juniormiqueletti.jmslab;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

public class ConnectionTest {

	@Test
	public void testConnectionOk() throws NamingException, JMSException {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		connection.close();
		context.close();
	}
}
