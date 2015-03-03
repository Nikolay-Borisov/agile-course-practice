package ru.unn.agile.DeterminatorIntersection.Model;

public class Point3D {
    private final double x;
    private final double y;
    private final double z;

    public Point3D(final double inputX, final double inputY, final double inputZ) {
        x = inputX;
        y = inputY;
        z = inputZ;
    }

    public boolean isNullPoint() {
        return x == 0 && y == 0 && z == 0;
    }

    public double scalarMultiplication(final Point3D mPoint) {
        return x * mPoint.x + y * mPoint.y + z * mPoint.z;
    }
}
