package com.teodonnell0.uno.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.teodonnell0.uno.Card;
import com.teodonnell0.uno.GameProtocal;
import com.teodonnell0.uno.Hand;
import com.teodonnell0.uno.ImageMap;
import com.teodonnell0.uno.ImageMap.ImageName;
import com.teodonnell0.uno.KeyState;
import com.teodonnell0.uno.MultiPlayerGame;
import com.teodonnell0.uno.UnoPanel;
import com.teodonnell0.uno.enums.CardRank;
import com.teodonnell0.uno.enums.Protocal;


public class MultiPlayerState extends GameState implements PlayerStateInterface {

	private MultiPlayerGame game;

	private int selectedCardHover = -1;

	public MultiPlayerState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {

		if(game.getGameInfo() != null && game.isConnectedToServer()) {
			if(KeyState.isPressed(KeyEvent.VK_ENTER)) {
				game.sendPacket(new GameProtocal(Protocal.MOVE, -2));
			}

			if(KeyState.isPressed(KeyEvent.VK_SPACE)) {
				game.sendPacket(new GameProtocal(Protocal.UNO));
			}

			Point p = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, gameStateManager.getPanel());

			if(p.y > 600-145 && p.y < 900) {
				int spacing = (UnoPanel.WIDTH-(game.getHand().size()*46))/2; //Space between x:0 and the start of the hand drawing
				int numberOfCards = game.getHand().size();
				int selected = (p.x-spacing)/46;
				if(selected > -1 && selected < numberOfCards) {
					selectedCardHover = selected;
				}
			}	
		}
	}

	@Override
	public void draw(Graphics2D graphics2D) {

		if(game.shouldShowLogo()) {
			BufferedImage logo = (BufferedImage) ImageMap.getImage(ImageName.Uno_Logo);
			graphics2D.drawImage(logo, (UnoPanel.WIDTH-logo.getWidth())/2, (UnoPanel.HEIGHT-logo.getHeight())/2, null);
		}
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		FontMetrics metrics = graphics2D.getFontMetrics();
		if(game != null) {
			if(!game.isConnectedToServer()) {
				game = null;
				gameStateManager.setState(State.Menu);
				JOptionPane.showMessageDialog(null, "The server has disconnected");
				return;
			}
			if(game.getGameInfo() == null) {
				graphics2D.setColor(Color.BLACK);
				graphics2D.fillRect(0, 0, UnoPanel.WIDTH, UnoPanel.HEIGHT);
				graphics2D.setColor(Color.WHITE);
				String waitingMessage = "Waiting for more players to join the server";
				int w = metrics.stringWidth(waitingMessage);
				graphics2D.drawString(waitingMessage, (UnoPanel.WIDTH-w)/2, (UnoPanel.HEIGHT-metrics.getHeight())/2);
			} else {
				if(game.isGameOver()) {
					graphics2D.setColor(Color.BLACK);
					graphics2D.fillRect(0, 0, UnoPanel.WIDTH, UnoPanel.HEIGHT);

					graphics2D.setFont(new Font("Arial",Font.BOLD, 36));
					metrics = graphics2D.getFontMetrics();
					graphics2D.setColor(Color.WHITE);
					int width = metrics.stringWidth(game.getWinner() + " won");
					graphics2D.drawString(game.getWinner() + " won", (UnoPanel.WIDTH - width)/2, (UnoPanel.HEIGHT - 300));

					
					try {
						Thread.sleep(5000);
						gameStateManager.setState(State.Menu);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					drawBoardBackground(graphics2D);

					drawArrow(graphics2D);

					drawPlayerNames(graphics2D);

					drawDiscardCard(graphics2D);

					drawUpCard(graphics2D);

					drawHumanPlayersCards(graphics2D);

					drawComputersCards(graphics2D);

					drawUnoLogo(graphics2D);
				}
			}
		}
	}

	@Override
	public void handleMouseInput(MouseEvent e) {
		if(game != null && game.isConnectedToServer()) {
			if(e.getY() > 600-145 && e.getY() < 900) {
				int spacing = (UnoPanel.WIDTH-(game.getHand().size()*46))/2; //Space between x:0 and the start of the hand drawing
				int numberOfCards = game.getHand().size();
				int selected = (e.getX()-spacing)/46;

				if(selected > -1 && selected < numberOfCards) {
					game.sendPacket(new GameProtocal(Protocal.MOVE, selected));
					gameStateManager.update();
				}
			}
		}
	}

	public void setGame(MultiPlayerGame game) {
		this.game = game;
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
	public void drawArrow(Graphics2D graphics2D) {
		if(game.getCurrentPlayerIndex() > -1) {
			switch(game.getGameInfo().getInfoList().size()) {

			case 2: {
				switch(game.getPlayerId()) {
				case 0:
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
					}
					break;

				case 1:
					switch(game.getCurrentPlayerIndex()) {
					case 0:
						BufferedImage leftArrow = (BufferedImage) ImageMap.getImage(ImageName.Right_Arrow);
						int xOffset = (UnoPanel.WIDTH - leftArrow.getWidth()) / 2 + 100;
						int yOffset = (UnoPanel.HEIGHT - leftArrow.getHeight()) / 2;
						graphics2D.drawImage(leftArrow, xOffset, yOffset, null);

						break;

					case 1:
						BufferedImage downArrow = (BufferedImage) ImageMap.getImage(ImageName.Down_Arrow);
						xOffset = (UnoPanel.WIDTH - downArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - downArrow.getHeight()) / 2 + 100;
						graphics2D.drawImage(downArrow, xOffset, yOffset, null);

						break;
					}
					break;
				}
				break;
			}


			case 3: {
				switch(game.getPlayerId()) {
				case 0:
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

					}
					break;

				case 1:
					switch(game.getCurrentPlayerIndex()) {
					case 0:
						BufferedImage leftArrow = (BufferedImage) ImageMap.getImage(ImageName.Up_Arrow);
						int xOffset = (UnoPanel.WIDTH - leftArrow.getWidth()) / 2;
						int yOffset = (UnoPanel.HEIGHT - leftArrow.getHeight()) / 2 - 100;
						graphics2D.drawImage(leftArrow, xOffset, yOffset, null);

						break;

					case 1:
						BufferedImage downArrow = (BufferedImage) ImageMap.getImage(ImageName.Down_Arrow);
						xOffset = (UnoPanel.WIDTH - downArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - downArrow.getHeight()) / 2 + 100;
						graphics2D.drawImage(downArrow, xOffset, yOffset, null);

						break;

					case 2:
						BufferedImage rightArrow = (BufferedImage) ImageMap.getImage(ImageName.Right_Arrow);
						xOffset = (UnoPanel.WIDTH - rightArrow.getWidth()) / 2 + 100;
						yOffset = (UnoPanel.HEIGHT - rightArrow.getHeight()) / 2;
						graphics2D.drawImage(rightArrow, xOffset, yOffset, null);
						break;

					}
					break;

				case 2:
					switch(game.getCurrentPlayerIndex()) {
					case 0:
						BufferedImage upArrow = (BufferedImage) ImageMap.getImage(ImageName.Right_Arrow);
						int xOffset = (UnoPanel.WIDTH - upArrow.getWidth()) / 2 + 100;
						int yOffset = (UnoPanel.HEIGHT - upArrow.getHeight()) / 2;
						graphics2D.drawImage(upArrow, xOffset, yOffset, null);
						break;

					case 1:
						BufferedImage leftArrow = (BufferedImage) ImageMap.getImage(ImageName.Up_Arrow);
						xOffset = (UnoPanel.WIDTH - leftArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - leftArrow.getHeight()) / 2 - 100;
						graphics2D.drawImage(leftArrow, xOffset, yOffset, null);
						break;

					case 2:
						BufferedImage downArrow = (BufferedImage) ImageMap.getImage(ImageName.Down_Arrow);
						xOffset = (UnoPanel.WIDTH - downArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - downArrow.getHeight()) / 2 + 100;
						graphics2D.drawImage(downArrow, xOffset, yOffset, null);
						break;

					}
					break;
				}
				break;
			}

			case 4: {
				switch(game.getPlayerId()) {
				case 0:
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
					break;

				case 1:
					switch(game.getCurrentPlayerIndex()) {
					case 0:
						BufferedImage leftArrow = (BufferedImage) ImageMap.getImage(ImageName.Left_Arrow);
						int xOffset = (UnoPanel.WIDTH - leftArrow.getWidth()) / 2 - 100;
						int yOffset = (UnoPanel.HEIGHT - leftArrow.getHeight()) / 2;
						graphics2D.drawImage(leftArrow, xOffset, yOffset, null);

						break;

					case 1:
						BufferedImage downArrow = (BufferedImage) ImageMap.getImage(ImageName.Down_Arrow);
						xOffset = (UnoPanel.WIDTH - downArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - downArrow.getHeight()) / 2 + 100;
						graphics2D.drawImage(downArrow, xOffset, yOffset, null);

						break;

					case 2:
						BufferedImage rightArrow = (BufferedImage) ImageMap.getImage(ImageName.Right_Arrow);
						xOffset = (UnoPanel.WIDTH - rightArrow.getWidth()) / 2 + 100;
						yOffset = (UnoPanel.HEIGHT - rightArrow.getHeight()) / 2;
						graphics2D.drawImage(rightArrow, xOffset, yOffset, null);
						break;

					case 3:
						BufferedImage upArrow = (BufferedImage) ImageMap.getImage(ImageName.Up_Arrow);
						xOffset = (UnoPanel.WIDTH - upArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - upArrow.getHeight()) / 2 - 100;
						graphics2D.drawImage(upArrow, xOffset, yOffset, null);
						break;
					}
					break;

				case 2:
					switch(game.getCurrentPlayerIndex()) {
					case 0:
						BufferedImage upArrow = (BufferedImage) ImageMap.getImage(ImageName.Up_Arrow);
						int xOffset = (UnoPanel.WIDTH - upArrow.getWidth()) / 2;
						int yOffset = (UnoPanel.HEIGHT - upArrow.getHeight()) / 2 - 100;
						graphics2D.drawImage(upArrow, xOffset, yOffset, null);
						break;

					case 1:
						BufferedImage leftArrow = (BufferedImage) ImageMap.getImage(ImageName.Left_Arrow);
						xOffset = (UnoPanel.WIDTH - leftArrow.getWidth()) / 2 - 100;
						yOffset = (UnoPanel.HEIGHT - leftArrow.getHeight()) / 2;
						graphics2D.drawImage(leftArrow, xOffset, yOffset, null);
						break;

					case 2:
						BufferedImage downArrow = (BufferedImage) ImageMap.getImage(ImageName.Down_Arrow);
						xOffset = (UnoPanel.WIDTH - downArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - downArrow.getHeight()) / 2 + 100;
						graphics2D.drawImage(downArrow, xOffset, yOffset, null);
						break;

					case 3:
						BufferedImage rightArrow = (BufferedImage) ImageMap.getImage(ImageName.Right_Arrow);
						xOffset = (UnoPanel.WIDTH - rightArrow.getWidth()) / 2 + 100;
						yOffset = (UnoPanel.HEIGHT - rightArrow.getHeight()) / 2;
						graphics2D.drawImage(rightArrow, xOffset, yOffset, null);
						break;
					}
					break;

				case 3:
					switch(game.getCurrentPlayerIndex()) {
					case 0:
						BufferedImage rightArrow = (BufferedImage) ImageMap.getImage(ImageName.Right_Arrow);
						int xOffset = (UnoPanel.WIDTH - rightArrow.getWidth()) / 2 + 100;
						int yOffset = (UnoPanel.HEIGHT - rightArrow.getHeight()) / 2;
						graphics2D.drawImage(rightArrow, xOffset, yOffset, null);
						break;

					case 1:
						BufferedImage upArrow = (BufferedImage) ImageMap.getImage(ImageName.Up_Arrow);
						xOffset = (UnoPanel.WIDTH - upArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - upArrow.getHeight()) / 2 - 100;
						graphics2D.drawImage(upArrow, xOffset, yOffset, null);
						break;

					case 2:
						BufferedImage leftArrow = (BufferedImage) ImageMap.getImage(ImageName.Left_Arrow);
						xOffset = (UnoPanel.WIDTH - leftArrow.getWidth()) / 2 - 100;
						yOffset = (UnoPanel.HEIGHT - leftArrow.getHeight()) / 2;
						graphics2D.drawImage(leftArrow, xOffset, yOffset, null);
						break;

					case 3:
						BufferedImage downArrow = (BufferedImage) ImageMap.getImage(ImageName.Down_Arrow);
						xOffset = (UnoPanel.WIDTH - downArrow.getWidth()) / 2;
						yOffset = (UnoPanel.HEIGHT - downArrow.getHeight()) / 2 + 100;
						graphics2D.drawImage(downArrow, xOffset, yOffset, null);
						break;
					}
					break;
				}
			}
			}
		}
	}

	@Override
	public void drawPlayerNames(Graphics2D graphics2D) {

		switch(game.getGameInfo().getInfoList().size()) {
		case 2:
			switch(game.getPlayerId()) {
			case 0:
				String playerName = game.getGameInfo().getPlayerInfo(0).getName();
				FontMetrics fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				break;
			case 1:
				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(0).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				break;
			}
			break;
		case 3:
			switch(game.getPlayerId()) {
			case 0:
				String playerName = game.getGameInfo().getPlayerInfo(0).getName();
				FontMetrics fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				playerName = game.getGameInfo().getPlayerInfo(2).getName();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, 155);

				break;
			case 1:
				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(2).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				playerName = game.getGameInfo().getPlayerInfo(0).getName();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, 155);

				break;
			case 2:
				playerName = game.getGameInfo().getPlayerInfo(2).getName();
				fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(0).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, 155);

				break;
			}
			break;
		case 4:
			switch(game.getPlayerId()) {
			case 0:
				String playerName = game.getGameInfo().getPlayerInfo(0).getName();
				FontMetrics fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				playerName = game.getGameInfo().getPlayerInfo(2).getName();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, 155);

				playerName = game.getGameInfo().getPlayerInfo(3).getName();
				graphics2D.drawString(playerName, 155,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);
				break;
			case 1:
				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(2).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				playerName = game.getGameInfo().getPlayerInfo(3).getName();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, 155);

				playerName = game.getGameInfo().getPlayerInfo(0).getName();
				graphics2D.drawString(playerName, 155,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);
				break;
			case 2:
				playerName = game.getGameInfo().getPlayerInfo(2).getName();
				fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(3).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				playerName = game.getGameInfo().getPlayerInfo(0).getName();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, 155);

				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				graphics2D.drawString(playerName, 155,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);
				break;
			case 3:
				playerName = game.getGameInfo().getPlayerInfo(3).getName();
				fontMetrics = graphics2D.getFontMetrics();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, UnoPanel.HEIGHT-155);

				playerName = game.getGameInfo().getPlayerInfo(0).getName();
				graphics2D.drawString(playerName, UnoPanel.WIDTH-155-fontMetrics.stringWidth(playerName)/2,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);

				playerName = game.getGameInfo().getPlayerInfo(1).getName();
				graphics2D.drawString(playerName, (UnoPanel.WIDTH-fontMetrics.stringWidth(playerName))/2, 155);

				playerName = game.getGameInfo().getPlayerInfo(2).getName();
				graphics2D.drawString(playerName, 155,  (UnoPanel.HEIGHT-fontMetrics.getHeight())/2);
				break;
			}
			break;
		}
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
		BufferedImage upCard= (BufferedImage) ImageMap.getImage(ImageName.valueOf(game.getUpCard().toString()));
		int offsetX = (UnoPanel.WIDTH - upCard.getWidth()) / 2 + 50;
		int offsetY = (UnoPanel.HEIGHT - upCard.getHeight()) / 2;
		graphics2D.drawImage(upCard, offsetX, offsetY, null);
	}

	@Override
	public void drawHumanPlayersCards(Graphics2D graphics2D) {
		Hand hand = game.getHand();
		int playerDeckWidth = 92*hand.size()/2;
		int playerDeckHeight = 145;
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
		BufferedImage handOneDeck = null;
		BufferedImage handTwoDeck = null;
		BufferedImage handThreeDeck = null;
		int w = 0, x = 0, y = 0;
		switch(game.getGameInfo().getInfoList().size()) {
		case 2:
			switch(game.getPlayerId()) {
			case 0:
				w = 1;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				break;
			case 1:
				w = 0;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				break;
			}
			break;

		case 3:
			switch(game.getPlayerId()) {
			case 0:
				w = 1;
				x = 2;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handTwoDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(x).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				break;
			case 1:
				w = 2;
				x = 1;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handTwoDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(x).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				break;
			case 2:
				w = 0;
				x = 1;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handTwoDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(x).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				break;
			}
			break;

		case 4:
			switch(game.getPlayerId()) {
			case 0:
				w = 1;
				x = 2;
				y = 3;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handTwoDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(x).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handThreeDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(y).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				break;
			case 1:
				w = 2;
				x = 3;
				y = 0;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handTwoDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(x).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handThreeDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(y).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				break;
			case 2:
				w = 3;
				x = 0;
				y = 1;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handTwoDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(x).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handThreeDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(y).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				break;
			case 3:
				w = 0;
				x = 1;
				y = 2;
				handOneDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(w).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handTwoDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(x).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
				handThreeDeck = new BufferedImage(92*game.getGameInfo().getPlayerInfo(y).getHandSize()/2+46, 135, BufferedImage.TYPE_INT_ARGB);
			}
			break;
		}
		AffineTransform affineTransform = new AffineTransform();


		//handOneDeck (right)
		Graphics deckGraphics;
		deckGraphics = handOneDeck.getGraphics();

		for(int i = 0; i < game.getGameInfo().getPlayerInfo(w).getHandSize(); i++) {
			deckGraphics.drawImage(ImageMap.getImage(ImageName.Card_Back), i*92/2, 0, null);
		}

		affineTransform.translate(UnoPanel.WIDTH/2, UnoPanel.HEIGHT/2);
		affineTransform.scale(0.5, 0.5);
		affineTransform.rotate(-Math.PI/2);
		affineTransform.translate( -(UnoPanel.HEIGHT-handOneDeck.getHeight())/3, (UnoPanel.WIDTH-135));

		graphics2D.drawImage(handOneDeck, affineTransform, null);

		if(game.getGameInfo().getInfoList().size() > 2){
			//handTwoDeck (across)
			affineTransform = new AffineTransform();
			deckGraphics = handTwoDeck.getGraphics();

			for(int i = 0; i < game.getGameInfo().getPlayerInfo(x).getHandSize(); i++) {
				deckGraphics.drawImage(ImageMap.getImage(ImageName.Card_Back), i*92/2, 0, null);
			}

			affineTransform.translate(UnoPanel.WIDTH/2, UnoPanel.HEIGHT/2);

			affineTransform.scale(0.5, 0.5);
			affineTransform.rotate(Math.PI);
			affineTransform.translate(-(UnoPanel.WIDTH-handTwoDeck.getWidth())/3, UnoPanel.HEIGHT-135);

			graphics2D.drawImage(handTwoDeck, affineTransform, null);
		}
		if(game.getGameInfo().getInfoList().size() > 3) { 
			//handThreeDeck (left)
			affineTransform = new AffineTransform();
			deckGraphics = handThreeDeck.getGraphics();

			for(int i = 0; i < game.getGameInfo().getPlayerInfo(y).getHandSize(); i++) {
				deckGraphics.drawImage(ImageMap.getImage(ImageName.Card_Back), i*92/2, 0, null);
			}

			affineTransform.translate(UnoPanel.WIDTH/2, UnoPanel.HEIGHT/2);

			affineTransform.scale(0.5, 0.5);
			affineTransform.rotate(Math.PI/2);
			affineTransform.translate(-(UnoPanel.HEIGHT-handThreeDeck.getHeight())/3, (UnoPanel.WIDTH-135));

			graphics2D.drawImage(handThreeDeck, affineTransform, null);
		}
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	private void drawUnoLogo(Graphics2D graphics2D) {
		if(game.shouldShowLogo()) {
			BufferedImage image = (BufferedImage) ImageMap.getImage(ImageName.Uno_Logo);
			graphics2D.drawImage(image, (UnoPanel.WIDTH-image.getWidth())/2, (UnoPanel.HEIGHT-image.getHeight())/2, null);
		}
	}

}
