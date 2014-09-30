package elements;

import game.Level;

import java.util.ArrayList;
import java.util.List;

import components.Component;
import components.GraphicsComponent;
import components.PhysicsComponent;

public abstract class Member {

	protected String name, type;

	protected Level level;
	protected List<Component> components = new ArrayList<Component>();
	protected GraphicsComponent graphics;
	protected PhysicsComponent physics;

	public Member(PhysicsComponent physics, GraphicsComponent graphics) {
		this.physics = physics;
		this.graphics = graphics;
		physics.setModify(this);
		graphics.setModify(this);
		components.add(physics);
		components.add(graphics);
	}

	public Member() {
	}

	public List<Component> getComponents() {
		return components;
	}

	public abstract Member clone();

	public void update() {
		for (Component c : components) {
			c.update();
		}
	}

	public void remove() {
		this.name = null;
		level.remove(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
	}

	public void setType(String string) {
		type = string;
	}

	public String getType() {
		return type;
	}

	public GraphicsComponent getGraphics() {
		return graphics;
	}

	public PhysicsComponent getPhysics() {
		return physics;
	}

	public void setPhysics(PhysicsComponent physics) {
		this.physics = physics;
	}

	public void setGraphics(GraphicsComponent graphics) {
		this.graphics = graphics;
	}

	public void copy(Member m) {
		m.graphics = this.graphics;
		m.physics = this.physics;
		m.type = this.type;
		m.name = this.name;
		m.level = this.level;
	}
}
