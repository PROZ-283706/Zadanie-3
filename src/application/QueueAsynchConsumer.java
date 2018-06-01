package application;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import javafx.application.Platform;

public class QueueAsynchConsumer implements MessageListener {
	GameController gameController;

	public QueueAsynchConsumer(GameController gameController) {
		this.gameController = gameController;
	}

	@Override
	public void onMessage(Message message) {
		Platform.runLater(() -> {
			try {
				String text = message.getStringProperty("TEXT");

				if (text.length() > 4 && text.substring(0, 4).equals("wait")) {
					gameController.setPlayers(Integer.parseInt(text.substring(4, text.length())));
					return;
				}
				
				if (text.length() > 7 && text.substring(0, 7).equals("endgame")) {
					gameController.setEndGame(true);
					return;
				}
				
				if (text.length() > 7 && text.substring(0, 7).equals("newgame")) {
					gameController.newGame(Integer.parseInt(text.substring(7, text.length())));
					return;
				}
				switch (text) {
				case "00":
					System.out.println("Pole 0");
					gameController.clickOnButton(0, gameController.getOpponentChar());
					break;
				case "01":
					System.out.println("Pole 1");
					gameController.clickOnButton(1, gameController.getOpponentChar());
					break;
				case "02":
					System.out.println("Pole 2");
					gameController.clickOnButton(2, gameController.getOpponentChar());
					break;
				case "10":
					System.out.println("Pole 3");
					gameController.clickOnButton(3, gameController.getOpponentChar());
					break;
				case "11":
					System.out.println("Pole 4");
					gameController.clickOnButton(4, gameController.getOpponentChar());
					break;
				case "12":
					System.out.println("Pole 5");
					gameController.clickOnButton(5, gameController.getOpponentChar());
					break;
				case "20":
					System.out.println("Pole 6");
					gameController.clickOnButton(6, gameController.getOpponentChar());
					break;
				case "21":
					System.out.println("Pole 7");
					gameController.clickOnButton(7, gameController.getOpponentChar());
					break;
				case "22":
					System.out.println("Pole 8");
					gameController.clickOnButton(8, gameController.getOpponentChar());
					break;
				}

			} catch (JMSException e) {
				e.printStackTrace();
			}
		});
	}
}
