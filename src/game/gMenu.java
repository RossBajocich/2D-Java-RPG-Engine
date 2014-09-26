package game;

import java.awt.Color;

import gui.BufferedScreen;

public class gMenu {
	enum direction {
		UP, DOWN
	}

	Game g;
	int current = 0;
	private int max = 2;
	String[] options;

	public gMenu(Game game) {
		this.g = game;
		options = new String[max];
		options[0] = "Resume";
		options[1] = "Exit";
	}

	public void move(direction d) {
		if (d == direction.UP) {
			if (current < max - 1) {
				current++;
			} else {
				current = 0;
			}
		} else if (d == direction.DOWN) {
			if (current > 0) {
				current--;
			} else {
				current = max - 1;
			}
		}
	}

	public void select() {
		switch (current) {
		case 0:

		case 1:

		}
	}

	public void draw(BufferedScreen scr) {
		int w = scr.getWidth() / 2;
		int h = scr.getHeight() / 2;
		for (int i = 0; i < options.length; i++) {
			if (i == current) {
				scr.getGraphics().setColor(Color.YELLOW);
			}else{
				scr.getGraphics().setColor(Color.WHITE);
			}
			scr.getGraphics().drawString(options[i], w,
					(h - (options.length / 2)) * (i + 1));
		}
	}
}
