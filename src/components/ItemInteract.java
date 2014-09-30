package components;

import items.Item;
import utilities.Console;
import utilities.Console.in;
import characters.Player;
import elements.Member;

public class ItemInteract extends InteractComponent {

	Item i;

	public ItemInteract() {
		super();
	}

	public void setModify(Member modify) {
		this.modify = modify;
		if (!(modify instanceof Item)) {
			Console.log(
					"Using ItemInteract on non-item Member! uknown results may occur",
					in.INFO);
		} else {
			i = (Item) modify;
		}
	}

	@Override
	public void onInteract(Player p) {
		// not a backpack
		if (p.getContainer() != null
				&& p.getContainer().getTotal() + i.getStats().size <= p
						.getContainer().getSize()) {
			p.getContainer().addItem(i);
			i.remove();
		}
	}

}
