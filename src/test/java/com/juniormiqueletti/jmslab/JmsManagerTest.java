package com.juniormiqueletti.jmslab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.junit.Test;

import com.juniormiqueletti.jsmlab.JmsManager;

public class JmsManagerTest {

	public void testConnectionJmsManagerOk() throws NamingException, JMSException {
		JmsManager jmsManager = new JmsManager();
		
		jmsManager
			.startConnection()
			.closeConnection();
	}
	
	@Test
	public void testJmsManagerSendMessageOk() throws NamingException, JMSException {
		JmsManager jmsManager = new JmsManager();
		
		jmsManager.startConnection()
				.sendTexMessage("Test");
	}
	
	@Test
	public void testJmsManagerSendReceiveMessageOk() throws NamingException, JMSException {
		JmsManager jmsManager = new JmsManager();
		
		Message receiveMessage = 
				jmsManager.startConnection()
				.sendTexMessage("Test")
				.receiveMessage();
		
		assertNotNull(receiveMessage);
		
		TextMessage textMessage = (TextMessage) receiveMessage;
		
		assertEquals("Test",textMessage.getText());
	}
	
	@Test
	public void testJmsSendReceiveMessage() throws NamingException, JMSException {
		JmsManager jmsManager = new JmsManager();
		jmsManager.startConnection()
			.activeMessageListener()
			.sendTexMessage("okok")
			.sendTexMessage("okok2")
			.sendTexMessage("okok3");
	}
}
