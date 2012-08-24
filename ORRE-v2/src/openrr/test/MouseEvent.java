package openrr.test;

public class MouseEvent {
	
	public static int PRESS = 0;
	public static int RELEASE = 1;
	public static int MOVE = 2;
	
	int x;
	int y;
	
	int type;
	
	boolean handled = false;

	public MouseEvent(int inX, int inY, int inType) {
		x = inX;
		y = inY;
		type = inType;
	}
	
	public void handled() {
		handled = true;
	}
	
	public int getType() {
		return type;
	}
	
	public int[] getPosData() {
		return new int[] {x, y};
	}
	
}
