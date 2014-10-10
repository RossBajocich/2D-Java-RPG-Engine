package components;

import elements.Member;

public abstract class Component {
	Member modify;

	public Component() {

	}

	public void setModify(Member member) {
		this.modify = member;
	}

	public abstract void update();
}
