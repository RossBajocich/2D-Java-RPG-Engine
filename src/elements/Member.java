package elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import world.Level;

import components.Component;
import components.GraphicsComponent;
import components.PhysicsComponent;

public abstract class Member {
	protected String name, type = "";

	protected Level level;
	protected Map<Class<? extends Component>, Component> components = new HashMap<>();

	public Member() {
	}

	public Member(PhysicsComponent physics, GraphicsComponent graphics) {
		physics.setModify(this);
		graphics.setModify(this);
		components.put(PhysicsComponent.class, physics);
		components.put(GraphicsComponent.class, graphics);
	}

	@Override
	public abstract Member clone();

	public void copy(Member m) {
		m.components.putAll(this.components);

		for (Component c : m.components.values()) {
			c.setModify(m);
		}

		m.type = this.type;
		m.name = this.name;
		m.level = this.level;
	}

	public <T> T get(Class<T> clazz) {
		// check if a component in the list is a subclass of clazz
		if (components.get(clazz) == null) {
			for (Entry<Class<? extends Component>, Component> c : components
					.entrySet()) {
				if (clazz.isAssignableFrom(c.getKey())) {
					return clazz.cast(c.getValue());
				}
			}
		}
		return clazz.cast(components.get(clazz));
	}

	/*
	 * Only to be used for looping through components to call abstract function
	 * update() on each
	 */
	public List<Component> getComponents() {
		List<Component> temp = new ArrayList<Component>(components.values());
		return temp;
	}

	public Level getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void set(Component c) {
		components.put(c.getClass(), c);
		c.setModify(this);
	}

	public void setLevel(Level l) {
		this.level = l;
	}

	public void setName(String s) {
		name = s;
	}

	public void setType(String string) {
		type = string;
	}

	public void update() {
		for (Component c : components.values()) {
			c.update();
		}
	}
}
