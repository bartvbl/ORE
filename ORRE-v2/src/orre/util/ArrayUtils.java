package orre.util;

public class ArrayUtils {
	public static void shiftLeft(Object[] array, int amount) {
		Object[] temp = array.clone();
		for(int i = 0; i < array.length; i++) {
			array[(i - amount + array.length) % array.length] = temp[i];
		}
	}
	
	public static int[] append(int[] array, int value) {
		int[] appendedArray = new int[array.length + 1];
		System.arraycopy(array, 0, appendedArray, 0, array.length);
		appendedArray[array.length] = value;
		return appendedArray;
	}
}
