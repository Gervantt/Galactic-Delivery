package edu.narxoz.galactic.bodies;

public abstract class CelestialBody {

    private final String name;
    private final double x;
    private final double y;

    protected CelestialBody(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(CelestialBody other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
