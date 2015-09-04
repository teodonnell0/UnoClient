package com.teodonnell0.uno.GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import com.teodonnell0.uno.MultiPlayerGame;
import com.teodonnell0.uno.Player;

public class MenuOptionMultiPlayerState extends GameState {

	private String playerName;
	private final int DEFAULT_PORT = 49817;
	
	public MenuOptionMultiPlayerState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}

	@Override
	public void init() {
		while(playerName == null || playerName.trim().isEmpty() || playerName.length() > 12) {
			playerName = JOptionPane.showInputDialog("What is your name?");
		}
		String host = null; 
		Object[] options = {"localhost", "Server", "Exit"};
		int selection = JOptionPane.showOptionDialog(null, "Select a server:", "Server Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

		switch(selection) {
		case 0:
			host = "localhost";
			break;
		case 1: 
			host = JOptionPane.showInputDialog("What is the server's IP address?", "9.75.65.19");
			break;
		case 2:
			System.exit(0);
			break;
		}
		

		gameStateManager.setMultiPlayerGame(new MultiPlayerGame(playerName, host, DEFAULT_PORT));
		
		gameStateManager.setState(State.MultiPlayer);
		
		
		
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D graphics2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseInput(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
