package game;

import items.Item;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.TreeMap;

import utilities.Console;
import utilities.Console.in;
import utilities.Images;
import characters.Player;

import components.GraphicsComponent;
import components.PhysicsComponent;

import elements.Decoration;
import elements.Member;
import elements.Prop;

public class Level {
	int width, height;
	String bg;

	EnumMap<RenderLayer, List<Member>> renders = new EnumMap<RenderLayer, List<Member>>(
			RenderLayer.class);

	List<Member> elements = new ArrayList<Member>();
	// List<PhysicsComponent> moved = new ArrayList<PhysicsComponent>();
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
		int left = p.getBoundsRect(x_move, 0).x;
		int top = p.getBoundsRect(0, y_move).y;
		int right = left + p.getBoundsRect(x_move, 0).width;
		int bottom = top + p.getBoundsRect(0, y_move).height;

		if (left < 0 || top < 0 || right > width || bottom > height) {
			Console.log("running into the wall! x= " + left + " y= " + top
					+ " right = " + right + " bottom= " + bottom
					+ " lvlwidth= " + width + " lvlheight= " + height, in.INFO);
			return false;
		}
		for (Member m : elements) {
			if (((PhysicsComponent) m.get(PhysicsComponent.class)).getBounds() != null
					&& m.get(PhysicsComponent.class) != p) {
				if (p.getBounds().intersects(
						(PhysicsComponent) m.get(PhysicsComponent.class),
						(int) x_move, (int) y_move)) {
					Rectangle r1 = p.getBoundsRect(x_move, y_move);
					Rectangle r2 = ((PhysicsComponent) m
							.get(PhysicsComponent.class)).getBoundsRect();
					System.out.printf(
							"Collision player, x1:%d, y1:%d, w1:%d, h1:%d\n",
							r1.x, r1.y, r1.width + r1.x, r1.y + r1.height);
					System.out.printf(
							"Collision member, x2:%d, y2:%d, w2:%d, h2:%d\n",
							r2.x, r2.y, r2.width + r2.x, r2.y + r2.height);
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

	public BufferedImage getBackground() {
		return Images.get(bg);
	}

	public List<Member> getElements() {
		return elements;
	}

	public void addMember(Member e) {
		if (!elements.contains(e)) {
			elements.add(e);
			if (e.get(GraphicsComponent.class) != null) {
				// TODO: remove this, very useless
				if (e instanceof Decoration) {
					addRender(RenderLayer.DECORATION, e);
				} else if (e instanceof Item) {
					addRender(RenderLayer.ITEM, e);
				} else {
					addRender(RenderLayer.MAIN, e);
				}
			} else {
				Console.log("Not a valid object @ level -> addMember()",
						in.ERROR);
			}
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

	private List<Member> getSorted(List<Member> old) {
		return sort(old);
	}

	private List<Member> sort(List<Member> in) {
		TreeMap<Integer, Member> vals = new TreeMap<Integer, Member>();

		for (Member r : in) {
			PhysicsComponent p = r.get(PhysicsComponent.class);
			if (!vals.containsKey((int) (p.getY() + ((GraphicsComponent) r
					.get(GraphicsComponent.class)).getHeight()))) {
				vals.put((int) (p.getY() + ((GraphicsComponent) r
						.get(GraphicsComponent.class)).getHeight()), r);
			} else {
				vals.put(
						(int) (p.getY()
								+ ((GraphicsComponent) r
										.get(GraphicsComponent.class))
										.getHeight() + 1), r);
			}
		}
		return new ArrayList<Member>(vals.values());
	}

	public void setBackground(String data) {
		if (data.contains(".")) {
			Images.load(data, Images.EXT.NONE);
		} else {
			Images.load(data, Images.EXT.JPG);
		}

		bg = data;

		if (Images.get(bg).getWidth() != width
				|| Images.get(bg).getHeight() != height) {
			BufferedImage original = Images.get(bg);

			BufferedImage f = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			f.getGraphics().drawImage(original, 0, 0, width, height, null);
			Images.set(f, bg);
		}
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

		if (renders.containsKey(RenderLayer.MAIN)) {
			List<Member> old = new ArrayList<Member>(
					renders.get(RenderLayer.MAIN));
			renders.put(RenderLayer.MAIN, getSorted(old));
		}

	}

	public boolean attackRequest(Player sender) {

		return false;
	}

	public void remove(Member e) {
		// TODO: synchonize dis function
		elements.remove(e);
		removeRender(e);
	}

	public void setMainPlayer(Player p) {
		mainPlayer = p;
		Console.log("main player for this level is " + mainPlayer, in.INFO);
	}
}
