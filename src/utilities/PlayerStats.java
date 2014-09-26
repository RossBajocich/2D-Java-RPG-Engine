package utilities;

public class PlayerStats extends Stats {
	public int attk, def, speed, health, mana;
	public double moveSpeed;
	public String type;
	
	public PlayerStats(){
		
	}
	
	public PlayerStats(PlayerStats s){
		attk = s.attk;
		def = s.def;
		speed = s.speed;
		health = s.health;
		moveSpeed = s.moveSpeed;
		mana = s.mana;
	}
	
	public void add(ItemStats s) {
		attk += s.attk;
		def += s.def;
		speed += s.speed;
		health += s.health;
		moveSpeed += s.moveSpeed;
		mana += s.mana;
	}
}
