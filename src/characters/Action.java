package characters;

import elements.Member;
import graphics.Animation;
import utilities.Functor;

public class Action {

	String name;
	Animation anim;
	Functor function;

	public Action(String name) {
		this.name = name;
	}

	public void setAnim(Animation a) {
		anim = a;
	}

	public void setFunction(Functor f) {
		function = f;
	}
	
	public void call(Member caller){
		function.execute(caller);
	}
}
