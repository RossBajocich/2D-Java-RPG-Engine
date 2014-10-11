package utilities;

import java.util.ArrayList;
import java.util.List;

public class Clock {

	private static List<Timer> timers = new ArrayList<Timer>();

	public Clock() {

	}

	public static Timer createTimer(long ms) {
		Timer t = new Timer(ms);
		timers.add(t);
		return t;
	}

	public static Timer createTimer(long ms, Functor f) {
		Timer t = new Timer(ms, f);
		timers.add(t);
		return t;
	}

	public static void update() {
		List<Timer> del = new ArrayList<Timer>();
		for (Timer t : timers) {
			if (t.isDone()) {
				del.add(t);
			} else {
				t.update();
			}
		}
		timers.removeAll(del);
	}
}
