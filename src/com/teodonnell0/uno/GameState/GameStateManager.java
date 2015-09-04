package com.teodonnell0.uno.GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import com.teodonnell0.uno.Game;
import com.teodonnell0.uno.MultiPlayerGame;
import com.teodonnell0.uno.SinglePlayerGame;
import com.teodonnell0.uno.UnoPanel;

public class GameStateManager {

	protected SinglePlayerGame singlePlayerGame;
	protected MultiPlayerGame multiPlayerGame;
	
	private boolean paused;
	private PauseState pauseState;
	
	private GameState[] gameStates;
	private State currentState;
	private State previousState;
	
	private UnoPanel panel;
	public GameStateManager(UnoPanel panel) {
		this.panel = panel;
		previousState = State.Running;
		currentState = State.Running;
		paused = false;
		pauseState = new PauseState(this);
		gameStates = new GameState[State.values().length];
		setState(State.Menu);
	}
	
	
	public UnoPanel getPanel() {
		return panel;
	}


	public void setState(State state) {
		previousState = currentState;
		unloadState(previousState);
		currentState = state;
		
		switch(state) {
		case Intro:
			gameStates[State.Intro.ordinal()] = new IntroState(this);
			gameStates[State.Intro.ordinal()].init();
			break;
		case Menu:
			gameStates[State.Menu.ordinal()] = new MenuState(this);
			gameStates[State.Menu.ordinal()].init();
			break;
		case MenuOption:
			gameStates[State.MenuOption.ordinal()] = new MenuOptionState(this);
			gameStates[State.MenuOption.ordinal()].init();
			break;
		case MenuOptionSinglePlayer:
			gameStates[State.MenuOptionSinglePlayer.ordinal()] = new MenuOptionSinglePlayerState(this);
			gameStates[State.MenuOptionSinglePlayer.ordinal()].init();
			break;
		case MenuOptionMultiPlayer:
			gameStates[State.MenuOptionMultiPlayer.ordinal()] = new MenuOptionMultiPlayerState(this);
			gameStates[State.MenuOptionMultiPlayer.ordinal()].init();
			break;
		case SinglePlayer:
			gameStates[State.SinglePlayer.ordinal()] = new SinglePlayerState(this);
			SinglePlayerState s = (SinglePlayerState) gameStates[State.SinglePlayer.ordinal()];
			//s.setGame(game);
			gameStates[State.SinglePlayer.ordinal()].init();
			break;
		case MultiPlayer:
			gameStates[State.MultiPlayer.ordinal()] = new MultiPlayerState(this);
			MultiPlayerState m = (MultiPlayerState) gameStates[State.MultiPlayer.ordinal()];
			m.setGame(multiPlayerGame);
			gameStates[State.MultiPlayer.ordinal()].init();
			break;
		}
	}
	
	
	
	public void setSinglePlayerGame(SinglePlayerGame game) {
		this.singlePlayerGame = game;
	}
	
	public void setMultiPlayerGame(MultiPlayerGame game) {
		this.multiPlayerGame = game;
	}
	
	public MultiPlayerGame getMultiPlayerGame() {
		return multiPlayerGame;
	}
	
	public void unloadState(State state) {
		gameStates[state.ordinal()] = null;
	}
	
	public void update() {
		if(paused) {
			pauseState.update();
		} else if(gameStates[currentState.ordinal()] != null) {
			gameStates[currentState.ordinal()].update();
		}
	}
	
	public void draw(Graphics2D graphics2D) {
		if(paused) {
			pauseState.draw(graphics2D);
		} else if (gameStates[currentState.ordinal()] != null) {
			gameStates[currentState.ordinal()].draw(graphics2D);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		gameStates[currentState.ordinal()].handleMouseInput(e);
	}
	
}
