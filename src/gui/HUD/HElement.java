package gui.HUD;

import gui.BufferedScreen;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class HElement {
	BufferedImage image;
	int x, y, width, height;

	public HElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public abstract void update();

	public void reset() {

	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public abstract void draw(BufferedScreen s);

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
}
