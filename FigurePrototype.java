import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * Class used as a holder for data of a figure that is currenty being created.
 * @author Daniel Włudarczyk
 */
public class FigurePrototype
{
    /**
     * Determines if the {@link FigurePrototype} is currently collecting points.
     * True if there is a figure in the creation, false if not.
     */
    public static boolean isReady=false;
    private static int _allPointsToCollect=0;
    /**
     * vector of points that will be passed to the {@link Figure} constructor
     */
    public static Vector<Point2D.Float> collectedPoints=new Vector<Point2D.Float>();

    /**
     * Adds point to {@link FigurePrototype#collectedPoints}.
     * @param x x coordinate of a given point
     * @param y y coordinate of a given point
     * @return message with information of how many point are left to be collected 
     * or null if all the required points are already collected
     */
    public static String addPoint(float x, float y)
    {
        collectedPoints.add(new Point2D.Float(x, y));
        if(_allPointsToCollect - collectedPoints.size()== 0)
        {
            isReady = false;
            
            return null;
        }
        else
        {
            return Integer.toString(_allPointsToCollect - collectedPoints.size()) + " points left to choose";
        }
    }

    /**
     * Initializes a prototype of a figure in order to start gathering data about the figure.
     * @param size amount of points that are to e collected
     * @return detail information about the points that has to be given t a figure prototype or
     * information thatthere is already a figure in creation is it is so
     */
    public static String[] initializeFigure(int size)
    {
        if(!isReady)
        {
            collectedPoints.clear();
            isReady=true;
            _allPointsToCollect=size;
            String[] result = new String[2];
            result[0] = "Creating ";
            switch(size)
            {
                case 1:
                    result[0] += "circle";
                    result[1] = "Choose the middle of the circle";
                break;

                case 2:
                    result[0]+="rectangle";
                    result[1]="Choose left-top and then right-bottom corners of the rectangle";
                break;

                default:
                    result[0]+="polygon";
                    result[1]="Choose the points of the polygon";
                break;
            }
            result[0]+="...";
            
            return result;
        }
        else
        {
            return new String[]{"Can't create 2 figures and the same time !",
            "Finish creatin the first figure"};
        }
        
    }
}