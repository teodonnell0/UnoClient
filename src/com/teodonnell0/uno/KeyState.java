package com.teodonnell0.uno;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;

public class KeyState {

	public static final HashMap<Integer, Boolean> keyState = new HashMap<>();
	public static final HashMap<Integer, Boolean> previousKeyState = new HashMap<>();
	
	static {
		keyState.put(KeyEvent.VK_LEFT, false);
		keyState.put(KeyEvent.VK_RIGHT, false);
		keyState.put(KeyEvent.VK_ENTER, false);
		keyState.put(KeyEvent.VK_SPACE,  false);
		previousKeyState.put(KeyEvent.VK_LEFT, false);
		previousKeyState.put(KeyEvent.VK_RIGHT, false);
		previousKeyState.put(KeyEvent.VK_ENTER, false);
		previousKeyState.put(KeyEvent.VK_SPACE, false);
	}
	
	public static void keySet(int kv, boolean b) {
		
		switch(kv) {
		case KeyEvent.VK_SPACE:
			keyState.put(kv, b);
			break;
		case KeyEvent.VK_UP:
			keyState.put(kv, b);
			break;
		case KeyEvent.VK_DOWN:
			keyState.put(kv, b);
			break;
		case KeyEvent.VK_RIGHT:
			keyState.put(kv, b);
			break;
		case KeyEvent.VK_LEFT:
			keyState.put(kv, b);
			break;
		case KeyEvent.VK_ENTER:
			keyState.put(kv, b);
			break;
		}
	}
	
	public static void update() {
		Iterator<Integer> keyIterator = previousKeyState.keySet().iterator();
		
		while(keyIterator.hasNext()) {
			Integer k = keyIterator.next();
			previousKeyState.put(k, keyState.get(k));
		}
	}
	
	public static boolean isPressed(Integer k) {
		return keyState.get(k) && !previousKeyState.get(k);
	}
	
	public static boolean isDown(Integer i) {
		return keyState.get(i);
	}
	
	public static boolean anyKeyDown() {
		Iterator<Integer> keyIterator = keyState.keySet().iterator();
		
		while(keyIterator.hasNext()) {
			if(keyState.get(keyIterator.next())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean anyKeyPress() {
		Iterator<Integer> keyIterator = keyState.keySet().iterator();
		
		while(keyIterator.hasNext()) {
			Integer k = keyIterator.next();
			if(keyState.get(k) && !previousKeyState.get(k)) {
				return true;
			}
		}
		return false;
	}
}
