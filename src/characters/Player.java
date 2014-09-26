package characters;

import items.Container;
import items.Item;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import loaders.ResourceLoader;
import utilities.Clock;
import utilities.Console;
import utilities.Console.in;
import utilities.Functor;
import utilities.Globals;
import utilities.Globals.Direction;
import utilities.Images;
import utilities.Images.EXT;
import utilities.PlayerStats;
import elements.Clickable;
import elements.Collidable;
import elements.Interactable;
import elements.Member;
import elements.Renderable;
import game.Position;
import game.View;
import graphics.Animation;

public class Player extends Member implements Renderable, Collidable,
		Interactable, Clickable {

	public static final double interactdistance = 100;
	public static double attackDistance = 75;
	protected Globals.Direction direction = Globals.Direction.UP;
	protected boolean mainPlayer = false;
	protected Position old;
	protected String imgID;
	protected Container container = new Container(0);
	protected PlayerStats stats = new PlayerStats();
	protected String typeName = "";
	protected int health;
	protected long lastAttack = 0, lastInteract = 0;
	protected boolean dead;
	protected float interactWait = 0.5f;
	protected int maxMana;
	protected HashMap<String, Animation> animations = new HashMap<String, Animation>();
	protected Animation current_anim;

	public Player(double x, double y, int width, int height) {
		super(x, y, width, height);
		captureOld();
	}

	public Player() {
		super();
		captureOld();
		Animation left = new Animation(1000, true);
		left.addImage("link_2.png");
		
		Animation right = new Animation(1000, true);
		right.addImage("link_4.png");
		
		Animation up = new Animation(1000, true);
		up.addImage("link_3.png");
		
		Animation down = new Animation(1000, true);
		down.addImage("link_1.png");
		
		//Animation die = ResourceLoader.getAnimationFromSheet("link_die.png", 0, 0, 18, 25, "die", 5, 2000, false);
		Animation die = new Animation(1000, false);
		die.addImage("dead.png");
		
		animations.put("left", left);
		animations.put("right", right);
		animations.put("up", up);
		animations.put("down", down);
		animations.put("die", die);
		
		current_anim = animations.get("down");
	}

	public boolean isMainPlayer() {
		return mainPlayer;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String tn) {
		typeName = tn;
	}

	public void setHealth(int h) {
		this.health = h;
	}

	public void addHealth(int h) {
		if (health + h <= 0) {
			health = 0;
			die();
		} else if (health + h >= stats.health) {

		} else {
			health += h;
		}
	}

	private void die() {
		// Images.add("dead.png");
		current_anim = animations.get("die");
		dead = true;
		Clock.createTimer(2000, new Functor() {
			@Override
			public void execute(Object o) {
				((Player) o).dropItems(((Player) o).container.getItems());
				((Member) o).remove();
			}
		}, this);
	}

	public boolean isDead() {
		return dead;
	}

	public double getHealth() {
		return health;
	}

	public void setMainPlayer(boolean state) {
		mainPlayer = state;
	}

	@Override
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

	public PlayerStats getStats() {
		PlayerStats ps = new PlayerStats(stats);
		ps.add(container.getStats());
		return ps;
	}

	public void captureOld() {
		if (old == null) {
			old = new Position(x, y);
		} else {
			old.x = x;
			old.y = y;
		}
	}

	public void move(Direction dir) {
		if (!dead) {
			captureOld();
			if (dir != direction) {
				// TODO change image
				this.direction = dir;
				switch (dir) {
				case DOWN:
					current_anim = animations.get("down");
					break;
				case UP:
					current_anim = animations.get("up");
					break;
				case LEFT:
					current_anim = animations.get("left");
					break;
				case RIGHT:
					current_anim = animations.get("right");
					break;
				}
			}

			double speed = getStats().moveSpeed;

			switch (dir) {
			case DOWN:
				this.y += speed;
				break;
			case UP:
				this.y -= speed;
				break;
			case LEFT:
				this.x -= speed;
				break;
			case RIGHT:
				this.x += speed;
				break;
			}
			level.notifyMove(this);
			level.update();
		}
	}

	public void undoMove() {
		// TODO make smarter
		this.x = old.x;
		this.y = old.y;
	}

	public BufferedImage getImage() {
		return current_anim.getNext();
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	public boolean collide(Collidable p) {
		return getBounds().intersects(p.getBounds());
	}

	public void setImage(String img) {
		// TODO Auto-generated method stub
		Images.load(img, EXT.NONE);
		this.imgID = img;
	}

	public boolean isWithin(View v) {
		return (this.x + width >= v.getX()
				&& this.x - this.width <= v.getX() + v.getWidth()
				&& this.y + height >= v.getY() && this.y - this.height <= v
				.getY() + v.getHeight());
	}

	public Position getPosition() {
		return new Position(x, y);
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void onInteract(Interactable i) {
		// TODO Auto-generated method stub
		if (!dead) {

		}

	}

	public void setStats(PlayerStats stats) {
		this.stats = stats;
	}

	public double getMaxHealth() {
		// TODO Auto-generated method stub
		return stats.health;
	}

	@Override
	public void interact(Interactable i) {
		// TODO Auto-generated method stub
		if (!dead) {
			if ((System.currentTimeMillis() - lastInteract) > interactWait * 1000) {
				lastInteract = System.currentTimeMillis();
				Console.log(name + " interacted with " + i.getName(),
						Console.in.INFO);
			}
		}
	}

	public void attack(Player p) {
		// TODO Auto-generated method stub
		if (!dead) {
			if (!p.isDead()) {
				// TODO: make this check a timer...
				if ((System.currentTimeMillis() - lastAttack) > stats.speed * 1000) {
					// TODO: make this "miss" code better
					Random r = new Random();
					int i = r.nextInt(100);
					Console.log("random: " + i + " def " + p.getStats().def,
							in.INFO);
					if (i <= p.getStats().def) {
						// missed
						Console.log("Player " + name
								+ " missed attack against " + p.name, in.INFO);
					} else {
						int atk = getStats().attk;
						if (atk < 0) {
							atk = 0;
						}
						p.addHealth(-1 * atk);
					}
					lastAttack = System.currentTimeMillis();
				}
			}
		}
	}

	public void onAttack(Player p) {
		if (!dead) {
			attack(p);
		}
	}

	public double getWaitRatio() {
		double diff = (System.currentTimeMillis() - lastAttack);
		if (diff > stats.speed * 1000) {
			return 1;
		}
		return diff / ((double) stats.speed * 1000);
	}

	public void dropItems(LinkedList<Item> keys) {
		LinkedList<Item> temp = new LinkedList<Item>(keys);
		for (Item key : temp) {
			dropItem(key);
		}
	}

	public void dropItem(Item key) {
		Random r = new Random();
		key.setX(x + r.nextInt(width));
		key.setY(y + r.nextInt(height));
		level.addMember(key);
		container.removeItem(key);
	}

	@Override
	public String getImageName() {
		// TODO Auto-generated method stub
		return imgID;
	}

	@Override
	public void step() {
		if (!dead) {

		}
	}

	@Override
	public Member clone() {
		Player p = new Player();
		copy(p);
		return p;
	}

	public void copy(Player p) {
		p.level = level;
		p.container = container;

		p.health = health;
		p.stats = stats;
		p.maxMana = maxMana;

		p.width = width;
		p.height = height;
		p.bounds = bounds;

		p.imgID = imgID;
	}

	public void setMaxMana(int m) {
		maxMana = m;
	}

	public int getMaxMana() {
		return maxMana;
	}

	@Override
	public void onClick(int x, int y, button b) {
		// TODO Auto-generated method stub

	}
}
