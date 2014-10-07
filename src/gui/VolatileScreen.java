package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.VolatileImage;

public class VolatileScreen extends Screen {
	Graphics graphics;
	Image bg;

	public VolatileScreen(int x, int y, int width, int height,
			RenderManager manager) {
		super(x, y, width, height, manager);
		img = manager.getWindow().createVolatileImage(width, height);
	}

	@Override
	public void reset() {
		graphics = img.getGraphics();
		graphics.setColor(Color.RED);
		graphics.fillRect(0, 0, width, height);
	}
	
	public VolatileImage getImage() {
		return (VolatileImage) img;
	}
}
