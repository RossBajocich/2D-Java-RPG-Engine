package elements;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import utilities.Console;
import characters.Player;

public class Zone {

	Map<Player, Boolean> states = new HashMap<Player, Boolean>();
	int x, y, width, height;

	public Zone(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Zone() {
		// TODO Auto-generated constructor stub
	}

	public Rectangle2D getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle2D.Double(x, y, width, height);
	}

	public boolean collide(Player p) {
		if (p.getBounds().intersects(getBounds())) {
			if (!states.containsKey(p)) {
				states.put(p, false);
			}
			if (states.get(p) == false) {
				onEntry(p);
			} else {
				onExit(p);
			}
			return true;
		}
		return false;
	}

	private void onEntry(Player p) {
		Console.log(p.getName() + " has just Entered this zone",
				Console.in.INFO);
	}

	private void onExit(Player p) {
		Console.log(p.getName() + " has just Exited this zone",
				Console.in.INFO);
	}
}
