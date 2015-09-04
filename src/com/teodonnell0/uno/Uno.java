package com.teodonnell0.uno;

import javax.swing.JFrame;

public class Uno {

	public static void main(String...strings) {
		JFrame window = new JFrame("UNO!");
		UnoPanel unoPanel = new UnoPanel();
		window.add(unoPanel);
		
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
