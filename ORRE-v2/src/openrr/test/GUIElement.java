package openrr.test;

public class GUIElement {
	
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected Frame parent;
	
	public GUIElement(int[] posData, Frame parent) {
		setPosData(posData);
		setParent(parent);
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
