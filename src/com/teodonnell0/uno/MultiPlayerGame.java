package com.teodonnell0.uno;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.teodonnell0.uno.enums.CardColor;
import com.teodonnell0.uno.enums.Protocal;

public class MultiPlayerGame implements Runnable{

	private Socket connection;
	private int playerId;
	private volatile GameInfo gameInfo;
	private Hand hand;
	private ObjectInputStream objectInputStream;
	
	private ObjectOutputStream objectOutputStream;
	private Player player;
	private boolean playing;
	
	private int selectedCardIndex = 0;
	
	private Thread thread; 
	
	private String winner = "";
	private boolean gameOver = false;
	private boolean connectedToServer;
	public MultiPlayerGame(String playerName, String host, int port) {
		playing = true;
		connect(host, port);
		sendPacket(new GameProtocal(Protocal.ACCOUNT, playerName));
	}
	
	private void connect(String host, int port) {
		try {
			connection = new Socket(host, port);
			OutputStream outputStream = connection.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			InputStream inputStream = connection.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
			connectedToServer = true;
			start();
		} catch (IOException e) {
			System.err.println("Server is not running");
			e.printStackTrace();
		}
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public boolean isPlayerTurn() {
		return gameInfo.getCurrentPlayerIndex() == player.getPlayerId();
	}
	
	public int getCurrentPlayerIndex() {
		return gameInfo.getCurrentPlayerIndex();
	}
	
	public CardColor getCurrentColor() {
		return gameInfo.getCurrentColor();
	}
	
	
	public int getPlayerId() {
		return playerId;
	}

	public Card getUpCard() {
		return gameInfo.getUpCard();
	}
	@Override
	public void run() {
		GameInfo gameInfo = null;
		Hand hand = null;
		
		while(playing && isConnectedToServer()) {
			try {
				GameProtocal incoming = (GameProtocal) objectInputStream.readObject();
				switch(incoming.getProtocal()) {
				case PLAYER_ID:
					Integer id = (Integer) incoming.getMessage();
					player.setPlayerId(id);
					System.out.println(id);
					break;
				case ACCOUNT:
					Player player = (Player) incoming.getMessage();
					playerId = player.getClientNumber();
					setPlayer(player);
					break;
				case GAME_INFO:
					gameInfo = (GameInfo) incoming.getMessage();
					setGameInfo(gameInfo);
					break;
				case CHAT:
					String chat = (String) incoming.getMessage();
					System.out.println(chat);
					break;
				case HAND:
					hand = (Hand) incoming.getMessage();
					setHand(hand);
					break;
				case INVALID_MOVE:
					System.err.println("You can't do that!");
					break;
				case GAME_OVER:
					winner = (String) incoming.getMessage();
					gameOver = true;
					System.out.println(winner + " wins");
					break;
				case UNO:
					Integer index = (Integer) incoming.getMessage();
					showLogo = true;
					break;
				default:
					break;
				}
			} catch(IOException e) {
				e.printStackTrace();
				System.err.println("Server disconnected");
				connectedToServer = false;
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	private boolean showLogo = false;
	private int unoCounter = 0;
	public boolean shouldShowLogo() {
		if(showLogo) {

			if(++unoCounter < 25){
				System.out.println(unoCounter);
				return true;
			}else {
				showLogo = false;
				unoCounter = 0;
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public String getWinner() {
		return winner;
	}
	public void sendPacket(GameProtocal gameProtocal) {
		try {
			objectOutputStream.writeObject(gameProtocal);
		} catch (IOException e) {
			System.err.println("Message failed due to server disconnect");
			e.printStackTrace();
			playing = false;
		}
	}

	private synchronized void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	private void setHand(Hand hand) {
		this.hand = hand;
	}

	private void setPlayer(Player player) {
		this.player = player;
	}

	private void start() {
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public boolean isConnectedToServer() {
		return connectedToServer;
	}
}
