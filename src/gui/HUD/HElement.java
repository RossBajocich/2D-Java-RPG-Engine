package gui.HUD;

import gui.Screen;

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

	public abstract void draw(Screen s);

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void reset() {

	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public abstract void update();
}
