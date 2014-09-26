package elements;

import java.awt.Image;

import utilities.Images;


public class Decoration extends Member implements Renderable {
	String imgID;
	
	public Decoration(double x, double y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public Decoration() {
		// TODO Auto-generated constructor stub
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
		Decoration p = new Decoration();
		p.bounds = bounds;
		p.type = type;
		p.width = width;
		p.height = height;
		p.imgID = imgID;
		p.name = name;
		return p;
	}
}
