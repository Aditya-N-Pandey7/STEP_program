abstract class Shape {
    private static int counter = 1000;
    private final String shapeId;

    protected Shape() {
        shapeId = "SH-" + (++counter);
    }

    public abstract double calculateArea();

    public abstract void scale(double factor);

    public abstract void scale(double xFactor, double yFactor);

    public String getShapeId() {
        return shapeId;
    }

    public static void printArea(Shape s) {
        System.out.println(s.calculateArea());
    }
}

class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        if (radius <= 0) throw new IllegalArgumentException("radius must be positive");
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("factor must be positive");
        radius *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        if (xFactor <= 0 || yFactor <= 0)
            throw new IllegalArgumentException("factors must be positive");
        radius *= Math.sqrt(xFactor * yFactor);
    }
}

class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        if (side <= 0) throw new IllegalArgumentException("side must be positive");
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("factor must be positive");
        side *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        if (xFactor <= 0 || yFactor <= 0)
            throw new IllegalArgumentException("factors must be positive");
        side *= Math.sqrt(xFactor * yFactor);
    }
}

public class BasicDrawingCanvas {
    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);

        System.out.println(c.calculateArea());
        System.out.println(sq.calculateArea());

        sq.scale(2.0);
        System.out.println(sq.calculateArea());

        Shape.printArea(c);

        // Shape s = new Shape(); // Does not compile because Shape is abstract.
    }
}
