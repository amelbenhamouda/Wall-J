package fr.umlv.projet.objectAndAStar;

/**
 * Class for Point.
 * 
 * @author BEN HAMOUDA, HARDY, RUBIO
 *
 */
public class Point {
	private final int x;
	private final int y;

	/**
	 * Constructor for Point.
	 * 
	 * @param x
	 *            Coordinated x of the point.
	 * @param y
	 *            Coordinated y of the point.
	 */
	public Point(int x, int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("coordinate's robot wrong must be positive: " + x + " x " + y + " y ");
		}
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns a hash code value for the object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Test the equality between two points.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * The String class represents character strings.
	 */
	@Override
	public String toString() {
		return "p: (" + x + "," + y + ") ";
	}

	/**
	 * Getter to recover the x.
	 * 
	 * @return x Current x.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter to recover the y.
	 * 
	 * @return y Current y.
	 */
	public int getY() {
		return y;
	}

}
