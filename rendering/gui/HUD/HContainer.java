package gui.HUD;

import items.Container;
import items.Item;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import utilities.Console;
import utilities.Console.in;
import characters.Player;

import components.GraphicsComponent;

public class HContainer extends HElement {
	private Container b;
	private Player player;
	private int nWidth, nHeight;

	public HContainer(int x, int y, int width, int height, Player player) {
		super(x, y, width, height);
		this.b = player.getContainer();
		this.player = player;
		nWidth = width / 5;
		nHeight = height / 10;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, x, y, width, height, null);

		int zx = 0, zy = 0;
		if (b != null) {
			for (Item i : b.getItems()) {
				g.drawImage((i.get(GraphicsComponent.class)).getImage(), x
						+ (zx * nWidth), y + (zy * nHeight), nWidth, nHeight,
						null);
				zx++;
				if (zx * nWidth >= width) {
					zx = 0;
					zy++;
				}
			}
		}
		g.setColor(Color.black);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawRect(x, y, width, height);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		drop(e.getPoint());
		Console.log("Mouse clicked on the BACKPACK", in.INFO);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		drop(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void update() {
		b = player.getContainer();
	}

	private void drop(Point p) {
		if (b != null && player != null) {
			int zx = 0, zy = 0, px = 0;
			for (Item i : b.getItems()) {
				if (px > b.getTotal()) {
					return;
				}
				Rectangle r = new Rectangle(x + (zx * nWidth), y
						+ (zy * nHeight), nWidth, nHeight);
				if (r.contains(p)) {
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
	}
}
