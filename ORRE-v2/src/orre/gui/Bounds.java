package orre.gui;

public class Bounds {
	private final double percentageLocationX;
	private final double pixelOffsetX;
	private final double percentageLocationY;
	private final double pixelOffsetY;
	private final double widthPercent;
	private final double widthPixelOffset;
	private final double heightPercent;
	private final double heightPixelOffset;

	public Bounds(
			double percentageLocationX, 
			double pixelOffsetX, 
			
			double percentageLocationY, 
			double pixelOffsetY, 
			
			double widthPercent, 
			double widthPixelOffset, 
			
			double heightPercent, 
			double heightPixelOffset
		) {
		this.percentageLocationX = percentageLocationX;
		this.pixelOffsetX = pixelOffsetX;
		
		this.percentageLocationY = percentageLocationY;
		this.pixelOffsetY = pixelOffsetY;
		
		this.widthPercent = widthPercent;
		this.widthPixelOffset = widthPixelOffset;
		
		this.heightPercent = heightPercent;
		this.heightPixelOffset = heightPixelOffset;
	}
	
	public double getX(double parentWidth, double parentHeight) {
		return (parentWidth * percentageLocationX) + pixelOffsetX;
	}
	
	public double getY(double parentWidth, double parentHeight) {
		return (parentHeight * percentageLocationY) + pixelOffsetY;
	}
	
	public double getWidth(double parentWidth, double parentHeight) {
		return (parentWidth * widthPercent) + widthPixelOffset;
	}
	
	public double getHeight(double parentWidth, double parentHeight) {
		return (parentHeight * heightPercent) + heightPixelOffset;
	}
}
