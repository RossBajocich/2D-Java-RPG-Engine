package components;

import game.View;
import graphics.Animation;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import utilities.Console;
import utilities.Console.in;

public class GraphicsComponent extends Component {

	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private String current_anim = "default";
	int width, height;

	public GraphicsComponent() {
		super();
		setCurrentAnimation("defualt");
	}

	public void setCurrentAnimation(String uuid) {
		if (animations.get(uuid) != null || current_anim == null) {
			current_anim = uuid;
		}
	}
	
	public boolean isWithin(View v) {
		PhysicsComponent p = modify.getPhysics();
		
		if (p == null) {
			return false;
		}
		return (p.getX() + width >= v.getX()
				&& p.getX() - width <= v.getX() + v.getWidth()
				&& p.getY() + height >= v.getY() && p.getY() - height <= v
				.getY() + v.getHeight());
	}

	public boolean isOutside(int xx, int yy, int wwidth, int hheight) {
		PhysicsComponent p = modify.getPhysics();
		return p.getX() + width < xx || p.getY() + height < yy
				|| p.getX() > wwidth || p.getY() > hheight;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Animation getCurrentAnimation() {
		return animations.get(current_anim);
	}

	public void addAnimation(String uuid, Animation a) {
		animations.put(uuid, a);
	}

	public Animation getAnimation(String uuid) {
		if (animations.get(uuid) == null) {
			Console.log("The requested Animation " + uuid
					+ " is null in GraphicsComponent", in.INFO);
		}
		return animations.get(uuid);
	}

	@Override
	public void update() {
		
	}

	public BufferedImage getImage() {
		// TODO: make the index better, possible bug when two GraphicsComponents
		// are using the same animation in the future, would modify eachother's
		// index (currently its 1:1 however.)
		return animations.get(current_anim).getNext();
	}

}
