package utilities;

import game.Game;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
	private static Game g;

	public Mouse(Game g) {
		Mouse.g = g;
	}

	public Point getMousePos() {
		PointerInfo a = MouseInfo.getPointerInfo();
		return a.getLocation();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		g.getHUD().onMousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
	}
}
