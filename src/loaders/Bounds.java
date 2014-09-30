package loaders;

import java.awt.Rectangle;

public class Bounds {
	Rectangle rect;
	
	// add shape support
	public Bounds(int bw, int bh, int bx, int by) {
		rect = new Rectangle(bx,by,bw,bh);
	}

	public Bounds(int bw, int bh) {
		rect = new Rectangle(0,0,bw,bh);
	}

	public Rectangle getBounds() {
		return rect;
	}
	
	public Rectangle getBounds(int xOffset, int yOffset) {
		return new Rectangle(xOffset + rect.x, yOffset + rect.y, rect.width, rect.height);
	}

	public boolean intersects(Rectangle bounds) {
		// TODO Auto-generated method stub
		return rect.intersects(bounds.getBounds());
	}
}
