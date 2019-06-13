import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;

/**
 * Holds whole data of a polygon figure
 * @author Daniel Włudarczyk
 */
public class PolygonFigure extends Polygon implements IFigure
{
    public Color color=Color.GRAY;

    /**
     * Build a polygon from given vertices
     * @param points vector of points representing polygon vertices
     */
    PolygonFigure(Vector<Point2D.Float> points)
    {
        super();

        for (Point2D.Float point : points)
        {
            addPoint((int)point.getX(), (int)point.getY());
        }
    }

    public void resize(float growth)
    {
        Point2D.Float centerOfPolygon = new Point2D.Float(getCenterXFloat(),getCenterYFloat());

        for(int i=0; i<npoints; i++)
        {
            xpoints[i]+=(int)((float)xpoints[i] - centerOfPolygon.x)*growth/100;
            ypoints[i]+=(int)((float)ypoints[i] - centerOfPolygon.y)*growth/100;
        }

        invalidate();
    }
    public void move(float dx, float dy)
    {
        translate((int)dx, (int)dy);
    }


    public float getCenterXFloat()
    {
        float result=0;

        for(int i=0; i<npoints; i++)
        {
            result+=xpoints[i];
        }
        result/=npoints;

        return result;
    }
    public float getCenterYFloat()
    {
        float result=0;

        for(int i=0; i<npoints; i++)
        {
            result+=ypoints[i];
        }
        result/=npoints;

        return result;
    }

    public void changeColor(Color c)
    {
        color = c;
    }

    public boolean isHit(float x, float y)
    {
        return contains(x, y);
    }

    public String getType()
    {
        return FigureTypes.Polygon;
    }
    public Color getColor()
    {
        return color;
    }
    public Shape getShape()
    {
        return (Shape)this;
    }
}