import java.awt.*;
import java.awt.geom.*;


/**
 * Holds whole data of a circle figure
 * @author Daniel Włudarczyk
 */
public class CircleFigure extends Ellipse2D.Float implements IFigure
{
    /**
     * Color of the circle
     */
    private Color _color=Color.GRAY;

    /**
     * Modifies gven point to get the upper left corner of circle and
     * sets size of cricle to 100x100 to call constructor of base Ellipse2D class
     * @param middeOfCircle The middle of the cicle
     */
    CircleFigure(Point2D.Float middeOfCircle)
    {
        super(middeOfCircle.x-50, middeOfCircle.y-50, 100, 100);
    }


    public void resize(float growth)
    {
        width+=growth;
        height+=growth;
        x-=growth/2;
        y-=growth/2;
    }
    public void move(float dx, float dy)
    {
        x+=dx;
        y+=dy;
    }

    public float getCenterXFloat()
    {
        return (float)getCenterX();
    }
    public float getCenterYFloat()
    {
        return (float)getCenterY();
    }

    public void changeColor(Color c)
    {
        _color = c;
    }

    public boolean isHit(float x, float y)
    {
        return contains(x,y);
    }

    public String getType()
    {
        return FigureTypes.Circle;
    }
    public Color getColor()
    {
        return _color;
    }
    public Shape getShape()
    {
        return (Shape)this;
    }
}