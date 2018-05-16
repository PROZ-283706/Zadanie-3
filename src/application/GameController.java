package application;

import javafx.fxml.FXML;
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
	static final char BLANK=' ', O='O', X='X';
	private char position[];
	
	// Endpoints of the 8 rows in position[] (across, down, diagonally)
	private int rows[][]= {{0,2},{3,5},{6,8},{0,6},{1,7},{2,8},{0,8},{2,6}};
	
	private Player act;

	@FXML private void gameBtn00_Click() {
		gameBtn00.setText(act.getGameChar());
		gameBtn00.setDisable(true);
		position[0] = act.getGameChar()== "X" ? X : O;
		if(won(position[0]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
		
	}
	
	@FXML private void gameBtn01_Click() {
		gameBtn01.setText(act.getGameChar());
		gameBtn01.setDisable(true);
		position[1] = act.getGameChar()== "X" ? X : O;
		if(won(position[1]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
	}
	
	@FXML private void gameBtn02_Click() {
		gameBtn02.setText(act.getGameChar());
		gameBtn02.setDisable(true);
		position[2] = act.getGameChar()== "X" ? X : O;
		if(won(position[2]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
	}
	
	@FXML private void gameBtn10_Click() {
		gameBtn10.setText(act.getGameChar());
		gameBtn10.setDisable(true);
		position[3] = act.getGameChar()== "X" ? X : O;
		if(won(position[3]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
	}
	
	@FXML private void gameBtn11_Click() {
		gameBtn11.setText(act.getGameChar());
		gameBtn11.setDisable(true);
		position[4] = act.getGameChar()== "X" ? X : O;
		if(won(position[4]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
	}
	
	@FXML private void gameBtn12_Click() {
		gameBtn12.setText(act.getGameChar());
		gameBtn12.setDisable(true);
		position[5] = act.getGameChar()== "X" ? X : O;
		if(won(position[5]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
	}
	
	@FXML private void gameBtn20_Click() {
		gameBtn20.setText(act.getGameChar());
		gameBtn20.setDisable(true);
		position[6] = act.getGameChar()== "X" ? X : O;
		if(won(position[6]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
	}
	
	@FXML private void gameBtn21_Click() {
		gameBtn21.setText(act.getGameChar());
		gameBtn21.setDisable(true);
		position[7] = act.getGameChar()== "X" ? X : O;
		if(won(position[7]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
	}
	
	@FXML private void gameBtn22_Click() {
		gameBtn22.setText(act.getGameChar());
		gameBtn22.setDisable(true);
		position[8] = act.getGameChar()== "X" ? X : O;
		if(won(position[8]))
		{
			System.out.println("Wygrales. Koniec gry!");
			setGameButtonsDisable(true);
		}
		else if(isDraw()) System.out.println("Remis. Koniec gry!");
	}
	
	
	 public void initialize() {
		 //setGameButtonsDisable(true);
		 act = new Player("O");
		 gameCharLbl.setText(act.getGameChar());
		 position = new char[9];
		 for (int i = 0; i < 9; ++i)
				position[i]=BLANK;
		 
	 }
	
	private void setGameButtonsDisable(boolean a) {
		gameBtn00.setDisable(a);
		gameBtn01.setDisable(a);
		gameBtn02.setDisable(a);
		gameBtn10.setDisable(a);
		gameBtn11.setDisable(a);
		gameBtn12.setDisable(a);
		gameBtn20.setDisable(a);
		gameBtn21.setDisable(a);
		gameBtn22.setDisable(a);
	}
	
	// Return true if player has won
	   boolean won(char player) {
	     for (int i=0; i<8; ++i)
	       if (testRow(player, rows[i][0], rows[i][1]))
	         return true;
	     return false;
	   }

	// Has player won in the row from position[a] to position[b]?
	   boolean testRow(char player, int a, int b) {
	     return position[a]==player && position[b]==player
	         && position[(a+b)/2]==player;
	   }


	   // Return 0-8 for the position of a blank spot in a row if the
	   // other 2 spots are occupied by player, or -1 if no spot exists
	   int findRow(char player) {
	     for (int i=0; i<8; ++i) {
	       int result=find1Row(player, rows[i][0], rows[i][1]);
	       if (result>=0)
	         return result;
	     }
	     return -1;
	   }

	   // If 2 of 3 spots in the row from position[a] to position[b]
	   // are occupied by player and the third is blank, then return the
	   // index of the blank spot, else return -1.
	   int find1Row(char player, int a, int b) {
	     int c=(a+b)/2;  // middle spot
	     if (position[a]==player && position[b]==player && position[c]==BLANK)
	       return c;
	     if (position[a]==player && position[c]==player && position[b]==BLANK)
	       return b;
	     if (position[b]==player && position[c]==player && position[a]==BLANK)
	       return a;
	     return -1;
	   }

	   // Are all 9 spots filled?
	   boolean isDraw() {
	     for (int i=0; i<9; ++i)
	       if (position[i]==BLANK)
	         return false;
	     return true;
	   }

	
}

