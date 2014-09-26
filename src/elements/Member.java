package elements;

import game.Level;
import game.View;
import graphics.Animation;

import java.awt.Rectangle;
import java.util.HashMap;

import loaders.Bounds;
import utilities.Keyboard;

public abstract class Member {

	protected double x, y;
	protected int width, height;
	protected String name;
	protected HashMap<String, Double> vars = new HashMap<String, Double>();
	protected Level level;
	protected Bounds bounds;
	protected String type;
	protected HashMap<String, Animation> animations = new HashMap<String, Animation>();

	public Member(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Member() {
		x = 0;
		y = 0;
		width = 10;
		height = 10;
		bounds = new Bounds((int) x, (int) y, width, height);
	}

	public void Listen(Keyboard k) {
		// listen to a specific key and do something when it is pressed released
		// or whatever you sign up for
	}

	public abstract Member clone();

	public void step(){
		
	}

	public void setBounds(Bounds r) {
		bounds = r;
	}

	public Rectangle getBounds() {
		return bounds.getBounds((int)x,(int)y);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getDistance(Member e) {
		if (e != null) {
			return Math.sqrt(Math.pow(x - e.getX(), 2)
					+ Math.pow(y - e.getY(), 2));
		}
		return 0;
	}

	public boolean isOutside(int xx, int yy, int wwidth, int hheight){
		return x + width < xx || y + height < yy || x > wwidth || y > hheight;
	}
	
	public boolean isWithin(View v) {
		return (this.x + width >= v.getX()
				&& this.x - this.width <= v.getX() + v.getWidth()
				&& this.y + height >= v.getY() && this.y - this.height <= v
				.getY() + v.getHeight());
	}

	public void remove() {
		this.name = null;
		this.vars = null;
		level.remove(this);
	}

	public void setLevel(Level l) {
		this.level = l;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
	}

	public void setType(String string) {
		// TODO Auto-generated method stub
		type = string;
	}

	public String getType() {
		return type;
	}
}
