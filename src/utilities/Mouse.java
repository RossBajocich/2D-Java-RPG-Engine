package utilities;

import game.Game;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Mouse extends MouseAdapter {
	private static Game g;
	static LinkedList<mListener> listeners = new LinkedList<mListener>();

	public Mouse(Game g) {
		Mouse.g = g;
	}

	public Point getMousePos() {
		PointerInfo a = MouseInfo.getPointerInfo();
		return a.getLocation();
	}

	public static void addListener(mListener m, Functor f) {
		listeners.add(m);
	}

	public static void removeListener(mListener m) {
		listeners.remove(m);
	}

	public static LinkedList<mListener> getListeners() {
		return listeners;
	}

	public void mousePressed(MouseEvent e) {
		g.getHUD().onMousePressed(e);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
