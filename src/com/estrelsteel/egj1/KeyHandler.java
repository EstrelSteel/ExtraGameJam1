package com.estrelsteel.egj1;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public int keys;
	
	public KeyHandler() {
		keys = 0;
	}
	
	public enum KeyMap {
		UP(0), DOWN(1), LEFT(2), RIGHT(3), ANYKEY(4);
		
		int shift;
		
		KeyMap(int shift) {
			this.shift = shift;
		}
		
		public int getShift() {
			return shift;
		}
	}
	
	private int biton(int bits, int shift) {
		int i = 1 << shift;
		return bits | i;
	}
	
	private int bitoff(int bits, int shift) {
		int i = ~(1 << shift);
		return bits & i;
	}
	
	public void pressKey(KeyMap key) {
		keys = biton(keys, key.getShift());
	}
	
	public void releaseKey(KeyMap key) {
		keys = bitoff(keys, key.getShift());
	}
	
	public boolean isKeyDown(KeyMap key) {
		return ((keys >> key.getShift()) & 0x1) == 1;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 87: /* UP */
		case 79:
		case 38:
			pressKey(KeyMap.UP);
			break;
		case 83: /* DOWN */
		case 76:
		case 40:
			pressKey(KeyMap.DOWN);
			break;
		case 65: /* LEFT */
		case 75:
		case 37:
			pressKey(KeyMap.LEFT);
			break;
		case 68: /* RIGHT */
		case 186:
		case 39:
			pressKey(KeyMap.RIGHT);
			break;
		default:
			break;
		}
		pressKey(KeyMap.ANYKEY);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 87:	/*	UP		*/
		case 79:
		case 38:
			releaseKey(KeyMap.UP);
			break;
		case 83:	/*	DOWN	*/
		case 76:
		case 40:
			releaseKey(KeyMap.DOWN);
			break;
		case 65:	/*	LEFT	*/
		case 75:
		case 37:
			releaseKey(KeyMap.LEFT);
			break;
		case 68:	/*	RIGHT	*/
		case 186:
		case 39:
			releaseKey(KeyMap.RIGHT);
			break;
		default:
			break;
		}
		releaseKey(KeyMap.ANYKEY);
	}
}
