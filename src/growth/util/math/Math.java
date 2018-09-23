package growth.util.math;

/**
 * Math class.
 * This class is used to compute complicated calculations.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Math {

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
	public static float map(float x, float a, float b, float c, float d) {
		return (x - a) / (b - a) * (d - c) + c;
	}

	public static String[] addValue(String[] values, String ... newValues ){
		String[] values2 = new String[values.length+newValues.length];
		System.arraycopy(values, 0, values2, 0, values.length);
		System.arraycopy(newValues, 0, values2, values.length, newValues.length);

		return values2;
	}

	/**
	 * Test if a string is a number
	 *
	 * @param string String to test
	 *
	 * @return boolean's result
	 */
	public static boolean notInteger(String string) {
		try {
			Integer.parseInt(string);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}
}