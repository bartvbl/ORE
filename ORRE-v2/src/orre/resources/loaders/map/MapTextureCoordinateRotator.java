package orre.resources.loaders.map;

import openrr.map.Orientation;
import openrr.map.WallType;

public class MapTextureCoordinateRotator {

	public static double[][] rotateCoordinates(double[][] textureCoordinates2x2, Orientation orientation, WallType wallType) {
		textureCoordinates2x2 = performRotation(textureCoordinates2x2, orientation);
		return textureCoordinates2x2;
	}
	
	private static Orientation rotateOrientationCW(Orientation orientation) {
		switch(orientation) {
			default:
			case north: return Orientation.east;
			case east: return Orientation.south;
			case south: return Orientation.west;
			case west: return Orientation.north;
		}
	}
	
	private static Orientation rotateOrientationCCW(Orientation orientation) {
		switch(orientation) {
			default:
			case north: return Orientation.west;
			case east: return Orientation.north;
			case south: return Orientation.east;
			case west: return Orientation.south;
		}
	}
	
	private static Orientation flipOrientation(Orientation orientation) {
		switch(orientation) {
			default:
			case north: return Orientation.south;
			case east: return Orientation.west;
			case south: return Orientation.south;
			case west: return Orientation.east;
		}
	}

	private static double[][] performRotation(double[][] textureCoordinates2x2, Orientation orientation) {
//		if(orientation == Orientation.west) {
//			switched out the two u coordinates
//			swapUCoordinates(textureCoordinates2x2);
	//	} else if(orientation == Orientation.east) {
//			//switched out the two v coordinates
//			swapVCoordinates(textureCoordinates2x2);
//		} else if(orientation == Orientation.south) {
//			//switched out both the u and v coordinates
//			swapUCoordinates(textureCoordinates2x2);
//			swapVCoordinates(textureCoordinates2x2);
//		}
		return textureCoordinates2x2;
	}
	
	private static void swapUCoordinates(double[][] textureCoordinates2x2) {
		double uCoordinate = textureCoordinates2x2[0][0];
		textureCoordinates2x2[0][0] = textureCoordinates2x2[1][0];
		textureCoordinates2x2[1][0] = uCoordinate;
	}

	private static void swapVCoordinates(double[][] textureCoordinates2x2) {
		double vCoordinate = textureCoordinates2x2[0][1];
		textureCoordinates2x2[0][1] = textureCoordinates2x2[1][1];
		textureCoordinates2x2[1][1] = vCoordinate;
	}

}
