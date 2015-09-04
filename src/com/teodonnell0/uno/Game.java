package com.teodonnell0.uno;

import com.teodonnell0.uno.enums.CardColor;

public abstract class Game {

	protected Hand hand;
	
	public abstract CardColor getCurrentColor();
	
	public abstract Card getUpCard();
	
	public abstract Hand getPlayerHand();
	
	public abstract boolean isPlayable(Card card);
	public abstract boolean isPlayerTurn();
	public abstract boolean isRunning();
	
	public abstract String getPlayerName(int index);
	public abstract String getWinner();
	
	public abstract void setSelectedCardIndex(int index);
	
	public abstract int getCurrentPlayerIndex();
}
