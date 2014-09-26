package elements;

public interface Clickable {
	public static enum button {
		LEFT, RIGHT, HOVER
	}

	public void onClick(int x, int y, button b);

}
