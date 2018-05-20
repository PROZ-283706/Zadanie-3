package application;

public class Player {
	private String gameChar;
	private Integer playerId;

	public Player(String c, Integer id) {
		this.gameChar = c;
		this.playerId = id;
	}

	public String getGameChar() {
		return gameChar;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setGameChar(String gameChar) {
		this.gameChar = gameChar;
	}

	public void setPlayerId(Integer id) {
		this.playerId = id;
	}

	public String getOpponentChar() {
		if (gameChar.equals("X"))
			return "O";
		return "X";
	}

}
