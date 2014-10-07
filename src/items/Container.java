package items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utilities.Console;
import utilities.Stats;

import components.GraphicsComponent;
import components.PhysicsComponent;

import elements.Member;

public class Container extends Member {
	public int size, count = 0;
	List<Item> items = new ArrayList<Item>();
	protected Stats stats;

	public Container(PhysicsComponent physics, GraphicsComponent graphics,
			int size) {
		super(physics, graphics);
		this.size = size;
		stats = new Stats();
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void changeSize(int add) {
		size += add;
	}

	public void addItem(Item item) {
		if (count + 1 < size) {
			items.add(item);
			count++;
			updateStats();
		} else {
			Console.log("This backpack is too small to add " + 1
					+ " items, size = " + size, Console.in.INFO);
		}
	}

	public void dropItems() {
		for (Item key : items) {
			dropItem(key);
		}
	}

	public void dropItem(Item key) {
		Random r = new Random();

		PhysicsComponent physics = (PhysicsComponent) components
				.get(PhysicsComponent.class);
		GraphicsComponent graphics = (GraphicsComponent) components
				.get(GraphicsComponent.class);

		// Not correct, need to fix this to use the players values
		((PhysicsComponent) key.get(PhysicsComponent.class)).setX(physics
				.getX() + r.nextInt(graphics.getWidth()));
		((PhysicsComponent) key.get(PhysicsComponent.class)).setY(physics
				.getY() + r.nextInt(graphics.getHeight()));

		level.addMember(key);

		removeItem(key);
	}

	public void removeItem(Item item) {
		items.remove(item); // TODO add stack support with int value in each
							// item object (if item.isAStack then decrement
							// stack variable) NO LIST!!!
		count--;
		updateStats();
	}

	public int getTotal() {
		// TODO Auto-generated method stub
		return count;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return size;
	}

	public List<Item> getItems() {
		return items;
	}

	protected void updateStats() {
		stats = null;
		stats = new Stats();
		for (Item i : items) {
			stats.add(i.getStats());
		}
	}

	public Stats getStats() {
		return stats;
	}

	@Override
	public Member clone() {
		Container c = new Container(null, null, count);
		for (Item i : items) {
			c.addItem(i);
		}
		c.updateStats();
		c.type = type;

		super.copy(c);

		return c;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
