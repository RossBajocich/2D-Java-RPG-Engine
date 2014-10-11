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

	static HashMap<Integer, Container> containers = new HashMap<Integer, Container>();
	static HashMap<Integer, Decoration> decorations = new HashMap<Integer, Decoration>();
	static HashMap<Integer, Item> items = new HashMap<Integer, Item>();
	static HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	static HashMap<Integer, Prop> props = new HashMap<Integer, Prop>();

	public static void addContainer(final int id, final Container c) {
		containers.put(id, c);
	}

	public static void addDecoration(final int id, final Decoration d) {
		decorations.put(id, d);
	}

	public static void addItem(final int id, final Item i) {
		items.put(id, i);
	}

	public static void addPlayer(final int id, final Player p) {
		players.put(id, p);
	}

	public static void addProp(final int id, final Prop i) {
		props.put(id, i);
	}

	public static Member createElement(final int id, final int x, final int y,
			final ElementData ed) {
		Member e = null;

		switch (ed.elementType.toLowerCase()) {
		case "prop":
			e = props.get(id).clone();
			break;
		case "decoration":
			e = decorations.get(id).clone();
			break;
		case "player":
			final Player t = players.get(id);
			final Player p = new Player(t.get(PhysicsComponent.class),
					t.get(GraphicsComponent.class),
					t.get(InteractComponent.class),
					t.get(AttackComponent.class), t.get(InputComponent.class));
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

		e.get(PhysicsComponent.class).setX(x);
		e.get(PhysicsComponent.class).setY(y);
		e.setLevel(ed.level);
		e.setName(ed.name);
		return e;
	}
}
