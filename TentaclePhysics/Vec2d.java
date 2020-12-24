
public class Vec2d {
	double x;
	double y;
	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double distanceSq(Vec2d v) {
		return (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y);
	}
	public double distance(Vec2d v) {
		return Math.pow((x-v.x)*(x-v.x) + (y-v.y)*(y-v.y), 0.5);
	}
}
