package elements;

import game.View;

import java.awt.Image;

public interface Renderable{
	public boolean isWithin(View v);

	public double getX();

	public double getY();

	public int getWidth();

	public int getHeight();

	public void setImage(String id);

	public Image getImage();

	public String getImageName();
}
