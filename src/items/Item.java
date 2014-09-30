package items;

import utilities.Stats;

import components.GraphicsComponent;
import components.ItemInteract;
import components.PhysicsComponent;

import elements.Member;

public class Item extends Member {

	private Stats stats;
	private ItemInteract interact;

	public Item(PhysicsComponent physics, GraphicsComponent graphics,
			ItemInteract interact) {
		super(physics, graphics);
		this.interact = interact;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats s) {
		stats = s;
	}

	@Override
	public Member clone() {
		Item i = new Item(physics, graphics, interact);
		i.stats = stats;
		i.name = name;
		i.type = type;

		super.copy(i);

		return i;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
