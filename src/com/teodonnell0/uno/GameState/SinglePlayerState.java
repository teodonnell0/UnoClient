package com.teodonnell0.uno.GameState;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.SwingUtilities;

import com.teodonnell0.uno.Card;
import com.teodonnell0.uno.Deck;
import com.teodonnell0.uno.Hand;
import com.teodonnell0.uno.ImageMap;
import com.teodonnell0.uno.ImageMap.ImageName;
import com.teodonnell0.uno.SinglePlayerGame;
import com.teodonnell0.uno.UnoPanel;

public class SinglePlayerState extends GameState implements PlayerStateInterface {

	private Deck deck = new Deck();

	private SinglePlayerGame game;

	private int playerDeckHeight;

	private int playerDeckWidth;

	private int selectedCardHover = -1;

	public SinglePlayerState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}
	@Override
	public void draw(Graphics2D graphics2D) {
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if(game != null) {
			if(game.isRunning()) {
				drawBoardBackground(graphics2D);

				drawArrow(graphics2D);

				drawPlayerNames(graphics2D);

				drawDiscardCard(graphics2D);

				drawUpCard(graphics2D);

				drawHumanPlayersCards(graphics2D);

				drawComputersCards(graphics2D);

			} else {
				graphics2D.drawString(game.getWinner() + " wins", 400, 400);
			}
		}
	}
	@Override
	public void handleInput() {


	}

	@Override
	public void handleMouseInput(MouseEvent e) {

		if(game.isRunning()) {
			if(e.getY() > 180+65 && e.getY() < 305+65 && e.getX() > 265+120 && e.getX() < 355+120) {
				if(game.isPlayerTurn()) {
					game.setSelectedCardIndex(-2);
				}
			}

			if(e.getY() > 600-145 && e.getY() < 900) {
				int spacing = (UnoPanel.WIDTH-(game.getPlayerHand().size()*46))/2; //Space between x:0 and the start of the hand drawing
				int numberOfCards = game.getPlayerHand().size();
				int selected = (e.getX()-spacing)/46;
				if(selected > -1 && selected < numberOfCards) {
					game.setSelectedCardIndex(selected);
					gameStateManager.update();
				}
			}
		}
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
	public void setGame(SinglePlayerGame game) {
		this.game = game;
	}

	@Override
	public void update() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(p, gameStateManager.getPanel());
		if(p.y > 600-145 && p.y < 900) {
			int spacing = (UnoPanel.WIDTH-(game.getPlayerHand().size()*46))/2; //Space between x:0 and the start of the hand drawing
			int numberOfCards = game.getPlayerHand().size();
			int selected = (p.x-spacing)/46;
			if(selected > -1 && selected < numberOfCards) {
				selectedCardHover = selected;
			}
		}

	}

	@Override
	public void drawArrow(Graphics2D graphics2D) {
		if(game.getCurrentPlayerIndex() > -1) {
			switch(game.getCurrentPlayerIndex()) {
			case 0:
				BufferedImage downArrow = (BufferedImage) ImageMap.getImage(ImageName.Down_Arrow);
				int xOffset = (UnoPanel.WIDTH - downArrow.getWidth()) / 2;
				int yOffset = (UnoPanel.HEIGHT - downArrow.getHeight()) / 2 + 100;
				graphics2D.drawImage(downArrow, xOffset, yOffset, null);
				break;

			case 1:
				BufferedImage rightArrow = (BufferedImage) ImageMap.getImage(ImageName.Right_Arrow);
				xOffset = (UnoPanel.WIDTH - rightArrow.getWidth()) / 2 + 100;
				yOffset = (UnoPanel.HEIGHT - rightArrow.getHeight()) / 2;
				graphics2D.drawImage(rightArrow, xOffset, yOffset, null);
				break;

			case 2:
				BufferedImage upArrow = (BufferedImage) ImageMap.getImage(ImageName.Up_Arrow);
				xOffset = (UnoPanel.WIDTH - upArrow.getWidth()) / 2;
				yOffset = (UnoPanel.HEIGHT - upArrow.getHeight()) / 2 - 100;
				graphics2D.drawImage(upArrow, xOffset, yOffset, null);
				break;

			case 3:
				BufferedImage leftArrow = (BufferedImage) ImageMap.getImage(ImageName.Left_Arrow);
				xOffset = (UnoPanel.WIDTH - leftArrow.getWidth()) / 2 - 100;
				yOffset = (UnoPanel.HEIGHT - leftArrow.getHeight()) / 2;
				graphics2D.drawImage(leftArrow, xOffset, yOffset, null);
				break;
			}
		}
	}

	@Override
	public void drawBoardBackground(Graphics2D graphics2D) {
		if(game.getCurrentColor()!=null)
			switch (game.getCurrentColor()) {
			case RED:
				graphics2D.drawImage(ImageMap.getImage(ImageName.Game_Board_Red), 0, 0, UnoPanel.WIDTH, UnoPanel.HEIGHT, null);
				break;
			case BLUE:
				graphics2D.drawImage(ImageMap.getImage(ImageName.Game_Board_Blue), 0, 0, UnoPanel.WIDTH, UnoPanel.HEIGHT,null);
				break;
			case GREEN:
				graphics2D.drawImage(ImageMap.getImage(ImageName.Game_Board_Green), 0, 0, UnoPanel.WIDTH, UnoPanel.HEIGHT,null);
				break;
			case YELLOW:
				graphics2D.drawImage(ImageMap.getImage(ImageName.Game_Board_Yellow), 0, 0, UnoPanel.WIDTH, UnoPanel.HEIGHT,null);
				break;
			default:
				System.out.println("WTF");
				break;

			} else {
				System.out.println("NULL");
			}
	}

	@Override
	public void drawPlayerNames(Graphics2D graphics2D) {

		String playerName = game.getPlayerName(0);
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

		playerName = game.getPlayerName(1);
		graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

		playerName = game.getPlayerName(2);
		graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, 155);

		playerName = game.getPlayerName(3);
		graphics2D.drawString(playerName, 155,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);
	}

	@Override
	public void drawDiscardCard(Graphics2D graphics2D) {
			BufferedImage back = (BufferedImage) ImageMap.getImage(ImageName.Card_Back);
			int offsetX = (UnoPanel.WIDTH - back.getWidth()) / 2 - 50;
			int offsetY = (UnoPanel.HEIGHT - back.getHeight()) / 2;
			graphics2D.drawImage(ImageMap.getImage(ImageName.Card_Back),offsetX, offsetY, null);
	}

	@Override
	public void drawUpCard(Graphics2D graphics2D) {
		if(game.getUpCard() != null) {
			BufferedImage upCard= (BufferedImage) ImageMap.getImage(ImageName.valueOf(game.getUpCard().toString()));
			int offsetX = (UnoPanel.WIDTH - upCard.getWidth()) / 2 + 50;
			int offsetY = (UnoPanel.HEIGHT - upCard.getHeight()) / 2;
			graphics2D.drawImage(upCard, offsetX, offsetY, null);
		}
	}

	@Override
	public void drawHumanPlayersCards(Graphics2D graphics2D) {
		Hand hand = game.getPlayerHand();
		playerDeckWidth = 92*hand.size()/2;
		playerDeckHeight = 145;
		if(hand.size() > 0) {
			BufferedImage playerDeck = new BufferedImage(92*hand.size()/2, 145, BufferedImage.TYPE_INT_ARGB);
			Graphics deckGraphics = playerDeck.getGraphics();
			List<Card> cards = hand.getCards();
			for(int i = 0; i < cards.size(); i++) {
				if(i == selectedCardHover)
					deckGraphics.drawImage(ImageMap.getImage(ImageName.valueOf(cards.get(i).toString())), i*92/2, 0, null);
				else
					deckGraphics.drawImage(ImageMap.getImage(ImageName.valueOf(cards.get(i).toString())), i*92/2, 10, null);
			}
			graphics2D.drawImage(playerDeck, (UnoPanel.WIDTH - playerDeck.getWidth())/2, UnoPanel.HEIGHT-playerDeck.getHeight(), null);
		}
	}

	@Override
	public void drawComputersCards(Graphics2D graphics2D) {

	}
}
