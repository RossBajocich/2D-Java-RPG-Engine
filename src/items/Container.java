package items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utilities.Console;
import utilities.Stats;

import components.ContainerInteract;
import components.GraphicsComponent;
import components.InteractComponent;
import components.PhysicsComponent;

import elements.Member;

public class Container extends Member {
	public int size, count = 0;
	List<Item> items = new ArrayList<Item>();
	protected Stats stats;

	public Container(PhysicsComponent physics, GraphicsComponent graphics,
			ContainerInteract interact) {
		super(physics, graphics);
		stats = new Stats();
		this.type = "container";
		this.components.put(InteractComponent.class, interact);
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

	public void changeSize(int add) {
		size += add;
	}

	@Override
	public Member clone() {
		Container c = new Container(this.get(PhysicsComponent.class),
				this.get(GraphicsComponent.class),
				(ContainerInteract) this.get(InteractComponent.class));
		for (Item i : items) {
			c.addItem(i);
		}
		c.updateStats();
		c.type = type;
		c.count = count;
		c.size = size;
		c.stats = stats;

		super.copy(c);

		return c;
	}

	public void dropItem(Item key) {
		Random r = new Random();

		PhysicsComponent physics = this.get(PhysicsComponent.class);
		GraphicsComponent graphics = this.get(GraphicsComponent.class);

		// Not correct, need to fix this to use the players values
		key.get(PhysicsComponent.class).setX(
				physics.getX() + r.nextInt(graphics.getWidth()));
		key.get(PhysicsComponent.class).setY(
				physics.getY() + r.nextInt(graphics.getHeight()));

		level.addMember(key);

		removeItem(key);
	}

	public void dropItems() {
		for (Item key : items) {
			dropItem(key);
		}
	}

	public List<Item> getItems() {
		return items;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return size;
	}

	public Stats getStats() {
		return stats;
	}

	public int getTotal() {
		// TODO Auto-generated method stub
		return count;
	}

	public void removeItem(Item item) {
		items.remove(item); // TODO add stack support with int value in each
							// item object (if item.isAStack then decrement
							// stack variable) NO LIST!!!
		count--;
		updateStats();
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	protected void updateStats() {
		stats = null;
		stats = new Stats();
		for (Item i : items) {
			stats.add(i.getStats());
		}
	}
}
