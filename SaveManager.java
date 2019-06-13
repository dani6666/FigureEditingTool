import java.awt.geom.*;
import java.io.*;
import java.util.Vector;

/**
 * Class that manages file operations like
 * saving and loading content from files to the editor.
 * @author Daniel Włudarczyk
 */
public class SaveManager
{
    private static File _currentFile;

    /**
     * Deletes file that is currently assinged to the editor content.
     */
    public static void deleteCurrentFile()
    {
        _currentFile.getAbsoluteFile().delete();
    }

    /**
     * Updates currently assinged file based on current editor's content.
     */
    public static void updateExisting()
    {
        try
        {
            FileWriter fileWriter = new FileWriter(_currentFile, false);

            BufferedWriter bufferedWriter= new BufferedWriter(fileWriter);
    
            Vector<IFigure> figures = FigureManager.allFigures;

            bufferedWriter.write(Integer.toString(figures.size()));
            bufferedWriter.newLine();

            for(int i=0; i<figures.size();i++)
            {
                bufferedWriter.write(FigureManager.getColorName(figures.get(i).getColor()));
                bufferedWriter.newLine();

                bufferedWriter.write(figures.get(i).getType());
                bufferedWriter.newLine();

                switch(figures.get(i).getType())
                {
                    case FigureTypes.Circle:
                        CircleFigure circle = (CircleFigure)figures.get(i).getShape();

                        bufferedWriter.write(Float.toString(circle.getCenterXFloat()));
                        bufferedWriter.newLine();

                        bufferedWriter.write(Float.toString(circle.getCenterYFloat()));
                        bufferedWriter.newLine();

                        bufferedWriter.write(Float.toString(circle.width-100));
                        bufferedWriter.newLine();
                    break;

                    case FigureTypes.Rectangle:
                        RectangleFigure rectangle = (RectangleFigure)figures.get(i).getShape();

                        bufferedWriter.write(Float.toString(rectangle.x));
                        bufferedWriter.newLine();

                        bufferedWriter.write(Float.toString(rectangle.y));
                        bufferedWriter.newLine();

                        bufferedWriter.write(Float.toString(rectangle.x + rectangle.width));
                        bufferedWriter.newLine();

                        bufferedWriter.write(Float.toString(rectangle.y + rectangle.height));
                        bufferedWriter.newLine();
                    break;

                    case FigureTypes.Polygon:
                        PolygonFigure polygon = (PolygonFigure)figures.get(i).getShape();
                        bufferedWriter.write(Integer.toString(polygon.npoints));
                        bufferedWriter.newLine();

                        for(int j=0; j<polygon.npoints; j++)
                        {
                            bufferedWriter.write(Integer.toString(polygon.xpoints[j]));
                            bufferedWriter.newLine();

                            bufferedWriter.write(Integer.toString(polygon.ypoints[j]));
                            bufferedWriter.newLine();
                        }
                    break;
                }
            }

            bufferedWriter.close();
        }
        catch(Exception ex)
        {
            MessageWindow.show(ex.getMessage());
        }
    }

    /**
     * Creates new, empty file and assings the file to the editor.
     * @param fileName name of the new file (without format)
     */
    public static void createNewFile(String fileName)
    {
        try
        {
            _currentFile = new File(System.getProperty("user.dir")+"/Data/"+fileName+".txt");

            _currentFile.createNewFile();
        }
        catch(Exception ex)
        {
            MessageWindow.show(ex.getMessage());
        }
    }

    /**
     * Fills editor with figures based on the given file
     * @param fileName name of the source file (with .txt format inside name)
     */
    public static void loadFile(String fileName)
    {
        try
        {
            _currentFile = new File(System.getProperty("user.dir")+"/Data/"+fileName);

          

            FileReader fileReader = new FileReader(_currentFile);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int figuresCount=0;
            String figuresCountString;
            figuresCountString = bufferedReader.readLine();
            if(figuresCountString != null)
            {
                figuresCount = Integer.parseInt(figuresCountString);

                FigureManager.log.addToLog("Adding "+ figuresCount + " figures from "+fileName);
            }

            for(int i=0; i<figuresCount; i++)
            {
                String colorName = bufferedReader.readLine();

                Vector<Point2D.Float> figurePoints = new Vector<Point2D.Float>();
                switch(bufferedReader.readLine())
                {
                    case FigureTypes.Circle:


                        figurePoints.add(new Point2D.Float(
                            Float.parseFloat(bufferedReader.readLine()), 
                            Float.parseFloat(bufferedReader.readLine())));

                        IFigure circle = FigureManager.addFigure(figurePoints, colorName);

                        circle.resize(Float.parseFloat(bufferedReader.readLine()));

                    break;

                    case FigureTypes.Rectangle:

                        for(int j=0; j<2; j++)
                        {
                            figurePoints.add(new Point2D.Float(
                                Float.parseFloat(bufferedReader.readLine()), 
                                Float.parseFloat(bufferedReader.readLine())));
                        }
                    
                        FigureManager.addFigure(figurePoints, colorName);

                    break;

                    case FigureTypes.Polygon:

                        int pointsCount =Integer.parseInt(bufferedReader.readLine());
                        for(int j=0; j<pointsCount; j++)
                        {
                            figurePoints.add(new Point2D.Float(
                                Float.parseFloat(bufferedReader.readLine()), 
                                Float.parseFloat(bufferedReader.readLine())));
                        }
                    
                        FigureManager.addFigure(figurePoints, colorName);

                    break;
                }
            }

            bufferedReader.close();
        }
        catch(Exception ex)
        {
            MessageWindow.show(ex.getMessage());
        }
    }
}
