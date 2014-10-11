package game;

import elements.Member;
import graphics.ReferenceFrame;

import components.PhysicsComponent;

public class View extends ReferenceFrame {

	private Member follow;

	public View(int x, int y, int width, int height, Level current_level,
			Member p) {
		super(x, y, width, height, current_level);
		this.follow = p;
	}

	public void center(int newX, int newY) {
		x = newX - width / 2;
		y = newY - height / 2;
	}

	public void center(Member p) {
		follow = p;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		update();
		return x;
	}

	public int getY() {
		update();
		return y;
	}

	public void update() {
		int centerX = (int) ((follow.get(PhysicsComponent.class)).getX() + ((follow
				.get(PhysicsComponent.class)).getBoundsRect().width / 2));
		int centerY = (int) ((follow.get(PhysicsComponent.class)).getY() + ((follow
				.get(PhysicsComponent.class)).getBoundsRect().height / 2));

		if ((centerX - (width / 2) >= 0)
				&& (centerX + (width / 2) < current_level.getWidth())) {
			x = (int) (centerX - (width / 2));
		}
		if ((centerY - (height / 2) >= 0)
				&& (centerY + (height / 2) < current_level.getHeight())) {
			y = (int) (centerY - (height / 2));
		}
	}
}
