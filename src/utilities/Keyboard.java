package utilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Keyboard implements KeyListener {

	private static boolean[] keys = new boolean[512];;
	private static List<Integer> pressed = new ArrayList<Integer>();

	public static int KEY_UP = KeyEvent.VK_W, KEY_DOWN = KeyEvent.VK_S,
			KEY_LEFT = KeyEvent.VK_D, KEY_RIGHT = KeyEvent.VK_A;

	public static boolean getKey(int k) {
		if (k >= 0 && k < keys.length) {
			return keys[k];
		} else {
			return false;
		}
	}

	public static boolean[] getKeys() {
		return keys;
	}

	public static List<Integer> getPressed() {
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

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = true;
		}
		keys[KeyEvent.VK_SHIFT] = e.isShiftDown();
		keys[KeyEvent.VK_ALT] = e.isAltDown();
		keys[KeyEvent.VK_CONTROL] = e.isControlDown();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = false;
		}
		keys[KeyEvent.VK_SHIFT] = e.isShiftDown();
		keys[KeyEvent.VK_ALT] = e.isAltDown();
		keys[KeyEvent.VK_CONTROL] = e.isControlDown();

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}