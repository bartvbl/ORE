package orre.util;

public class ArrayUtils {
	public static void shiftLeft(Object[] array, int amount) {
		Object[] temp = array.clone();
		for(int i = 0; i < array.length; i++) {
			array[(i - amount + array.length) % array.length] = temp[i];
		}
	}
}
