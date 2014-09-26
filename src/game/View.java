package game;

import characters.Player;

public class View {

	private int width, height, x, y, widthMax, heightMax;
	private Player follow;

	public View(int width, int height, int widthMax, int heightMax, Player p) {
		this.width = width;
		this.height = height;
		this.follow = p;
		this.widthMax = widthMax;
		this.heightMax = heightMax;
	}

	public int getX() {
		update();
		return x;
	}

	public int getY() {
		update();
		return y;
	}

	public void center(int newX, int newY) {
		x = newX - width / 2;
		y = newY - height / 2;
	}

	public void center(Player p) {
		follow = p;
	}

	public void update() {
		int centerX = (int) (follow.getX() + (follow.getWidth() / 2));
		int centerY = (int) (follow.getY() + (follow.getHeight() / 2));

		if ((centerX - (width / 2) >= 0) && (centerX + (width / 2) < widthMax)) {
			x = (int) (centerX - (width / 2));
		}
		if ((centerY - (height / 2) >= 0)
				&& (centerY + (height / 2) < heightMax)) {
			y = (int) (centerY - (height / 2));
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
