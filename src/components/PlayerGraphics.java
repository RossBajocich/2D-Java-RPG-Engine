package components;

import components.PhysicsComponent.Direction;

public class PlayerGraphics extends GraphicsComponent {

	public PlayerGraphics() {
		super();
	}

	public void update(){
		if (modify.getPhysics().getDirection() != Direction.DOWN) {
			switch (modify.getPhysics().getDirection()) {
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
