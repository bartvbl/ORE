package orre.util;

import openrr.map.WallType;

public class PrintUtils {
	public static void printMatrix(Object[][] matrix) {
		for(Object[] wid : matrix) {
			for(Object type : wid) {
				System.out.print(type + " ");
			}
			System.out.println();
		}
	}
}
