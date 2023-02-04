package engine.maths;

import java.util.Objects;

public class Vector2f {
    private float x, y;

    public Vector2f(float x, float y){
        this.x = x;
        this.y = 1 - y; // don't change this. it works this way
    }

    public static Vector2f add(Vector2f vector1, Vector2f vector2) {
        return new Vector2f(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    public static Vector2f subtract(Vector2f vector1, Vector2f vector2) {
        return new Vector2f(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
    }

    public static Vector2f multiply(Vector2f vector1, Vector2f vector2) {
        return new Vector2f(vector1.getX() * vector2.getX(), vector1.getY() * vector2.getY());
    }

    public static Vector2f divide(Vector2f vector1, Vector2f vector2) {
        return new Vector2f(vector1.getX() / vector2.getX(), vector1.getY() / vector2.getY());
    }

    public static float length(Vector2f vector) {
        return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
    }

    public static Vector2f normalize(Vector2f vector) {
        float len = Vector2f.length(vector);
        return Vector2f.divide(vector, new Vector2f(len, len));
    }

    public static float dot(Vector2f vector1, Vector2f vector2) {
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2f vector2f)) return false;
        return Float.compare(vector2f.getX(), getX()) == 0 && Float.compare(vector2f.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
