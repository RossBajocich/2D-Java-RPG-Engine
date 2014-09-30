package utilities;

public class Stats {
	public int attack, def, speed, health, mana, size;
	public double moveSpeed;
	public String type;
	
	public Stats(){
		
	}
	
	public Stats(Stats s){
		attack = s.attack;
		def = s.def;
		speed = s.speed;
		health = s.health;
		moveSpeed = s.moveSpeed;
		mana = s.mana;
		size = s.size;
	}
	
	public void add(Stats s) {
		attack += s.attack;
		def += s.def;
		speed += s.speed;
		health += s.health;
		moveSpeed += s.moveSpeed;
		mana += s.mana;
		size += s.size;
	}
}
