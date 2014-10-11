package elements;

import java.util.ArrayList;
import java.util.List;

import utilities.Console;
import characters.Player;

import components.PhysicsComponent;

public class Zone extends Member {
	List<Player> inside = new ArrayList<Player>();

	public Zone() {
	}

	public Zone(PhysicsComponent physics) {
		// zones are not drawn on screen, so graphics is null
		super(physics, null);
	}

	@Override
	public Member clone() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCollide(Player p) {
		if ((p.get(PhysicsComponent.class)).getBounds().intersects(
				(p.get(PhysicsComponent.class)), 0, 0)) {
			if (inside.contains(p)) {
				onEntry(p);
			} else {
				onExit(p);
			}
		}
	}

	@Override
	public void update() {
		super.update();
	}

	private void onEntry(Player p) {
		Console.log(p.getName() + " has just Entered this zone",
				Console.in.INFO);
	}

	private void onExit(Player p) {
		Console.log(p.getName() + " has just Exited this zone", Console.in.INFO);
	}
}
