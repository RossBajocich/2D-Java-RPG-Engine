package gui.HUD;

import gui.BufferedScreen;
import items.Container;
import items.Item;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import utilities.Console;
import utilities.Console.in;
import characters.Player;

public class HBackPack extends HElement {
	Container b;
	Player player;

	public HBackPack(int x, int y, int width, int height, Player player) {
		super(x, y, width, height);
		this.b = player.getContainer();
		this.player = player;
	}

	@Override
	public void update() {
		b = player.getContainer();
	}

	private void drop(Point p) {
		int zx = 0, zy = 0, px = 0;
		for (Item i : b.getItems()) {
			if (px > b.getTotal()) {
				return;
			}
			int nWidth = 20;
			int nHeight = 20;
			Rectangle r = new Rectangle(x + (zx * nWidth), y + (zy * nHeight),
					nWidth, nHeight);
			if (HUD.isWithin(p, r)) {
				player.getContainer().dropItem(i);
				return;
			}
			zx++;
			if (zx * nWidth >= width) {
				zx = 0;
				zy++;
			}
			px++;
		}
	}

	public void mouseClicked(MouseEvent e) {
		drop(e.getPoint());
		Console.log("Mouse clicked on the FUCKING BACKPACK", in.INFO);
	}

	public void mousePressed(MouseEvent e) {
		drop(e.getPoint());
	}

	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void draw(BufferedScreen s) {
		s.getGraphics().drawImage(image, x, y, width, height, null);
		if (b == null) {
			return;
		}
		Graphics2D g = s.getGraphics();
		int zx = 0, zy = 0;
		for (Item i : b.getItems()) {
			int nWidth = 20;
			int nHeight = 20;
			g.drawImage(i.getGraphics().getImage(), x + (zx * nWidth), y
					+ (zy * nHeight), nWidth, nHeight, null);
			zx++;
			if (zx * nWidth >= width) {
				zx = 0;
				zy++;
			}
		}
	}
}
