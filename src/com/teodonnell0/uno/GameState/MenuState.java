package com.teodonnell0.uno.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import com.teodonnell0.uno.ImageMap;
import com.teodonnell0.uno.ImageMap.ImageName;
import com.teodonnell0.uno.KeyState;

public class MenuState extends GameState {

	private enum Option {
		SinglePlayer,
		MultiPlayer,
		Options,
		Exit;

		public Option getNext() {
			int i = (this.ordinal()+1) % (Option.values().length);
			return Option.values()[i];
		}

		public Option getPrevious() {
			int i = this.ordinal()-1;
			if(i < 0) {
				return Option.values()[Option.values().length-1];
			}
			return Option.values()[i];
		}
	}

	private Option currentOption;

	public MenuState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}

	@Override
	public void init() {
		currentOption = Option.SinglePlayer;
	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw(Graphics2D graphics2D) {
		graphics2D.drawImage(ImageMap.getImage(ImageName.Menu_Background), 0, 0, 900, 600, null);

		graphics2D.setFont(new Font("Arial", Font.BOLD, 18));
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2D.setColor(new Color(255, 255, 255));

		for(Option option : Option.values()) {
			if(currentOption == option) {
				graphics2D.setColor(new Color(100, 100, 100));
				graphics2D.drawString(option.name(), 200+150*option.ordinal(), 550);
			} else {
				graphics2D.setColor(new Color(255, 255, 255));
				graphics2D.drawString(option.name(), 200+150*option.ordinal(), 550);
			}
		}
	}
	
	

	@Override
	public void handleInput() {
		if(KeyState.isPressed(KeyEvent.VK_LEFT)) {
			currentOption = currentOption.getPrevious();
		} 
		if(KeyState.isPressed(KeyEvent.VK_RIGHT)) {
			currentOption = currentOption.getNext();	
		}
		
		if(KeyState.isPressed(KeyEvent.VK_ENTER)) {
			selectOption();
		}
	}
	
	private void selectOption() {
		switch(currentOption) {
		case SinglePlayer:
			JOptionPane.showMessageDialog(null, "Single Player not supported in Alpha");//gameStateManager.setState(State.MenuOptionSinglePlayer);
			break;
		case MultiPlayer:
			gameStateManager.setState(State.MenuOptionMultiPlayer);
			break;
		case Options:
			gameStateManager.setState(State.MenuOption);
			break;
		case Exit:
			System.exit(0);
			break;
			
		}
	}

	@Override
	public void handleMouseInput(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
