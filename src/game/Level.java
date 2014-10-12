package game;

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

import elements.Member;

public class Level {
	public enum RenderLayer {
		DECORATION(0), MAIN(1);
		RenderLayer(int x) {
		}
	}

	private int width, height;
	private String bg;
	private EnumMap<RenderLayer, List<Member>> renders = new EnumMap<RenderLayer, List<Member>>(
			RenderLayer.class);
	private List<Member> elements = new ArrayList<Member>();
	private List<Member> toRemove = new ArrayList<Member>();
	private Player mainPlayer;

	public Level() {

	}

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void addMember(Member e) {
		if (!elements.contains(e)) {
			elements.add(e);
			if (e.get(GraphicsComponent.class) != null) {
				// This corresponds to removeRender(...), both must be changed
				// the same
				switch (e.getType().toLowerCase()) {
				case "decoration":
					addRender(RenderLayer.DECORATION, e);
					break;
				case "item":
					addRender(RenderLayer.DECORATION, e);
					break;
				case "zone":
					addRender(RenderLayer.DECORATION, e);
					break;
				case "container":
					addRender(RenderLayer.DECORATION, e);
					break;
				case "player":
					addRender(RenderLayer.MAIN, e);
					break;
				case "prop":
					addRender(RenderLayer.MAIN, e);
					break;
				default:
					addRender(RenderLayer.MAIN, e);
					break;
				}
			} else {
				Console.log("Not a valid object @ level -> addMember()",
						in.ERROR);
			}
		}
	}

	public BufferedImage getBackground() {
		return Images.get(bg);
	}

	public List<Member> getElements() {
		return elements;
	}

	public int getHeight() {
		return height;
	}

	public Player getMainPlayer() {
		if (mainPlayer != null) {
			return mainPlayer;
		} else {
			Console.log(("No MainPlayer in this level!"), Console.in.ERROR);
			return null;
		}
	}

	public EnumMap<RenderLayer, List<Member>> getRenders() {
		return renders;
	}

	public int getWidth() {
		return width;
	}

	public void removeMember(Member e) {
		if (!toRemove.contains(e)) {
			toRemove.add(e);
		}
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

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMainPlayer(Player p) {
		mainPlayer = p;
		Console.log("main player for this level is " + mainPlayer, in.INFO);
	}

	public void setWidth(int width) {
		this.width = width;
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
			if (left < 0) {
				p.setX(0);
			}
			if (top < 0) {
				p.setY(0 - 5);
			}
			if (right > width) {
				p.setX((width - 225) - p.getBounds().getRect().width);
			}
			if (bottom > height) {
				p.setY((height - 235) - p.getBounds().getRect().height);
			}
			return false;
		}
		for (Member m : elements) {
			if ((m.get(PhysicsComponent.class)).getBounds() != null
					&& m.get(PhysicsComponent.class) != p) {
				if (p.getBounds().intersects(m.get(PhysicsComponent.class),
						(int) x_move, (int) y_move)) {
					// Rectangle r1 = p.getBoundsRect(x_move, y_move);
					// Rectangle r2 = ( m
					// .get(PhysicsComponent.class)).getBoundsRect();
					return false;
				}
			}
		}
		return true;
	}

	public void update() {
		for (Member p : toRemove) {
			removeRender(p);
			elements.remove(p);
			Console.log("removed " + p.getName(), in.INFO);
		}
		toRemove.clear();

		for (Member p : elements) {
			p.update();
		}

		if (renders.containsKey(RenderLayer.MAIN)) {
			List<Member> old = new ArrayList<Member>(
					renders.get(RenderLayer.MAIN));
			renders.put(RenderLayer.MAIN, getSorted(old));
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

	private List<Member> getSorted(List<Member> old) {
		return sort(old);
	}

	private void remove(RenderLayer i, Member e) {
		renders.get(i).remove(e);
	}

	private void removeRender(Member e) {
		switch (e.getType().toLowerCase()) {
		case "decoration":
			remove(RenderLayer.DECORATION, e);
			break;
		case "item":
			remove(RenderLayer.DECORATION, e);
			break;
		case "zone":
			remove(RenderLayer.DECORATION, e);
			break;
		case "container":
			remove(RenderLayer.DECORATION, e);
			break;
		case "player":
			remove(RenderLayer.MAIN, e);
			break;
		case "prop":
			remove(RenderLayer.MAIN, e);
			break;
		default:
			remove(RenderLayer.MAIN, e);
			break;
		}
	}

	private List<Member> sort(List<Member> in) {
		TreeMap<Integer, Member> vals = new TreeMap<Integer, Member>();

		for (Member r : in) {
			PhysicsComponent p = r.get(PhysicsComponent.class);
			if (!vals.containsKey((int) (p.getY() + (r
					.get(GraphicsComponent.class)).getHeight()))) {
				vals.put((int) (p.getY() + (r.get(GraphicsComponent.class))
						.getHeight()), r);
			} else {
				vals.put(
						(int) (p.getY()
								+ (r.get(GraphicsComponent.class)).getHeight() + 1),
						r);
			}
		}
		return new ArrayList<Member>(vals.values());
	}
}
