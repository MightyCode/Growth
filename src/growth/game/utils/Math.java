package growth.game.utils;

public abstract class Math {
	public static double map(double x, double a, double b, double c, double d) {
		return (x - a) / (b - a) * (d - c) + c;
	}
}
