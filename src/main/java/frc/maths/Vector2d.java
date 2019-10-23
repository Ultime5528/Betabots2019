package frc.maths;

/**
 * Vector2
 */
public class Vector2d {

    private final double x;
    private final double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d add(double x, double y){
        return new Vector2d(this.x + x, this.y + y);
    }

    public Vector2d add(Vector2d vec){
        return add(vec.x, vec.y);
    }

    public Vector2d scale(double s){
        return new Vector2d(this.x * s, this.y * s);
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }
}