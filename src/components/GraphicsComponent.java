package components;

import graphics.Animation;
import graphics.ReferenceFrame;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import utilities.Console;
import utilities.Console.in;

public class GraphicsComponent extends Component {

	protected HashMap<String, Animation> animations = new HashMap<String, Animation>();
	protected String current_anim = "default";
	protected int width, height;

	public GraphicsComponent() {
		super();
		setCurrentAnimation("default");
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

	public Animation getCurrentAnimation() {
		return animations.get(current_anim);
	}

	public int getHeight() {
		return height;
	}

	public BufferedImage getImage() {
		return animations.get(current_anim).getImage();
	}

	public int getWidth() {
		return width;
	}

	public boolean isOutside(int xx, int yy, int wwidth, int hheight) {
		PhysicsComponent p = modify.get(PhysicsComponent.class);
		return p.getX() + width < xx || p.getY() + height < yy
				|| p.getX() > wwidth || p.getY() > hheight;
	}

	public boolean isWithin(ReferenceFrame v) {
		PhysicsComponent p = modify.get(PhysicsComponent.class);

		if (p == null) {
			return false;
		}
		return (p.getX() + width >= v.getX()
				&& p.getX() - width <= v.getX() + v.getWidth()
				&& p.getY() + height >= v.getY() && p.getY() - height <= v
				.getY() + v.getHeight());
	}

	public void setCurrentAnimation(String uuid) {
		if (animations.get(uuid) != null || current_anim == null) {
			current_anim = uuid;
		}
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void update() {

	}

}
