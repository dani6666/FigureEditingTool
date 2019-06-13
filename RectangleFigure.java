import java.awt.*;
import java.awt.geom.*;

/**
 * Holds whole data of a rectangle figure
 * @author Daniel Włudarczyk
 */
public class RectangleFigure extends Rectangle2D.Float implements IFigure
{
    public Color color=Color.GRAY;

    /**
     * Builds rectangle from given points
     * @param leftTopCorner left top corner of the rectangle
     * @param rightBottomCorner right bottom corner of the rectangle
     */
    RectangleFigure(Point2D.Float leftTopCorner, Point2D.Float rightBottomCorner)
    {
        super(leftTopCorner.x, leftTopCorner.y, rightBottomCorner.x-leftTopCorner.x, rightBottomCorner.y-leftTopCorner.y);
    }

    public void resize(float growth)
    {
        float addWidth= growth*100/height,
            addHeight= growth*100/width;
        width+=addWidth;
        height+=addHeight;
        x-=addWidth/2;
        y-=addHeight/2;
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
        color = c;
    }

    public boolean isHit(float x, float y)
    {
        return contains(x,y);
    }

    public String getType()
    {
        return FigureTypes.Rectangle;
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