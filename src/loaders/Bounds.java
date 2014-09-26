package loaders;

import java.awt.Rectangle;

public class Bounds {
	public int bw, bh, bx, by;

	// add shape support
	public Bounds(int bw, int bh, int bx, int by) {
		this.bw = bw;
		this.bh = bh;
		this.bx = bx;
		this.by = by;
	}

	public Bounds(int bw, int bh) {
		this.bw = bw;
		this.bh = bh;
		bx = 0;
		by = 0;
	}

	public Rectangle getBounds() {
		return new Rectangle(bx, by, bw, bh);
	}
	
	public Rectangle getBounds(int xOffset, int yOffset) {
		return new Rectangle(xOffset + bx, yOffset + by, bw, bh);
	}
}
