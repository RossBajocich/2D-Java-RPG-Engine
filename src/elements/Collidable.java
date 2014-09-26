package elements;

import java.awt.geom.Rectangle2D;

public interface Collidable {
	public Rectangle2D getBounds();

	public boolean collide(Collidable p);
}
