package com.juniormiqueletti.jmslab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.juniormiqueletti.jsmlab.JmsManager;

public class JmsManagerTest {

	JmsManager jmsManager;
	
	@Before
	public void setUp() throws NamingException, JMSException{
		jmsManager = new JmsManager();
		jmsManager.startConnection();
	}
	
	@After
	public void destroy() throws JMSException, NamingException {
		jmsManager.closeConnection();
	}
	
	@Test
	public void testJmsManagerSendMessageOk() throws NamingException, JMSException {
		jmsManager.startConnection()
				.sendTexMessage("Test");
	}
	
	@Test
	public void testJmsManagerSendReceiveMessageOk() throws NamingException, JMSException {
		
		Message receiveMessage = 
				jmsManager.startConnection()
					.sendTexMessage("Test")
					.receiveMessage();
		
		assertNotNull(receiveMessage);
		
		TextMessage textMessage = (TextMessage) receiveMessage;
		
		assertEquals("Test",textMessage.getText());
	}
	
	@Test
	public void testJmsMessageListener() throws NamingException, JMSException {
		jmsManager.startConnection()
			.activeMessageListener()
			.sendTexMessage("okok")
			.sendTexMessage("okok2")
			.sendTexMessage("okok3");
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testJmsgetEnumeration() throws NamingException, JMSException {
		Enumeration enumeration = jmsManager.startConnection()
			.getEnumeration();
		
		assertNotNull(enumeration);
	}
}
