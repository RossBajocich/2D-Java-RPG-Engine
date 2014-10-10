package components;

import items.Container;
import utilities.Console;
import utilities.Console.in;
import characters.Player;
import elements.Member;

public class ContainerInteract extends InteractComponent {

	public ContainerInteract() {
		super();
	}
	
	public void onInteract(Member sender) {
		//TODO: add gui to popup with items inside if is a "treasure chest" or if it is a "backpack"
		if (sender.getType().equalsIgnoreCase("player")) {
			if (modify.getType().equalsIgnoreCase("container")) {
				Console.log("Item is a container!", in.INFO);
				((Player) sender).setContainer((Container) modify);
				modify.getLevel().removeMember(modify);
				return;
			} else {
				Console.log(
						"item is not a container type equals "
								+ modify.getType(), in.INFO);
			}
		}
	}
}
