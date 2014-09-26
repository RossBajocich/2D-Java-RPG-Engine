package utilities;

import java.util.LinkedList;

public class Clock {

	private static LinkedList<Timer> timers = new LinkedList<Timer>();
	
	public Clock(){
		
	}
	
	public static void update(){
		LinkedList<Timer> del =new LinkedList<Timer>();
		for(Timer t : timers){
			if(t.isDone()){
				del.add(t);
			}else{
				t.update();
			}
		}
		timers.removeAll(del);
	}
	
	public static Timer createTimer(long ms){
		Timer t = new Timer(ms);
		timers.add(t);
		return t;
	}
	
	public static Timer createTimer(long ms, Functor f, Object o){
		Timer t = new Timer(ms,f,o);
		timers.add(t);
		return t;
	}
}
