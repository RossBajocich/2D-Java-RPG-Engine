package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class BufferedScreen extends Screen {
	Graphics graphics;
	Image bg;

	public BufferedScreen(int x, int y, int width, int height,
			RenderManager manager) {
		super(x, y, width, height, manager);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public BufferedImage getImage() {
		return (BufferedImage) img;
	}

	@Override
	public void reset() {
		graphics = img.getGraphics();
		// graphics.clearRect(x, y, width, height);
	}
}
