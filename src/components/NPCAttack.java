package components;

import characters.Player;

public class NPCAttack extends AttackComponent {

	public NPCAttack() {
		super();
	}

	public void onAttack(Player p) {
		if (!isDead()) {
			(modify.get(NPCInput.class)).setChasing(p);
		}
	}
}
