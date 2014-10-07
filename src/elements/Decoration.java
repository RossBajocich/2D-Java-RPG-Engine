package elements;

import components.GraphicsComponent;
import components.PhysicsComponent;

public class Decoration extends Member {
	public Decoration(PhysicsComponent physics, GraphicsComponent graphics) {
		super(physics, graphics);
	}

	@Override
	public Member clone() {
		Decoration p = new Decoration(null, null);
		super.copy(p);

		return p;
	}

	@Override
	public void update() {
		super.update();
	}
}
