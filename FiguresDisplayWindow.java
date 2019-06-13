import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Window which shows Canvas(JPanel) with figures, 
 * log with information about program runtime and
 * menu bar for handling files and add or deleting figures.
 * @author Daniel Włudarczyk
 */
public class FiguresDisplayWindow extends JFrame 
{
    /**
     * Constructor which sets up window and
     * its content (menubar, canvas and log),
     * informs {@link SaveManager} whether to create new file or load data from existing and
     * initializes FigureManager.
     * @param fileName name of a file to be loaded/created
     * @param isFileNew flag to determine if file is a new file, true if new file, false if existing file
     */
    public FiguresDisplayWindow(String fileName, boolean isFileNew) 
    {
        super("Figures Editing Tool");
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBounds(200, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel logPanel = new JPanel();
            logPanel.setMinimumSize(new Dimension(800, 110));
            logPanel.setPreferredSize(new Dimension(800, 110));
            logPanel.setMaximumSize(new Dimension(800, 110));
            logPanel.setBackground(Color.LIGHT_GRAY);
            
            Log log = new Log();

            logPanel.add(log);


        Canvas canvas = new Canvas();

        FigureManager.initialize(log, canvas);
        
        if (isFileNew) 
        {
            SaveManager.createNewFile(fileName);
        }
        else
        {
            SaveManager.loadFile(fileName);
        }


        JMenuBar menu = new JMenuBar();

            JMenu fileMenuItem = new JMenu("File");

                JMenuItem saveFile = new JMenuItem("Save");
                    saveFile.addActionListener((e) ->
                        {
                            SaveManager.updateExisting();
                        });
                    fileMenuItem.add(saveFile);

                JMenuItem saveFileAs = new JMenuItem("Save As");
                    saveFileAs.addActionListener((e) ->
                        {
                            log.addToLog("message");
                            SaveFileAsWindow window = new SaveFileAsWindow();
                            window.setVisible(true);
                        });
                    fileMenuItem.add(saveFileAs);

                JMenuItem deleteFile = new JMenuItem("Delete This File");
                    deleteFile.addActionListener((e) ->
                        {   
                            SaveManager.deleteCurrentFile();
                            setVisible(false);
                            FileChooseWindow window = new FileChooseWindow();
                            dispose();
                        });
                    fileMenuItem.add(deleteFile);

                JMenuItem homePage = new JMenuItem("Return to home page");
                    homePage.addActionListener((e) ->
                        {
                            setVisible(false);
                            FileChooseWindow window = new FileChooseWindow();
                            dispose();
                        });
                    fileMenuItem.add(homePage);

                menu.add(fileMenuItem);

            JMenu shapesMenuItem = new JMenu("Shapes");

                JMenu createShape = new JMenu("Create");

                    JMenuItem rect = new JMenuItem("Rectangle");
                        rect.addActionListener((e) ->
                            {
                                String[] message = FigurePrototype.initializeFigure(2);
                                log.addToLog(message[0]);
                                log.addToLog(message[1]);
                            });
                    createShape.add(rect);

                    JMenuItem circle = new JMenuItem("Circle");
                        circle.addActionListener((e) ->
                            {
                                String[] message = FigurePrototype.initializeFigure(1);
                                log.addToLog(message[0]);
                                log.addToLog(message[1]);
                            });
                    createShape.add(circle);

                    JMenu polygon = new JMenu("Polygon");
                        JMenuItem[] polygonPointsItems = new JMenuItem[5];
                        for(int i=0;i<5;i++)
                        {
                            final int val=i+3;
                            polygonPointsItems[i]=new JMenuItem(Integer.toString(i+3) + " points");
                            polygonPointsItems[i].addActionListener((e) ->
                                {
                                    String[] message = FigurePrototype.initializeFigure(val);
                                    log.addToLog(message[0]);
                                    log.addToLog(message[1]);
                                });
                            polygon.add(polygonPointsItems[i]);
                        }   

                    createShape.add(polygon);

                    shapesMenuItem.add(createShape);

                JMenuItem deleteAllShapes = new JMenuItem("Delete all");
                    deleteAllShapes.addActionListener((e) ->
                        {
                            FigureManager.deleteAll();
                        });
                    shapesMenuItem.add(deleteAllShapes);

            menu.add(shapesMenuItem);

            JMenu infoItem = new JMenu("Info");
                infoItem.addMouseListener(new MouseListener() 
                {   
                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mousePressed(MouseEvent e) {}

                    @Override
                    public void mouseExited(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {}

                    @Override
                    public void mouseClicked(MouseEvent e) 
                    {
                        ProgramInfoWindow window = new ProgramInfoWindow();
                        window.setVisible(true);
                    }
                });

            menu.add(infoItem);

        

        setJMenuBar(menu);
        add(canvas);
        add(logPanel);

        setVisible(true);
    }
}