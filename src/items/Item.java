package items;

import java.awt.Image;

import utilities.Images;
import utilities.ItemStats;
import characters.Player;
import elements.Member;
import elements.Interactable;
import elements.Renderable;

public class Item extends Member implements Renderable, Interactable {

	protected String imgName;
	ItemStats stats;
	Player holder;

	public Item(double x, double y, int width, int height, Player holder) {
		super(x, y, width, height);
		this.holder = holder;
	}

	public Item() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setImage(String id) {
		imgName = id;
		Images.add(imgName);
	}

	@Override
	public Image getImage() {
		return Images.get(imgName);
	}

	@Override
	public void interact(Interactable i) {
		// nothing
	}

	@Override
	public void onInteract(Interactable i) {
		if (i instanceof Player) {
			Player p = (Player) i;

			// not a backpack
			if (p.getContainer() != null
					&& p.getContainer().getTotal() + stats.size <= p
							.getContainer().getSize()) {
				p.getContainer().addItem(this);
				this.holder = p;
				this.remove();
			}
		}
	}

	public void unEquip() {
		holder = null;
	}

	public void equip(Player p) {
		holder = p;
	}

	public ItemStats getStats() {
		return stats;
	}

	public void setStats(ItemStats s) {
		stats = s;
	}

	@Override
	public String getImageName() {
		// TODO Auto-generated method stub
		return imgName;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

	@Override
	public Member clone() {
		Item i = new Item();
		i.stats = stats;
		i.imgName = imgName;
		i.name = name;
		i.type = type;
		i.width = width;
		i.height = height;
		return i;
	}
}
