package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class Screen {
	Image img;
	BufferedImage bg;
	int x, y, width, height;
	
	public Screen(int x, int y, int width, int height, RenderManager manager) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public Image getImage(){
		return img;
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setBackground(Image b) {
		if (b != null) {
			this.bg = (BufferedImage) b;
		} else {
			throw new NullPointerException("Background image is null!");
		}
	}

	public abstract void reset();

	public Graphics2D getGraphics() {
		return (Graphics2D) img.getGraphics();
	}
}
