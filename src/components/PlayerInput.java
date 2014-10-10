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
			((PhysicsComponent) modify.get(PhysicsComponent.class))
					.move(Direction.UP);
			((GraphicsComponent) modify.get(GraphicsComponent.class))
					.setCurrentAnimation("up_walk");
			isMoving = true;
		}
		if (Keyboard.getKey(KeyEvent.VK_S)) {
			((PhysicsComponent) modify.get(PhysicsComponent.class))
					.move(Direction.DOWN);
			((GraphicsComponent) modify.get(GraphicsComponent.class))
					.setCurrentAnimation("down_walk");
			isMoving = true;
		}
		if (Keyboard.getKey(KeyEvent.VK_A)) {
			((PhysicsComponent) modify.get(PhysicsComponent.class))
					.move(Direction.LEFT);
			((GraphicsComponent) modify.get(GraphicsComponent.class))
					.setCurrentAnimation("left_walk");
			isMoving = true;
		}
		if (Keyboard.getKey(KeyEvent.VK_D)) {
			((PhysicsComponent) modify.get(PhysicsComponent.class))
					.move(Direction.RIGHT);
			((GraphicsComponent) modify.get(GraphicsComponent.class))
					.setCurrentAnimation("right_walk");
			isMoving = true;
		}
		((GraphicsComponent) modify.get(GraphicsComponent.class))
				.getCurrentAnimation().setRunning(isMoving);

		if (Keyboard.getKey(KeyEvent.VK_F)) {
			((AttackComponent) (modify).get(AttackComponent.class)).attack();
		}
		if (Keyboard.getKey(KeyEvent.VK_I)) {
			((InteractComponent) modify.get(InteractComponent.class))
					.interact();
		}
	}
}
