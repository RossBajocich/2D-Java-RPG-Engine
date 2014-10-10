package items;

import utilities.Stats;

import components.GraphicsComponent;
import components.ItemInteract;
import components.PhysicsComponent;

import elements.Member;

public class Item extends Member {

	private Stats stats;

	public Item(PhysicsComponent physics, GraphicsComponent graphics,
			ItemInteract interact) {
		super(physics, graphics);
		components.put(ItemInteract.class, interact);
		interact.setModify(this);
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats s) {
		stats = s;
	}

	@Override
	public Member clone() {
		Item i = new Item(
				(PhysicsComponent) components.get(PhysicsComponent.class),
				(GraphicsComponent) components.get(GraphicsComponent.class),
				(ItemInteract) components.get(ItemInteract.class));
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
