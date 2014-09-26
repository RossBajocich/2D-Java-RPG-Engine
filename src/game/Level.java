package game;

import items.Container;
import items.Item;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import utilities.Console;
import utilities.Console.in;
import utilities.Images;
import utilities.Images.EXT;
import characters.NPC;
import characters.Player;
import elements.Collidable;
import elements.Decoration;
import elements.Interactable;
import elements.Member;
import elements.Prop;
import elements.Renderable;

public class Level {
	int width, height;
	LinkedList<Member> moved = new LinkedList<Member>();

	public static final int DECORATION = 0;
	public static final int ITEM = 1;
	public static final int MAIN = 2;

	String bg;

	TreeMap<Integer, LinkedList<Renderable>> renders = new TreeMap<Integer, LinkedList<Renderable>>();
	LinkedList<Player> players = new LinkedList<Player>();
	LinkedList<Member> elements = new LinkedList<Member>();
	private Player mainPlayer;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Level() {

	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isWithin(View v) {
		return (this.width >= v.getX() && this.width <= v.getX() + v.getWidth()
				&& this.height >= v.getY() && this.height <= v.getY()
				+ v.getHeight());
	}

	/*
	 * private void createBackground() { BufferedImage newBg = new
	 * BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); Graphics g =
	 * newBg.createGraphics(); for (int x = 0; x < width; x++) { for (int y = 0;
	 * y < height; y++) { g.drawImage(bg, x * bg.getWidth(), y * bg.getHeight(),
	 * null); } } g.dispose(); }
	 */

	public BufferedImage getBackground() {
		if (bg == null) {
			// createBackground();
			throw new NullPointerException("Background image is null!");
		}
		return Images.get(bg);
	}

	public LinkedList<Member> getElements() {
		return elements;
	}

	public void addMember(Member e) {
		if (!elements.contains(e)) {
			elements.add(e);
		}
		Renderable a = (Renderable) e;

		if (e instanceof Decoration) {
			addRender(DECORATION, a);
		} else if (e instanceof Item) {
			addRender(ITEM, a);
		} else if (e instanceof Prop || e instanceof Player) {
			addRender(MAIN, a);
			if (e instanceof Player) {
				if (!players.contains((Player) e)) {
					Console.log("player " + e.getName(), in.INFO);
					players.add((Player) e);
				}
			}
		} else if (e instanceof Decoration) {
			addRender(DECORATION, a);
		}else if (e instanceof Container){
			addRender(DECORATION, a);
		} else {
			Console.log("Not a valid object @ level -> addMember()", in.ERROR);
		}
	}

	private void addRender(int i, Renderable r) {
		LinkedList<Renderable> currentValue = renders.get(i);
		if (currentValue == null) {
			currentValue = new LinkedList<Renderable>();
			renders.put(i, currentValue);
		}
		currentValue.add(r);
	}

	private void removeRender(Renderable e) {
		Renderable a = (Renderable) e;

		if (e instanceof Decoration) {
			remove(DECORATION, a);
		} else if (e instanceof Item) {
			remove(ITEM, a);
		} else if (e instanceof Player || e instanceof Prop) {
			remove(MAIN, a);
		} else {
			remove(DECORATION, a);
		}
	}

	private void remove(int i, Renderable e) {
		renders.get(i).remove(e);
	}

	public TreeMap<Integer, LinkedList<Renderable>> getRenders() {
		return renders;
	}

	private boolean compare(Renderable r1, Renderable r2) {
		return r1.getY() + r1.getHeight() < r2.getY() + r2.getHeight() ? true
				: false;
	}

	public LinkedList<Renderable> getSorted(LinkedList<Renderable> in) {
		return sort(in);
	}

	private LinkedList<Renderable> sort(LinkedList<Renderable> in) {
		TreeMap<Integer, Renderable> vals = new TreeMap<Integer, Renderable>();

		for (Renderable r : in) {
			if (!vals.containsKey((int) (r.getY() + r.getHeight()))) {
				vals.put((int) (r.getY() + r.getHeight()), r);
			} else {
				vals.put((int) (r.getY() + r.getHeight() + 1), r);
			}
		}
		return new LinkedList<Renderable>(vals.values());
	}

	public List<Renderable> getVisible(View v) {
		// NOT USED WTF YOURE STUPID
		LinkedList<Renderable> out = new LinkedList<Renderable>();
		for (List<Renderable> r : renders.values()) {
			for (Renderable real : r) {
				if (real.isWithin(v)) {
					out.add(real);
				}
			}
		}
		Console.log("getvisible?", in.INFO);
		return getSorted(out);
	}

	public void setBackground(String data) {
		if (data.contains(".")) {
			Images.load(data, EXT.NONE);
		} else {
			Images.load(data, EXT.JPG);
		}
		bg = data;
	}

	public Player getMainPlayer() {
		/*
		 * for (Player p : players) { if (((Player) p).isMainPlayer()) { return
		 * (Player) p; } }
		 */
		if (mainPlayer != null) {
			return mainPlayer;
		} else {
			Console.log(("No MainPlayer in this level!"), Console.in.ERROR);
			return null;
		}
	}

	public void update() {
		for (Player p : players) {
			if (p instanceof NPC) {
				((NPC) p).step();
			}
		}
		for (Member e : moved) {
			if (e instanceof Player) {
				if(e.isOutside(0, 0, width, height)){
					((Player) e).undoMove();
				}
				for (Member c : elements) {
					if (c instanceof Collidable) {
						if (e == c) {
							continue;
						}
						if (((Collidable) e).collide((Collidable) c)) {
							((Player) e).undoMove();
							//Console.log("Player " + e.getName() + " collided with " + c.getName(), in.INFO);
						}
					}
				}
			}
		}
		if (!moved.isEmpty()) {
			if (renders.containsKey(MAIN)) {
				LinkedList<Renderable> old = new LinkedList<Renderable>(
						renders.get(MAIN));
				renders.put(MAIN, getSorted(old));
			}
		}
		moved.clear();
	}

	public boolean interact(Player sender) {
		// returns false if nothing is found
		Interactable found = null;
		double foundDist = 0;
		for (Member i : elements) {
			if (i instanceof Interactable) {
				if (sender == i) {
					continue;
				}
				double dist = sender.getDistance(i);
				if (dist < Player.interactdistance) {
					if (found == null) {
						found = (Interactable) i;
						foundDist = dist;
						continue;
					}
					if (dist < foundDist) {
						found = (Interactable) i;
					}
				}
			}
		}
		if (found != null) {
			sender.interact(found);
			found.onInteract(sender);
			return true;
		}
		return true;
	}

	public boolean attack(Player sender) {
		for (Player p : players) {
			if (sender == p) {
				continue;
			}
			if (sender.getDistance(p) < Player.attackDistance) {
				sender.attack(p);
				p.onAttack(sender);
				return true;
			}
		}
		return false;
	}

	public void notifyMove(Member e) {
		if (!moved.contains(e)) {
			moved.add(e);
		}
	}

	public void remove(Member e) {
		//TODO: synchonize dis function
		elements.remove(e);
		removeRender((Renderable) e);
	}

	public LinkedList<Player> getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}

	public void setMainPlayer(Player p) {
		mainPlayer = p;
		Console.log("main player for this level is " + mainPlayer, in.INFO);
	}
}
