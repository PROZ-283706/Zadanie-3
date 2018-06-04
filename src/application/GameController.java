package application;

import java.util.Optional;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	private boolean win;
	private boolean endGame;
	static final char BLANK = ' ', O = 'O', X = 'X';
	private char position[];

	// Endpoints of the 8 rows in position[] (across, down, diagonally)
	private int rows[][] = { { 0, 2 }, { 3, 5 }, { 6, 8 }, { 0, 6 }, { 1, 7 }, { 2, 8 }, { 0, 8 }, { 2, 6 } };

	private Player act;

	@FXML private void gameBtn00_Click() {
		clickOnButton(0, act.getGameChar());
		producer.sendQueueMessage("00");
		checkGameResult (0);
	}

	@FXML private void gameBtn01_Click() {

		clickOnButton(1, act.getGameChar());
		producer.sendQueueMessage("01");
		checkGameResult (1);
	}

	@FXML private void gameBtn02_Click() {
		clickOnButton(2, act.getGameChar());
		producer.sendQueueMessage("02");
		checkGameResult (2);
	}

	@FXML private void gameBtn10_Click() {
		clickOnButton(3, act.getGameChar());
		producer.sendQueueMessage("10");
		checkGameResult (3);
	}

	@FXML private void gameBtn11_Click() {
		clickOnButton(4, act.getGameChar());
		producer.sendQueueMessage("11");
		checkGameResult (4);
	}

	@FXML private void gameBtn12_Click() {
		clickOnButton(5, act.getGameChar());
		producer.sendQueueMessage("12");
		checkGameResult (5);
	}

	@FXML private void gameBtn20_Click() {
		clickOnButton(6, act.getGameChar());
		producer.sendQueueMessage("20");
		checkGameResult (6);
	}

	@FXML private void gameBtn21_Click() {
		clickOnButton(7, act.getGameChar());
		producer.sendQueueMessage("21");
		checkGameResult (7);
	}

	@FXML private void gameBtn22_Click() {
		clickOnButton(8, act.getGameChar());
		producer.sendQueueMessage("22");
		checkGameResult (8);
	}

	public void initialize() {
		setGameButtonsDisable(true);
		
		int id = new Random().nextInt(1000000);
		act = new Player("O", id);
		myTurn = false;
		win = false;
		endGame = false;
		
		producer = new PTPProducer(act.getPlayerId());
		consumer = new PTPConsumer(new QueueAsynchConsumer(this), act.getPlayerId());
		
		position = new char[9];
		for (int i = 0; i < 9; ++i)
			position[i] = BLANK;

		producer.sendQueueMessage("wait" + act.getPlayerId() + "");
	}
	
	public void setMyTurn(boolean type) {
		this.myTurn = type;
	}

	public String getOpponentChar() {
		return act.getOpponentChar();
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
	
	private void reset() {
		gameCharLbl.setText(getOpponentChar());
		act.setGameChar(getOpponentChar());
		
		gameBtn00.setText("");
		gameBtn01.setText("");
		gameBtn02.setText("");
		gameBtn10.setText("");
		gameBtn11.setText("");
		gameBtn12.setText("");
		gameBtn20.setText("");
		gameBtn21.setText("");
		gameBtn22.setText("");
		
		setGameButtonsDisable(true);
		myTurn = false;
		endGame = false;
		
		for (int i = 0; i < 9; ++i)
			position[i] = BLANK;
		
		producer.sendQueueMessage("newgame"+ act.getPlayerId() + "");
		 
		win = false;
	}
	
	public void newGame(int opponentId) {
		
		if (act.getGameChar() == "X") {
			myTurn = true;
			setGameButtonsDisable(false);
		} else if (act.getGameChar() == "O" && myTurn == true) {
			
		} else
			producer.sendQueueMessage("newgame"+ act.getPlayerId() + "");
	}
	

	// Return true if player has won
	private boolean won(char player) {
		for (int i = 0; i < 8; ++i)
			if (testRow(player, rows[i][0], rows[i][1]))
				return true;
		return false;
	}

	// Has player won in the row from position[a] to position[b]?
	private boolean testRow(char player, int a, int b) {
		return position[a] == player && position[b] == player && position[(a + b) / 2] == player;
	}

	// Are all 9 spots filled?
	private boolean isDraw() {
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
	
	public void setEndGame(boolean endgame) {
		this.endGame = endgame;
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
		
		if(( won(position[number]) || isDraw() ) && endGame == false)
		{
			producer.sendQueueMessage("endgame"+ act.getPlayerId() + "");
			win = true;
			endGame = true;
			if (act.getGameChar() == "O") {
				myTurn = true;
				return;
			}
		} else if( endGame == true) {
			//myTurn = false;
			if (act.getGameChar() == "O") {
				myTurn = true;
			}
			checkGameResult (number);
			return;
		}

		if (myTurn) {
			setGameButtonsDisable(true);
			myTurn = false;
		} else {
			setGameButtonsDisable(false);
			myTurn = true;
		}
		
	}
	
	void checkGameResult (int number) {
		
		if (won(position[number])) {
			setGameButtonsDisable(true);
		
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Koniec gry");
			
			if(position[number] == X && act.getGameChar() == "X") {
				alert.setHeaderText("Wygrales grajac: " + act.getGameChar() + "!");
			} else if (position[number] == O && act.getGameChar() == "O") {
				alert.setHeaderText("Wygrales grajac: " + act.getGameChar()  + "!");
			} else {
				alert.setHeaderText("Przegrales grajac: " + act.getGameChar()  + "!");
			}
			
			alert.setContentText("Chcesz zagrać jeszcze raz?");
			Optional <ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
			reset();
			}
		} else if (isDraw() ) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Koniec gry");
			alert.setHeaderText("Remis!");
			alert.setContentText("Chcesz zagrać jeszcze raz?");
			Optional <ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
				reset();
			}
		}
	}

}