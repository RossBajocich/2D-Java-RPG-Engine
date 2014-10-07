package game;


public class Menu {
	enum direction {
		UP, DOWN
	}

	Game g;
	int current = 0;
	private int max = 2;
	String[] options;

	public Menu(Game game) {
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
}
