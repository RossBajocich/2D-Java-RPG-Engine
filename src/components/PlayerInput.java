package components;

import java.awt.event.KeyEvent;

import components.PhysicsComponent.Direction;

import utilities.Console;
import utilities.Keyboard;
import utilities.Console.in;
import characters.Player;
import elements.Member;

public class PlayerInput extends InputComponent {

	Player player;

	public PlayerInput() {
		super();
	}

	public void setModify(Member m) {
		this.modify = (Player) m;
		if (!(m instanceof Player)) {
			Console.log("Cannot have non player modifier for PlayerInput!",
					Console.in.ERROR);
		}
	}

	public void update() {
		if (Keyboard.getKey(KeyEvent.VK_W)) {
			modify.getPhysics().move(Direction.UP);
			modify.getGraphics().setCurrentAnimation("up_walk");
		}
		if (Keyboard.getKey(KeyEvent.VK_S)) {
			modify.getPhysics().move(Direction.DOWN);
			modify.getGraphics().setCurrentAnimation("down_walk");
		}
		if (Keyboard.getKey(KeyEvent.VK_A)) {
			modify.getPhysics().move(Direction.LEFT);
			modify.getGraphics().setCurrentAnimation("left_walk");
		}
		if (Keyboard.getKey(KeyEvent.VK_D)) {
			modify.getPhysics().move(Direction.RIGHT);
			modify.getGraphics().setCurrentAnimation("right_walk");
		}

		if (Keyboard.getKey(KeyEvent.VK_F)) {
			((Player) modify).getAttack().attack();
		}
		if (Keyboard.getKey(KeyEvent.VK_I)) {
			((Player) player).getInteract().interact();
		}
	}
}
