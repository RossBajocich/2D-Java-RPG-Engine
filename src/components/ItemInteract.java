package components;

import items.Item;
import utilities.Console;
import utilities.Console.in;
import characters.Player;
import elements.Member;

public class ItemInteract extends InteractComponent {
	public ItemInteract() {
		super();
	}

	@Override
	protected void onInteract(Member sender) {
		Console.log("was interacted by sender " + sender.getName(), in.INFO);
		if (sender.getType().equalsIgnoreCase("player")
				&& modify.getType().equalsIgnoreCase("item")) {
			if (((Player) sender).getContainer() != null
					&& ((Player) sender).getContainer().getTotal()
							+ ((Item) modify).getStats().size <= ((Player) sender)
							.getContainer().getSize()) {
				((Player) sender).getContainer().addItem((Item) modify);
				modify.getLevel().removeMember(modify);
			} else {
				Console.log("Item failed container = "
						+ ((Player) sender).getContainer().getSize(), in.INFO);
			}
		} else {
			Console.log(
					"Sender is not a player! Must be player to interact with Item",
					in.INFO);
		}
	}
}
