package com.teodonnell0.uno.GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class MenuOptionSinglePlayerState extends GameState {

	private String playerName;
	
	public MenuOptionSinglePlayerState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}

	@Override
	public void init() {
		while(playerName == null || playerName.isEmpty() || playerName.length() > 18) {
			playerName = JOptionPane.showInputDialog("What is your name?");
		}
	/*	
		Scoreboard scoreboard = new Scoreboard();
		scoreboard.addPlayerName(playerName);
		
		gameStateManager.setGame(new SingleplayerGame(scoreboard, 3, gameStateManager.getPanel()));
		
		gameStateManager.setState(State.SinglePlayer);*/
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
