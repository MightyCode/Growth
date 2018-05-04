package migthycode.growth.Game.utils;

public class Animation {
	
	private int begin, end, range;
	private int actual;
	private int delay;
	private int count1;
	
	public Animation(int begin, int end) {
		this.begin = begin;
		this.end = end;
		this.range = begin + 1 - end;
	}
	
	public Animation(int begin, int end, int delay) {
		this.begin = begin;
		this.end = end;
		this.range = begin + 1 - end;
		this.delay = delay;
		actual = begin;
	}
	
	public void update() {
		count1++;
		if(count1 > delay) { 
			count1 = 0;
			actual++;
			if(actual >= end) {
				actual = 0;
			}
		}
	}
	
	public int getCurrentId() { return begin + actual; }
}
















