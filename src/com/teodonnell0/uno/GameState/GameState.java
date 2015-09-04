package com.teodonnell0.uno.GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class GameState {
	
	protected GameStateManager gameStateManager;
	
	public GameState(GameStateManager gameStateManager) {
		this.gameStateManager = gameStateManager;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D graphics2D);
	public abstract void handleMouseInput(MouseEvent e);
	public abstract void handleInput();
}
