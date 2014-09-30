package characters;

import graphics.Animation;
import items.Container;
import items.Item;
import loaders.ResourceLoader;
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

	protected AttackComponent attack;
	protected InputComponent input;
	protected InteractComponent interact;

	protected Stats stats;

	public Player(PhysicsComponent physics, GraphicsComponent graphics,
			InteractComponent interact, AttackComponent attack,
			InputComponent input) {
		super(physics, graphics);
		this.interact = interact;
		this.attack = attack;
		this.input = input;
		
		input.setModify(this);
		attack.setModify(this);
		interact.setModify(this);

		components.add(input);
		components.add(attack);
		components.add(interact);
		init();
	}

	public void init() {
		Animation left_walk, right_walk, up_walk, down_walk;
		// Animation left_stand, right_stand, up_stand, down_stand;
		Animation die;

		left_walk = ResourceLoader.getAnimationFromSheet("link_sheet.png", 59,
				2, 18, 24, "left_walk", 3, 0, 200, true);
		right_walk = ResourceLoader.getAnimationFromSheet("link_sheet.png", 62,
				28, 18, 24, "right_walk", 3, 0, 200, true);
		up_walk = ResourceLoader.getAnimationFromSheet("link_sheet.png", 9, 28,
				17, 24, "up_walk", 3, 0, 200, true);
		down_walk = ResourceLoader.getAnimationFromSheet("link_sheet.png", 3,
				89, 18, 24, "down_walk", 4, 1, 200, true);

		/*
		 * left_stand = ResourceLoader.getAnimationFromSheet("link_sheet.png",
		 * 59, 2, 18, 24, "left_stand", 3, 200, true); right_stand =
		 * ResourceLoader.getAnimationFromSheet("link_sheet.png", 59, 2, 18, 24,
		 * "right_stand", 3, 200, true); up_stand =
		 * ResourceLoader.getAnimationFromSheet("link_sheet.png", 59, 2, 18, 24,
		 * "up_stand", 3, 200, true); left_stand =
		 * ResourceLoader.getAnimationFromSheet("link_sheet.png", 59, 2, 18, 24,
		 * "down_stand", 3, 200, true);
		 */

		die = new Animation(1000, false);
		die.addImage("dead.png");

		graphics.addAnimation("left_walk", left_walk);
		graphics.addAnimation("right_walk", right_walk);
		graphics.addAnimation("up_walk", up_walk);
		graphics.addAnimation("down_walk", down_walk);
		graphics.addAnimation("die", die);

		graphics.setCurrentAnimation("down_walk");
	}

	public void update() {
		super.update();
	}

	public boolean isMainPlayer() {
		return mainPlayer;
	}

	public void setMainPlayer(boolean state) {
		mainPlayer = state;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setContainer(Container b) {
		Container temp = container;
		container = b;
		container.setSize(container.getSize() + temp.getSize());
		for (Item i : temp.getItems()) {
			container.addItem(i);
		}
	}

	public Container getContainer() {
		return container;
	}

	public Stats getStats() {
		Stats ps = new Stats(stats);
		if (container != null) {
			//Has a container at this point
			ps.add(container.getStats());
		}
		return ps;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public Member clone() {
		Player p = new Player(physics, graphics, interact, attack, input);
		copy(p);
		return p;
	}

	public void copy(Player p) {
		p.container = container;

		p.stats = stats;
		
		p.interact = interact;
		p.input = input;
		p.attack = attack;

		super.copy(p);
	}

	public AttackComponent getAttack() {
		return attack;
	}

	public InputComponent getInput() {
		return input;
	}

	public InteractComponent getInteract() {
		return interact;
	}

	public void setAttack(AttackComponent attack) {
		components.remove(attack);
		this.attack = attack;
		attack.setModify(this);
		components.add(attack);
	}

	public void setInput(InputComponent input) {
		components.remove(input);
		this.input = input;
		input.setModify(this);
		components.add(input);
	}

	public void setInteract(InteractComponent interact) {
		components.remove(interact);
		this.interact = interact;
		interact.setModify(this);
		components.add(interact);
	}
}
