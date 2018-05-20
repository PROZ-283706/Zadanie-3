package application;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;

public class PTPProducer {
	private Integer id;

	public PTPProducer(Integer id) {
		this.id = id;
	}

	public void sendQueueMessage(String text) {
		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		try {
			((com.sun.messaging.ConnectionFactory) connectionFactory)
					.setProperty(com.sun.messaging.ConnectionConfiguration.imqAddressList, "localhost:7676/jms");

			JMSContext jmsContext = connectionFactory.createContext();

			Message message = jmsContext.createMessage();
			message.setStringProperty("ID", id.toString());
			message.setStringProperty("TEXT", text);

			JMSProducer jmsProducer = jmsContext.createProducer();
			Queue queue = new com.sun.messaging.Queue("ATJQueue");

			jmsProducer.send(queue, message);
			System.out.printf("Wiadomosc '%s' zostala wyslana.\n", message);

			jmsContext.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
