package loaders;

import items.Container;
import items.Item;

import java.util.HashMap;

import characters.Player;

import components.AttackComponent;
import components.GraphicsComponent;
import components.InputComponent;
import components.InteractComponent;
import components.PhysicsComponent;
import components.PlayerInput;

import elements.Decoration;
import elements.Member;
import elements.Prop;

public class TypeMaker {

	static HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	static HashMap<Integer, Item> items = new HashMap<Integer, Item>();
	static HashMap<Integer, Decoration> decorations = new HashMap<Integer, Decoration>();
	static HashMap<Integer, Prop> props = new HashMap<Integer, Prop>();
	static HashMap<Integer, Container> containers = new HashMap<Integer, Container>();

	public static void addPlayer(int id, Player p) {
		players.put(id, p);
	}

	public static void addItem(int id, Item i) {
		items.put(id, i);
	}

	public static void addDecoration(int id, Decoration d) {
		decorations.put(id, d);
	}

	public static void addProp(int id, Prop i) {
		props.put(id, i);
	}

	public static void addContainer(int id, Container c) {
		containers.put(id, c);
	}

	public static Member createElement(int id, int x, int y, ElementData ed) {
		Member e = null;

		switch (ed.elementType.toLowerCase()) {
		case "prop":
			e = props.get(id).clone();
			break;
		case "decoration":
			e = decorations.get(id).clone();
			break;
		case "player":
			Player t = players.get(id);
			Player p = new Player(
					((PhysicsComponent) t.get(PhysicsComponent.class)),
					((GraphicsComponent) t.get(GraphicsComponent.class)),
					((InteractComponent) t.get(InteractComponent.class)),
					((AttackComponent) t.get(AttackComponent.class)),
					((InputComponent) t.get(InputComponent.class)));
			if (ed.mainPlayer) {
				ed.level.setMainPlayer(p);
				p.setMainPlayer(ed.mainPlayer);
				p.set(new PlayerInput());
			}

			t.copy(p);
			p.setName(ed.name);

			e = p;
			break;
		case "item":
			e = items.get(id).clone();
			break;
		case "container":
			e = containers.get(id).clone();
			break;
		default:
			return null;
		}

		((PhysicsComponent) e.get(PhysicsComponent.class)).setX(x);
		((PhysicsComponent) e.get(PhysicsComponent.class)).setY(y);
		e.setLevel(ed.level);

		return e;
	}
}
