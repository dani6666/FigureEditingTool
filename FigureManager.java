import java.awt.*;
import java.awt.geom.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/**
 * Class taking care of all the figures and actions executed to all marked or clicked figures.
 * @author Daniel Włudarczyk
 */
public class FigureManager 
{
    /**
     * refference to log shown in FiguresDisplayWindow
     */
    public static Log log;
    /**
     * all figures shown on canvas
     */
    public static Vector<IFigure> allFigures;
    /**
     * figures that are to be moved
     */
    private static Vector<IFigure> _clickedFigures;
    /**
     * figures that are to be painted or deleted
     */
    private static Vector<IFigure> _rightClickedFigures;
    /**
     * refference to canvas shown in {@link FiguresDisplayWindow}
     */
    private static Canvas _canvas;
    /**
     * set of colors related to their name as String
     */
    private static Hashtable<String, Color> _colors;    
    
    /**
     * Initializing function that needs to be called on showing the window which displays figures
     *  before any other function from this class.
     * @param l a Log component of FiguresDisplayWindow
     * @param c a Canvas (JPnael that displays figures) component of {@link FiguresDisplayWindow}
     */
    public static void initialize(Log l, Canvas c)
    {
        log = l;
        _canvas = c;

        allFigures = new Vector<IFigure>();
        _clickedFigures = new Vector<IFigure>();
        _rightClickedFigures = new Vector<IFigure>();

        _colors = new Hashtable<String, Color>();
        _colors.put("Red", Color.RED);
        _colors.put("Green", Color.GREEN);
        _colors.put("Blue", Color.BLUE);
        _colors.put("Yellow", Color.YELLOW);
        _colors.put("Pink", Color.PINK);
        _colors.put("Gray", Color.GRAY);
        _colors.put("Black", Color.BLACK);
    }

    /**
     * Collects all the figures which the given point is inside of.
     * @param x x coordinate of the given point
     * @param y y coordinate of the given point
     * @return a Vector of figures which the given point is inside of.
     */
    private static Vector<IFigure> getAllCoveredFigures(float x, float y)
    {
        Vector<IFigure> result = new Vector<IFigure>();

        for(IFigure figure : allFigures)
        {
            if(figure.isHit(x,y))
            {
                result.add(figure);
            }
        }

        return result;
    }

    /**
     * Adds a figure to {@link FigureManager#allFigures} based on given points and their amount and
     * gives a figure a default gray color.
     * @param points middle of the circle or
     * upper left and bottom right corner of the rectangle or
     * all the vertices of the polygon
     * @return refference to the figure that was added to allFigures
     */
    public static IFigure addFigure(Vector<Point2D.Float> points)
    {
        return addFigure(points, "Gray");
    }


    /**
     * Adds a figure to allFigures based on given points and their amount and
     * gives a figure a color to be painted with.
     * @param points middle of the circle or
     * upper left and bottom right corner of the rectangle or
     * all the vertices of the polygon
     * @param colorName name of a color of the figure
     * @return refference to the figure that was added to allFigures, 
     * returns null if given points are incorrect
     */
    public static IFigure addFigure(Vector<Point2D.Float> points, String colorName)
    {
        switch(points.size())
        {
            case 1:
                log.addToLog("Drawing circle");
                CircleFigure circle = new CircleFigure(points.get(0));
                circle.changeColor(_colors.get(colorName));
                allFigures.add(circle);
                _canvas.repaint();

                return circle;

            case 2:
                log.addToLog("Drawing rectangle");
                RectangleFigure rectangle = new RectangleFigure(points.get(0), points.get(1));
                if(rectangle.width <= 0 || rectangle.height<=0)
                {
                    MessageWindow.show("Incorrectly chosen points");
                    return null;
                }
                rectangle.changeColor(_colors.get(colorName));
                allFigures.add(rectangle);
                _canvas.repaint();

                return rectangle;

            default:
                log.addToLog("Drawing polygon");
                PolygonFigure polygon = new PolygonFigure(points);
                polygon.changeColor(_colors.get(colorName));
                allFigures.add(polygon);
                _canvas.repaint();

                return polygon;
        }
    }

    /**
     * Has to be called when the process of moving shapes ended.
     */
    public static void movingShapesEnded()
    {
        for(IFigure figure : _clickedFigures)
        {
            log.addToLog("A "+figure.getType()+" was moved to position X = " + figure.getCenterXFloat() + "  Y = " + figure.getCenterYFloat());
        }

        _clickedFigures.clear();
    }

    /**
     * Adds all the figures that contains given point to {@link FigureManager#_clickedFigures}.
     * @param x x coordinate of the given point
     * @param y y coordinate of the given point
     */
    public static void mousePress(float x, float y)
    {
        _rightClickedFigures.clear();

        _clickedFigures.addAll(getAllCoveredFigures(x, y));
    }

    /**
     * Adds all the figures that contains given point to {@link FigureManager#_rightClickedFigures}.
     * @param x x coordinate of the given point
     * @param y y coordinate of the given point
     * @return returns true if the were any figures within the given point
     */
    public static boolean mouseRightClick(float x, float y)
    {
        _rightClickedFigures.clear();

        _rightClickedFigures.addAll(getAllCoveredFigures(x, y));

        if(_rightClickedFigures.size()!=0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Clears memory of the figures selected for deletion or color change and
     * collects a point for {@link FigurePrototype} class if any figure is currently in creation.
     * @param x x coordinate of the given point
     * @param y y coordinate of the given point
     */
    public static void mouseClick(float x, float y)
    {
        _rightClickedFigures.clear();

        if(FigurePrototype.isReady)
        {
            String message = FigurePrototype.addPoint(x, y);
            if(message == null)
            {
                addFigure(FigurePrototype.collectedPoints);
            }
            else
            {
                log.addToLog(message);
            }
        }
    }

    /**
     * Moves all the figures from {@link FigureManager#_clickedFigures}.
     * @param dx x coordinate of the given vector
     * @param dy y coordinate of the given vector
     */
    public static void moveShapes(float dx, float dy)
    {
        for(IFigure figure : _clickedFigures)
        {
            figure.move(dx,dy);
        }

        _canvas.repaint();
    }

    /**
     * Scales/resizes all the figures that contain given point.
     * @param x x coordinate of the given point
     * @param y y coordinate of the given point
     * @param growth amount of size to be added to the figures, if negative figures decrease in size
     */
    public static void scaleShapes(float x, float y, float growth)
    {
        String typeOfResize;
        if(growth>0)
        {
            typeOfResize = "enlarged";
        }
        else
        {
            typeOfResize = "reduced";
        }

        for(IFigure figure : getAllCoveredFigures(x, y))
        {
            figure.resize(growth);
            log.addToLog("A "+figure.getType()+" was "+typeOfResize);
        }


        _canvas.repaint();
    }

    /**
     * Changes color of all figures from {@link FigureManager#_rightClickedFigures}
     * @param colorName name of the given color
     */
    public static void changeColor(String colorName)
    {
        for(IFigure figure : _rightClickedFigures)
        {
            figure.changeColor(_colors.get(colorName));
            
            log.addToLog("A "+figure.getType()+"'s color was changed to "+colorName);
        }

        _canvas.repaint();
    }

    /**
     * Returns the name of the given color
     * @param color given color
     * @return name of the given color
     */
    public static String getColorName(Color color)
    {
        for(Map.Entry entry : _colors.entrySet())
        {
            if(color == entry.getValue())
            {
                return (String)entry.getKey();  
            }
        }

        return null;
    }


    /**
     * Deletes all figures from {@link FigureManager#_rightClickedFigures}
     */
    public static void deleteFigures()
    {
        for(IFigure figure : _rightClickedFigures)
        {
            allFigures.remove(figure);
            log.addToLog("A "+figure.getType()+" was deleted");
        }

        _canvas.repaint();
    }

    /**
     * Deletes all the existing figures.
     */
    public static void deleteAll()
    {
        allFigures.clear();

        log.addToLog("All figures were deleted");

        _canvas.repaint();
    }
}