package application;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

public class PTPConsumer {
	private JMSConsumer jmsConsumer;
	private JMSContext jmsContext;
	private Queue queue;

	public PTPConsumer(QueueAsynchConsumer asynchConsumer, Integer id) {
		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		jmsContext = connectionFactory.createContext();
		try {
			queue = new com.sun.messaging.Queue("ATJQueue");
			jmsConsumer = jmsContext.createConsumer(queue, "ID <> '" + id + "'");
			jmsConsumer.setMessageListener(asynchConsumer);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		jmsConsumer.close();
		jmsContext.close();
	}
}
