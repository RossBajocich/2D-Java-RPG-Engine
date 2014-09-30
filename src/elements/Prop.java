package elements;

import components.GraphicsComponent;
import components.InteractComponent;
import components.PhysicsComponent;

public class Prop extends Member {

	protected InteractComponent interact;

	public Prop(PhysicsComponent physics, GraphicsComponent graphics,
			InteractComponent interact) {
		super(physics, graphics);
		this.interact = interact;
		components.add(interact);
	}

	@Override
	public Member clone() {
		Prop p = new Prop(physics, graphics, interact);

		super.copy(p);

		return p;
	}

	@Override
	public void update() {
		super.update();
	}
}
