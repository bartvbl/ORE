package openrr.test;

public class GUIElement {
	
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected Frame parent;
	protected EventDispatcher eventDispatcher;
	protected boolean moving = false;
	protected double[] moveData;
	
	public GUIElement(int[] posData, EventDispatcher inEventDispatcher, Frame parent) {
		setPosData(posData);
		setParent(parent);
		eventDispatcher = inEventDispatcher;
	}
	
	public void setPosData(int[] posData) {
		x = posData[0];
		y = posData[1];
		w = posData[2];
		h = posData[3];
	}
	
	public int[] getPosData() {
		return new int[] {x, y, w, h};
	}
	
	public boolean inCoords(int[] coords) {
		if (coords[0]>=x && coords[0]<=x+w) {
			if (coords[1]>=y && coords[1]<=y+h) {
				return true;
			}
		}
		return false;
	}
	
	public void beginMove(int amount, double increments) {
		moveData = new double[] {amount, amount/increments, 0, x};
		getEventDispatcher().dispatchEvent(new Event(EventType.GUIELEMENT_MOVING, this));
	}
	
	public void move() {
		moveData[2] += moveData[1];
		x += Math.round(moveData[1]);
		if (moveData[2]==moveData[0]) {
			x = (int)(moveData[3]+moveData[0]);
			moveData = null;
			moving = false;
			getEventDispatcher().dispatchEvent(new Event(EventType.GUIELEMENT_MOVED, this));
		}
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}
	
	public void setParent(Frame newParent) {
		parent = newParent;
	}
	
	public Frame getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return !(parent==null);
	}
}
