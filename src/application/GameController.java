package application;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameController {
	@FXML Button gameBtn00;
	@FXML Button gameBtn01;
	@FXML Button gameBtn02;
	@FXML Button gameBtn10;
	@FXML Button gameBtn11;
	@FXML Button gameBtn12;
	@FXML Button gameBtn20;
	@FXML Button gameBtn21;
	@FXML Button gameBtn22;

	@FXML Label gameCharLbl;

	private PTPConsumer consumer;
	private PTPProducer producer;
	private boolean myTurn;

	static final char BLANK = ' ', O = 'O', X = 'X';
	private char position[];

	// Endpoints of the 8 rows in position[] (across, down, diagonally)
	private int rows[][] = { { 0, 2 }, { 3, 5 }, { 6, 8 }, { 0, 6 }, { 1, 7 }, { 2, 8 }, { 0, 8 }, { 2, 6 } };

	private Player act;

	@FXML private void gameBtn00_Click() {
		clickOnButton(0, act.getGameChar());
		producer.sendQueueMessage("00");
	}

	@FXML private void gameBtn01_Click() {

		clickOnButton(1, act.getGameChar());
		producer.sendQueueMessage("01");
	}

	@FXML private void gameBtn02_Click() {
		clickOnButton(2, act.getGameChar());
		producer.sendQueueMessage("02");
	}

	@FXML private void gameBtn10_Click() {
		clickOnButton(3, act.getGameChar());
		producer.sendQueueMessage("10");
	}

	@FXML private void gameBtn11_Click() {
		clickOnButton(4, act.getGameChar());
		producer.sendQueueMessage("11");
	}

	@FXML private void gameBtn12_Click() {
		clickOnButton(5, act.getGameChar());
		producer.sendQueueMessage("12");
	}

	@FXML private void gameBtn20_Click() {
		clickOnButton(6, act.getGameChar());
		producer.sendQueueMessage("20");
	}

	@FXML private void gameBtn21_Click() {
		clickOnButton(7, act.getGameChar());
		producer.sendQueueMessage("21");
	}

	@FXML private void gameBtn22_Click() {
		clickOnButton(8, act.getGameChar());
		producer.sendQueueMessage("22");
	}

	public void initialize() {
		setGameButtonsDisable(true);

		int id = new Random().nextInt(1000000);
		act = new Player("O", id);
		myTurn = false;
		
		producer = new PTPProducer(act.getPlayerId());
		consumer = new PTPConsumer(new QueueAsynchConsumer(this), act.getPlayerId());

		position = new char[9];
		for (int i = 0; i < 9; ++i)
			position[i] = BLANK;

		producer.sendQueueMessage("wait" + act.getPlayerId() + "");
	}

	private void setGameButtonsDisable(boolean a) {

		if (gameBtn00.getText().equals(""))
			gameBtn00.setDisable(a);
		if (gameBtn01.getText().equals(""))
			gameBtn01.setDisable(a);
		if (gameBtn02.getText().equals(""))
			gameBtn02.setDisable(a);
		if (gameBtn10.getText().equals(""))
			gameBtn10.setDisable(a);
		if (gameBtn11.getText().equals(""))
			gameBtn11.setDisable(a);
		if (gameBtn12.getText().equals(""))
			gameBtn12.setDisable(a);
		if (gameBtn20.getText().equals(""))
			gameBtn20.setDisable(a);
		if (gameBtn21.getText().equals(""))
			gameBtn21.setDisable(a);
		if (gameBtn22.getText().equals(""))
			gameBtn22.setDisable(a);

	}

	public void setMyTurn(boolean type) {
		this.myTurn = type;
	}

	public String getOpponentChar() {
		return act.getOpponentChar();
	}

	// Return true if player has won
	boolean won(char player) {
		for (int i = 0; i < 8; ++i)
			if (testRow(player, rows[i][0], rows[i][1]))
				return true;
		return false;
	}

	// Has player won in the row from position[a] to position[b]?
	boolean testRow(char player, int a, int b) {
		return position[a] == player && position[b] == player && position[(a + b) / 2] == player;
	}

	// Return 0-8 for the position of a blank spot in a row if the
	// other 2 spots are occupied by player, or -1 if no spot exists
	int findRow(char player) {
		for (int i = 0; i < 8; ++i) {
			int result = find1Row(player, rows[i][0], rows[i][1]);
			if (result >= 0)
				return result;
		}
		return -1;
	}

	// If 2 of 3 spots in the row from position[a] to position[b]
	// are occupied by player and the third is blank, then return the
	// index of the blank spot, else return -1.
	int find1Row(char player, int a, int b) {
		int c = (a + b) / 2; // middle spot
		if (position[a] == player && position[b] == player && position[c] == BLANK)
			return c;
		if (position[a] == player && position[c] == player && position[b] == BLANK)
			return b;
		if (position[b] == player && position[c] == player && position[a] == BLANK)
			return a;
		return -1;
	}

	// Are all 9 spots filled?
	boolean isDraw() {
		for (int i = 0; i < 9; ++i)
			if (position[i] == BLANK)
				return false;
		return true;
	}

	public void sendLeaveMessage() {
		consumer.close();
	}

	public void setPlayers(int opponentId) {
		if (act.getPlayerId() > opponentId) {
			gameCharLbl.setText(act.getGameChar());
		} else {
			myTurn = true;
			act.setGameChar("X");
			gameCharLbl.setText(act.getGameChar());
			setGameButtonsDisable(false);
		}
	}

	public void clickOnButton(int number, String sight) {

		switch (number) {
		case 0:
			gameBtn00.setText(sight);
			gameBtn00.setDisable(true);
			break;
		case 1:
			gameBtn01.setText(sight);
			gameBtn01.setDisable(true);
			break;
		case 2:
			gameBtn02.setText(sight);
			gameBtn02.setDisable(true);
			break;
		case 3:
			gameBtn10.setText(sight);
			gameBtn10.setDisable(true);
			break;
		case 4:
			gameBtn11.setText(sight);
			gameBtn11.setDisable(true);
			break;
		case 5:
			gameBtn12.setText(sight);
			gameBtn12.setDisable(true);
			break;
		case 6:
			gameBtn20.setText(sight);
			gameBtn20.setDisable(true);
			break;
		case 7:
			gameBtn21.setText(sight);
			gameBtn21.setDisable(true);
			break;
		case 8:
			gameBtn22.setText(sight);
			gameBtn22.setDisable(true);
			break;
		}

		position[number] = sight == "X" ? X : O;
		if (won(position[number])) {
			if (myTurn) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Koniec gry");
				alert.setHeaderText("Wygrales!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Koniec gry");
				alert.setHeaderText("Przegrales!");
				alert.showAndWait();
			}
			setGameButtonsDisable(true);
			return;
		} else if (isDraw()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Koniec gry");
			alert.setHeaderText("Remis!");
			alert.showAndWait();
			return;
		}

		setGameButtonsDisable(false);

		if (myTurn) {
			setGameButtonsDisable(true);
			myTurn = false;
		}

	}

}
