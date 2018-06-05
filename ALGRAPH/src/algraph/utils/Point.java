package algraph.utils;

public class Point {
	private double x;
	private double y;
	
	public Point() {
		x = 0;
		y = 0;
	}
	
	/*
	 * Create a new point(x,y)
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/*
	 * return true if this.x coordinate == otherPoint.x
	 * AND this.y coordinate == otherPoint.y
	 * else false
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

	
	
	
}
