package elements;

import components.GraphicsComponent;
import components.PhysicsComponent;

public class Decoration extends Member {
	public Decoration(PhysicsComponent physics, GraphicsComponent graphics) {
		super(physics, graphics);
	}

	@Override
	public Member clone() {
		Decoration p = new Decoration(physics, graphics);
		super.copy(p);

		return p;
	}

	@Override
	public void update() {
		super.update();
	}
}
