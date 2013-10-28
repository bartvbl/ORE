package orre.util;

public class MathUtil {
	public static int clamp(int input, int min, int max) {
		if(input < min) {
			return min;
		}
		if(input > max) {
			return max;
		}
		return input;
	}
}
