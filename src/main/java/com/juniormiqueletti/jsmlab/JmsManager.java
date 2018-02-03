package com.juniormiqueletti.jsmlab;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsManager {

	private Connection connection;
	private InitialContext context;
	
	public JmsManager() {
	}

	public JmsManager startConnection() throws NamingException, JMSException {
		context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		connection = factory.createConnection();
		connection.start();
		return this;
	}

	public JmsManager closeConnection() throws JMSException, NamingException {
		connection.close();
		context.close();
		return this;
	}

	public Message receiveMessage() throws JMSException, NamingException {
		Message message = null;
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination queue = (Destination) context.lookup("financial");
		MessageConsumer consumer = session.createConsumer(queue);
		
		message = consumer.receive(5000);
		
		return message;
	}
	
	public JmsManager sendTexMessage(String message) throws JMSException, NamingException {
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination queue = (Destination) context.lookup("financial");
		MessageProducer producer = session.createProducer(queue);
		
		TextMessage textMessage = session.createTextMessage(message);
		producer.send(textMessage);
		
		return this;
	}

	/**
	 * Active Message Listener and when receive a new message,
	 * cast to Message e print text on console.
	 * 
	 * @return JmsManager
	 */
	public JmsManager activeMessageListener() {
		
		Session session;
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination queue = (Destination) context.lookup("financial");
			MessageConsumer consumer = session.createConsumer(queue);
			
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					TextMessage textMessage  = (TextMessage)message;
			        try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return this;
	}

}
