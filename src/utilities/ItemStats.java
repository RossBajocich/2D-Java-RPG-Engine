package utilities;

public class ItemStats extends Stats {
	public int attk, def, speed, health, size, mana;
	public double moveSpeed;
	
	public void add(ItemStats s){
		attk += s.attk;
		def += s.def;
		speed += s.speed;
		health += s.health;
		moveSpeed += s.moveSpeed;
		size += s.size;
		mana += mana;
	}
}
