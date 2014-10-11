package loaders;

import java.awt.Rectangle;

import components.PhysicsComponent;

public class Bounds {
	Rectangle rect;

	public Bounds(int bw, int bh) {
		rect = new Rectangle(0, 0, bw, bh);
	}

	// add shape support
	public Bounds(int bx, int by, int bw, int bh) {
		rect = new Rectangle(bx, by, bw, bh);
	}

	public Rectangle getRect() {
		return rect;
	}

	public Rectangle getRect(int xOffset, int yOffset) {
		return new Rectangle(xOffset + rect.x, yOffset + rect.y, rect.width,
				rect.height);
	}

	/*
	 * Argument p = the object checking against, uses the x and y in getBounds()
	 * for offsets. Argument x,y = the new x & y of this bounds object
	 */
	public boolean intersects(PhysicsComponent p, int x, int y) {
		return p.getBoundsRect().intersects(this.getRect(x, y));
	}
}
