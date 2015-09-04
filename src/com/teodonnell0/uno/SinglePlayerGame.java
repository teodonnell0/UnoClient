package com.teodonnell0.uno;

import com.teodonnell0.uno.enums.CardColor;

public class SinglePlayerGame extends Game {

	@Override
	public CardColor getCurrentColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPlayable(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPlayerTurn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPlayerName(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card getUpCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hand getPlayerHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedCardIndex(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCurrentPlayerIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
