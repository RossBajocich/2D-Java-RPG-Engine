package utilities;

public class Timer {
	private long start, end, delay;
	private Functor f;
	private boolean done = false;

	public Timer(long ms) {
		set(ms);
	}

	public Timer(long ms, Functor f) {
		set(ms);
		this.f = f;
	}

	public boolean check() {
		return System.nanoTime() > end;
	}

	public boolean isDone() {
		return done;
	}

	public void update() {
		if (f != null) {
			if (System.currentTimeMillis() > end) {
				f.execute();
				f = null;
				done = true;
			}
		}
	}

	private void set(long ms) {
		delay = ms;
		start = System.currentTimeMillis();
		end = start + delay;
	}

}
