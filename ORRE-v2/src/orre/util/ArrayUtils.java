package orre.util;

public class ArrayUtils {
	public static void shiftLeft(Object[] array, int amount) {
		Object[] tempArray = new Object[amount];
		for(int i = 0; i < amount; i++) {
			tempArray[i] = array[i];
		}
		for(int i = 0; i < array.length - amount; i++) {
			array[i] = array[i + amount];
		}
		for(int i = 0; i < amount; i++) {
			array[array.length - amount + i] = tempArray[i];
		}
	}
}
