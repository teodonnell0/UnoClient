package com.teodonnell0.uno;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.teodonnell0.uno.GameState.GameStateManager;
import com.teodonnell0.uno.enums.CardColor;


public class UnoPanel extends JPanel implements Runnable, KeyListener, MouseListener {
	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	
	private Thread thread;
	private boolean running;
	private final int FPS = 30;
	private final int TARGET_TIME = 1000 / FPS;
	
	private BufferedImage image;
	private Graphics2D graphics2D;
	
	private GameStateManager gameStateManager;
	
	public UnoPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			addKeyListener(this);
			addMouseListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private void init() {
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics2D = (Graphics2D) image.getGraphics();
		gameStateManager = new GameStateManager(this);
	}
	
	public synchronized void stop() {
		if(!running) {
			return;
		}
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running) {
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = TARGET_TIME - elapsed / 1_000_000;
			if(wait < 0) {
				wait = TARGET_TIME;
			}
			
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update() {
		gameStateManager.update();
		KeyState.update();
	}
	
	private void draw() {
		gameStateManager.draw(graphics2D);
	}
	
	private void drawToScreen() {
		Graphics graphics = getGraphics();
		graphics.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		graphics.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		KeyState.keySet(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyState.keySet(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) {
			gameStateManager.mouseClicked(e);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public CardColor getColorSelection() {
		Object[] colors = {CardColor.RED, CardColor.GREEN, CardColor.YELLOW, CardColor.BLUE};
		int selectedColor;
		selectedColor = JOptionPane.showOptionDialog(null, "Select the color you wish to switch to", "Color switcher", JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE, null,	colors, CardColor.RED);
		return CardColor.values()[selectedColor];
	}
}
