package characters;

import graphics.Animation;
import items.Container;
import loaders.ResourceLoader;
import utilities.Console;
import utilities.Console.in;
import utilities.Stats;
import components.AttackComponent;
import components.GraphicsComponent;
import components.InputComponent;
import components.InteractComponent;
import components.PhysicsComponent;
import elements.Member;

public class Player extends Member {
	protected boolean mainPlayer = false;
	protected Container container;
	protected Stats stats;

	public Player(PhysicsComponent physics, GraphicsComponent graphics,
			InteractComponent interact, AttackComponent attack,
			InputComponent input) {
		super(physics, graphics);

		input.setModify(this);
		attack.setModify(this);
		interact.setModify(this);

		components.put(InputComponent.class, input);
		components.put(AttackComponent.class, attack);
		components.put(InteractComponent.class, interact);
		init();
	}

	public void init() {
		Animation left_walk, right_walk, up_walk, down_walk;
		Animation die;

		left_walk = ResourceLoader.getAnimationFromSheet("link_sheet.png", 59,
				2, 18, 24, "left_walk", 3, 0, 200, true);
		right_walk = ResourceLoader.getAnimationFromSheet("link_sheet.png", 62,
				28, 18, 24, "right_walk", 3, 0, 200, true);
		up_walk = ResourceLoader.getAnimationFromSheet("link_sheet.png", 9, 28,
				17, 24, "up_walk", 3, 0, 200, true);
		down_walk = ResourceLoader.getAnimationFromSheet("link_sheet.png", 3,
				89, 18, 24, "down_walk", 4, 1, 200, true);

		die = new Animation(1000, false);
		die.addImage("dead.png");

		GraphicsComponent graphics = (GraphicsComponent) components
				.get(GraphicsComponent.class);

		graphics.addAnimation("left_walk", left_walk);
		graphics.addAnimation("right_walk", right_walk);
		graphics.addAnimation("up_walk", up_walk);
		graphics.addAnimation("down_walk", down_walk);
		graphics.addAnimation("die", die);

		graphics.setCurrentAnimation("down_walk");
	}

	public void update() {
		if (container != null) {
			((PhysicsComponent) container.get(PhysicsComponent.class))
					.setX(((PhysicsComponent) components
							.get(PhysicsComponent.class)).getX());
			((PhysicsComponent) container.get(PhysicsComponent.class))
					.setY(((PhysicsComponent) components
							.get(PhysicsComponent.class)).getY());
		}
		super.update();
	}

	public boolean isMainPlayer() {
		return mainPlayer;
	}

	public void setMainPlayer(boolean state) {
		mainPlayer = state;
	}

	public void setContainer(Container b) {
		this.container = b;
		//container.setSize(container.getSize() + temp.getSize());
		//for (Item i : temp.getItems()) {
		//	container.addItem(i);
		//}
	}

	public Container getContainer() {
		return container;
	}

	public Stats getStats() {
		Stats ps = new Stats(stats);
		if (container != null) {
			// Has a container at this point
			ps.add(container.getStats());
		}
		return ps;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public Member clone() {
		// This is null because copy(Player p) copies all components through the
		// super()
		Player p = new Player(null, null, null, null, null);
		copy(p);
		return p;
	}

	public void copy(Player p) {

		p.container = container;

		p.stats = stats;
		super.copy(p);
	}
}
