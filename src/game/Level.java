package game;

import items.Item;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.TreeMap;

import utilities.Console;
import utilities.Console.in;
import utilities.Images;
import characters.Player;

import components.PhysicsComponent;

import elements.Decoration;
import elements.Member;
import elements.Prop;

public class Level {
	int width, height;
	String bg;

	EnumMap<RenderLayer, List<Member>> renders = new EnumMap<RenderLayer, List<Member>>(
			RenderLayer.class);

	List<Player> players = new ArrayList<Player>();
	List<Member> elements = new ArrayList<Member>();
	List<PhysicsComponent> moved = new ArrayList<PhysicsComponent>();
	private Player mainPlayer;

	public enum RenderLayer {
		DECORATION(0), ITEM(1), MAIN(2);
		RenderLayer(int x) {

		}
	}

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Level() {

	}

	/*
	 * Runs through all objects, and checks if PhysicsComponent p collides with
	 * any that have a PhysicsComponent
	 */
	public boolean tryMove(PhysicsComponent p, double x_move, double y_move) {
		if (p.getX() + x_move < 0 || p.getX() + p.getBounds().width >= width
				|| p.getY() + y_move < 0 || p.getY() + y_move > height) {
			return false;
		}
		for (Member m : elements) {
			if (m.getPhysics().getBounds() != null) {
				if (m.getPhysics().getBounds(x_move, y_move)
						.intersects(p.getBounds())) {
					return false;
				}
			}
		}
		return true;
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

	public BufferedImage getBackground() {
		if (bg == null) {
			throw new NullPointerException("Background image is null!");
		}
		return Images.get(bg);
	}

	public List<Member> getElements() {
		return elements;
	}

	public void addMember(Member e) {
		if (!elements.contains(e)) {
			elements.add(e);
		}

		if (e.getGraphics() != null) {
			// TODO: remove this, very useless
			if (e instanceof Decoration) {
				addRender(RenderLayer.DECORATION, e);
			} else if (e instanceof Item) {
				addRender(RenderLayer.ITEM, e);
			} else {
				addRender(RenderLayer.MAIN, e);
			}
		} else {
			Console.log("Not a valid object @ level -> addMember()", in.ERROR);
		}
		if (e instanceof Player) {
			players.add((Player) e);
		}
	}

	private void addRender(RenderLayer rl, Member r) {
		List<Member> currentValue = renders.get(rl);
		if (currentValue == null) {
			currentValue = new ArrayList<Member>();
			renders.put(rl, currentValue);
		}
		currentValue.add(r);
	}

	private void removeRender(Member e) {
		if (e instanceof Decoration) {
			remove(RenderLayer.DECORATION, e);
		} else if (e instanceof Item) {
			remove(RenderLayer.ITEM, e);
		} else if (e instanceof Player || e instanceof Prop) {
			remove(RenderLayer.MAIN, e);
		} else {
			remove(RenderLayer.DECORATION, e);
		}
	}

	private void remove(RenderLayer i, Member e) {
		renders.get(i).remove(e);
	}

	public EnumMap<RenderLayer, List<Member>> getRenders() {
		return renders;
	}

	public List<Member> getSorted(List<Member> old) {
		return sort(old);
	}

	private List<Member> sort(List<Member> in) {
		TreeMap<Integer, Member> vals = new TreeMap<Integer, Member>();

		for (Member r : in) {
			PhysicsComponent p = r.getPhysics();
			if (!vals
					.containsKey((int) (p.getY() + r.getGraphics().getHeight()))) {
				vals.put((int) (p.getY() + r.getGraphics().getHeight()), r);
			} else {
				vals.put((int) (p.getY() + r.getGraphics().getHeight() + 1), r);
			}
		}
		return new ArrayList<Member>(vals.values());
	}

	public List<Member> getVisible(View v) {
		// NOT USED WTF YOURE STUPID
		List<Member> out = new ArrayList<Member>();
		for (List<Member> r : renders.values()) {
			for (Member real : r) {
				if (real.getGraphics().isWithin(v)) {
					out.add(real);
				}
			}
		}
		Console.log("getvisible?", in.INFO);
		return getSorted(out);
	}

	public void setBackground(String data) {
		if (data.contains(".")) {
			Images.load(data, Images.EXT.NONE);
		} else {
			Images.load(data, Images.EXT.JPG);
		}
		bg = data;
	}

	public Player getMainPlayer() {
		if (mainPlayer != null) {
			return mainPlayer;
		} else {
			Console.log(("No MainPlayer in this level!"), Console.in.ERROR);
			return null;
		}
	}

	public void update() {
		for (Member p : elements) {
			p.update();
		}

		if (!moved.isEmpty()) {
			if (renders.containsKey(RenderLayer.MAIN)) {
				List<Member> old = new ArrayList<Member>(
						renders.get(RenderLayer.MAIN));
				renders.put(RenderLayer.MAIN, getSorted(old));
			}
		}
		moved.clear();
	}

	public boolean attack(Player sender) {
		for (Player p : players) {
			if (sender == p) {
				continue;
			}
			if (sender.getPhysics().getDistance(p) < sender.getAttack()
					.getAttackDistance()) {
				// Should i call onAttack() here? Or inside attack()?
				sender.getAttack().attack(p);
				return true;
			}
		}
		return false;
	}

	public void notifyMove(PhysicsComponent physicsComponent) {
		if (!moved.contains(physicsComponent)) {
			moved.add(physicsComponent);
		}
	}

	public void remove(Member e) {
		// TODO: synchonize dis function
		elements.remove(e);
		removeRender(e);
	}

	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}

	public void setMainPlayer(Player p) {
		mainPlayer = p;
		Console.log("main player for this level is " + mainPlayer, in.INFO);
	}
}
