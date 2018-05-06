package migthycode.growth.Game.utils;

public abstract class UsefulFunction {
	public static double map(double x, double a, double b, double c, double d) {
		return  (x - a) / (b - a) * (d - c) + c;
	}
}
