package components;

import java.awt.event.KeyEvent;

import utilities.Console;
import utilities.Keyboard;
import characters.Player;

import components.PhysicsComponent.Direction;

import elements.Member;

public class PlayerInput extends InputComponent {
	public PlayerInput() {
		super();
	}

	@Override
	public void setModify(Member m) {
		this.modify = m;
		if (!(m instanceof Player)) {
			Console.log("Cannot have non player modifier for PlayerInput!",
					Console.in.ERROR);
		}
	}

	@Override
	public void update() {
		boolean isMoving = false;
		if (Keyboard.getKey(KeyEvent.VK_W)) {
			(modify.get(PhysicsComponent.class)).move(Direction.UP);
			(modify.get(GraphicsComponent.class))
					.setCurrentAnimation("up_walk");
			isMoving = true;
		}
		if (Keyboard.getKey(KeyEvent.VK_S)) {
			(modify.get(PhysicsComponent.class)).move(Direction.DOWN);
			(modify.get(GraphicsComponent.class))
					.setCurrentAnimation("down_walk");
			isMoving = true;
		}
		if (Keyboard.getKey(KeyEvent.VK_A)) {
			(modify.get(PhysicsComponent.class)).move(Direction.LEFT);
			(modify.get(GraphicsComponent.class))
					.setCurrentAnimation("left_walk");
			isMoving = true;
		}
		if (Keyboard.getKey(KeyEvent.VK_D)) {
			(modify.get(PhysicsComponent.class)).move(Direction.RIGHT);
			(modify.get(GraphicsComponent.class))
					.setCurrentAnimation("right_walk");
			isMoving = true;
		}
		(modify.get(GraphicsComponent.class)).getCurrentAnimation().setRunning(
				isMoving);

		if (Keyboard.getKey(KeyEvent.VK_F)) {
			((modify).get(AttackComponent.class)).attack();
		}
		if (Keyboard.getKey(KeyEvent.VK_I)) {
			(modify.get(InteractComponent.class)).interact();
		}
	}
}
