package flap;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Bird extends Ellipse2D {

    public Ellipse2D Body, Eyes;

    public int X= 300, Y= 300, Width= 20, Height= 20;
    public int xEyes= X + 10, yEyes= Y + 5, wEyes= 10, hEyes= 10;

    public Bird() {
        Body= new Ellipse2D.Double(X, Y, Width, Height);
        Eyes= new Ellipse2D.Double(xEyes, yEyes, wEyes, hEyes);

    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {

    }
}
