package utilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Keyboard implements KeyListener {

	private static boolean[] keys;
	private static LinkedList<Integer> pressed;

	public static int KEY_UP, KEY_DOWN, KEY_LEFT, KEY_RIGHT;

	public Keyboard() {
		// TODO Auto-generated constructor stub
		keys = new boolean[512];
		pressed = new LinkedList<Integer>();
		KEY_UP = KeyEvent.VK_W;
		KEY_DOWN = KeyEvent.VK_S;
		KEY_RIGHT = KeyEvent.VK_D;
		KEY_LEFT = KeyEvent.VK_A;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = true;
		}
		keys[KeyEvent.VK_SHIFT] = e.isShiftDown();
		keys[KeyEvent.VK_ALT] = e.isAltDown();
		keys[KeyEvent.VK_CONTROL] = e.isControlDown();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = false;
		}
		keys[KeyEvent.VK_SHIFT] = e.isShiftDown();
		keys[KeyEvent.VK_ALT] = e.isAltDown();
		keys[KeyEvent.VK_CONTROL] = e.isControlDown();

	}

	public boolean getKey(int k) {
		if (k >= 0 && k < keys.length) {
			return keys[k];
		} else {
			return false;
		}
	}

	public boolean[] getKeys() {
		return keys;
	}

	public LinkedList<Integer> getPressed() {
		if (pressed.size() > 0) {
			int i = 0;
			for (Integer x : pressed) {
				if (!getKey(x)) {
					pressed.remove(i);
				}
				i++;
			}
		}
		return pressed;
	}

}