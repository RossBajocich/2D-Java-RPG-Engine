package components;

import components.PhysicsComponent.Direction;

public class PlayerGraphics extends GraphicsComponent {

	public PlayerGraphics() {
		super();
	}

	public void update() {
		if (((PhysicsComponent) modify.get(PhysicsComponent.class))
				.getDirection() != Direction.DOWN) {
			switch (((PhysicsComponent) modify.get(PhysicsComponent.class))
					.getDirection()) {
			case DOWN:
				setCurrentAnimation("down_walk");
				break;
			case UP:
				setCurrentAnimation("up_walk");
				break;
			case LEFT:
				setCurrentAnimation("left_walk");
				break;
			case RIGHT:
				setCurrentAnimation("right_walk");
				break;
			default:
				setCurrentAnimation("default");
				break;
			}
		}
	}

}
