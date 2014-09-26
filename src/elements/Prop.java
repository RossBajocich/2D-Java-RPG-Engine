package elements;

import java.awt.Image;

import utilities.Images;

public class Prop extends Member implements Collidable, Interactable, Renderable, Clickable {

	private String imgID;

	public Prop(double x, double y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public Prop() {
		// TODO Auto-generated constructor stub
	}

	public boolean collide(Collidable p) {
		return getBounds().intersects(p.getBounds());
	}

	@Override
	public void interact(Interactable i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInteract(Interactable i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setImage(String id) {
		// TODO Auto-generated method stub
		Images.add(id);
		imgID = id;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return Images.get(imgID);
	}

	@Override
	public String getImageName() {
		// TODO Auto-generated method stub
		return imgID;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Member clone() {
		Prop p = new Prop();
		p.bounds = bounds;
		p.type = type;
		p.width = width;
		p.height = height;
		p.imgID = imgID;
		p.name = name;
		return p;
	}

	@Override
	public void onClick(int x, int y, button b) {
		
	}
}
