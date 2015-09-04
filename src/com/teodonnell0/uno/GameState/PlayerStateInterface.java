package com.teodonnell0.uno.GameState;

import java.awt.Graphics2D;

public interface PlayerStateInterface {


	void drawBoardBackground(Graphics2D graphics2D);
	
	void drawArrow(Graphics2D graphics2D);

	void drawPlayerNames(Graphics2D graphics2D);
	
	void drawDiscardCard(Graphics2D graphics2D);
	
	void drawUpCard(Graphics2D graphics2D);

	void drawHumanPlayersCards(Graphics2D graphics2D);

	void drawComputersCards(Graphics2D graphics2D);
}
