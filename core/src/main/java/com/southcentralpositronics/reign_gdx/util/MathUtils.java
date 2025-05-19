package com.southcentralpositronics.reign_gdx.util;

public class MathUtils {

	public static int min(int value, int min) {
		return Math.max(value, min);
	}

	public static double min(double value, double min) {
		return Math.max(value, min);
	}

	public static int max(int value, int max) {
		return Math.min(value, max);
	}
	public static double max(double value, double max) {
		return Math.min(value, max);
	}

	public static int clamp(int value, int min, int max) {
		if (value < min)
			return min;
		else if (value > max)
			return max;
		else return value;
	}

	public static double clamp(double value, double min, double max) {
		if (value < min)
			return min;
		else if (value > max)
			return max;
		else return value;
	}

}
