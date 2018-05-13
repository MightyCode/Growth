package growth.game.utils;

/**
 * Math class.
 * This class is used to compute complicated calculations.
 *
 * @author MightyCode
 * @version 1.0
 */
public abstract class Math {

	/**
	 * Calculate the position of a number in the first interval to transpose this number in an second interval.
	 *
	 * @param x The number in the first interval.
	 * @param a First number of the first interval.
	 * @param b Latest number of the first interval.
	 * @param c First number of the second interval..
	 * @param d Latest number of the second interval.
	 *
	 * @return result
	*/
	public static double map(double x, double a, double b, double c, double d) {
		return (x - a) / (b - a) * (d - c) + c;
	}
}
