package elements;

import game.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import components.Component;
import components.GraphicsComponent;
import components.PhysicsComponent;

public abstract class Member {

	protected String name, type;

	protected Level level;
	protected Map<Class<? extends Component>, Component> components = new HashMap<>();

	public Member(PhysicsComponent physics, GraphicsComponent graphics) {
		physics.setModify(this);
		graphics.setModify(this);
		components.put(PhysicsComponent.class, physics);
		components.put(GraphicsComponent.class, graphics);
	}

	public Member() {
	}

	/*
	 * Only to be used for looping through components to call abstract function
	 * update() on each
	 */
	public List<Component> getComponents() {
		List<Component> temp = new ArrayList<Component>(components.values());
		return temp;
	}

	public <T extends Component> T get(Class<? extends Component> clazz) {
		return (T) clazz.cast(components.get(clazz));
	}

	public void set(Component c) {
		components.put(c.getClass(), c);
		c.setModify(this);
	}

	public void setLevel(Level l) {
		this.level = l;
	}

	public Level getLevel() {
		return level;
	}

	public abstract Member clone();

	public void update() {
		for (Component c : components.values()) {
			c.update();
		}
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

	public void copy(Member m) {
		m.components.putAll(this.components);
		
		for(Component c : m.components.values()){
			c.setModify(m);
		}
		
		m.type = this.type;
		m.name = this.name;
		m.level = this.level;
	}
}
