package utilities;


public class Timer {
	private long start, end, delay;
	private Functor f;
	private Object o;
	private boolean done = false;

	public Timer(long ms) {
		set(ms);
	}

	public Timer(long ms, Functor f, Object o) {
		set(ms);
		this.f = f;
		this.o = o;
	}

	private void set(long ms) {
		delay = ms;
		start = System.currentTimeMillis();
		end = start + delay;
	}

	public void update() {
		if (f != null && o != null) {
			if (System.currentTimeMillis() > end) {
				f.execute(o);
				f = null;
				o = null;
				done  = true;
			}
		}
	}

	public boolean check() {
		return System.nanoTime() > end;
	}

	public boolean isDone() {
		return done;
	}

}
