package items;

import java.awt.Image;
import java.util.LinkedList;

import utilities.Console;
import utilities.Images;
import utilities.ItemStats;
import characters.Player;
import elements.Interactable;
import elements.Member;
import elements.Renderable;

public class Container extends Member implements Renderable, Interactable{
	public int size, count = 0, id;
	LinkedList<Item> items = new LinkedList<Item>();
	protected ItemStats stats;
	private String img;
	private Player p = null;

	public Container(int size) {
		this.size = size;
		stats = new ItemStats();
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

	public LinkedList<Item> getItems() {
		return items;
	}

	protected void updateStats() {
		stats = null;
		stats = new ItemStats();
		for (Item i : items) {
			stats.add(i.getStats());
		}
	}

	public ItemStats getStats() {
		return stats;
	}

	@Override
	public Member clone() {
		Container c = new Container(size);
		for(Item i : items){
			c.addItem(i);
		}
		c.updateStats();
		c.img = img;
		c.bounds = bounds;
		c.type = type;
		c.width = width;
		c.height = height;
		return c;
	}

	@Override
	public void setImage(String id) {
		// TODO Auto-generated method stub
		img = id;
		Images.add(id);
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return Images.get(img);
	}

	@Override
	public String getImageName() {
		// TODO Auto-generated method stub
		return img;
	}

	@Override
	public void interact(Interactable i) {
		//nada
	}

	@Override
	public void onInteract(Interactable i) {
		if(i instanceof Player){
			((Player) i).setContainer(this);
			level.remove(this);
		}
	}

	public void setPlayer(Player p) {
		this.p = p;
	}
}
