import java.awt.*;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

/**
 * A JPanel which the figures are drawed on
 * @author Daniel Włudarczyk
 */
public class Canvas extends JPanel 
{
    /**
     * Current mouse position
     */
    private Point2D.Float mousePosition;

    /**
     * Initializes mousePosition field, sets up popupMenu and its listeners 
     * and implements mouse listeners for moving, pressing and trgigerring popup menu on mouse click
     */
    public Canvas() 
    {
        mousePosition=new Point2D.Float();

        
        JPopupMenu popupMenu = new JPopupMenu();
            JMenu colorMenuItem = new JMenu("Change Color");

                String[] colorNames = new String[]
                {
                    "Red",
                    "Green",
                    "Blue", 
                    "Yellow", 
                    "Pink", 
                    "Gray", 
                    "Black"
                };

                JMenuItem[] menuItems = new JMenuItem[colorNames.length];

                    for(int i=0;i<colorNames.length;i++)
                    {
                        final int val = i;
                        menuItems[i] = new JMenuItem(colorNames[i]);
                        menuItems[i].addActionListener(new ActionListener() 
                        {
                            @Override
                            public void actionPerformed(ActionEvent e) 
                            {
                                FigureManager.changeColor(colorNames[val]);
                            }
                        });

                        colorMenuItem.add(menuItems[i]);
                    }
            popupMenu.add(colorMenuItem);


            JMenuItem deleteShapeItem = new JMenuItem("Delete");
                deleteShapeItem.addActionListener(new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        FigureManager.deleteFigures();
                    }
                });

            popupMenu.add(deleteShapeItem);
        

        
        addMouseListener(new MouseListener() 
        {
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) 
            {
                FigureManager.movingShapesEnded();
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                mousePosition.setLocation(e.getX(), e.getY());

                FigureManager.mousePress(e.getX(), e.getY());
            }

            @Override
            public void mouseClicked(MouseEvent e) 
            {
                if (e.getButton() == MouseEvent.BUTTON3) 
                {
                    if (FigureManager.mouseRightClick(e.getX(), e.getY())) 
                    {
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                } 
                else 
                {
                    FigureManager.mouseClick(e.getX(), e.getY());
                }
            }
        });

        addMouseMotionListener(new MouseMotionListener()
        {
            @Override
            public void mouseMoved(MouseEvent e) {}
        
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                FigureManager.moveShapes(e.getX() - (float)mousePosition.getX(), e.getY() - (float)mousePosition.getY());
                mousePosition.setLocation(e.getX(), e.getY());
            }
        });

        addMouseWheelListener(new MouseWheelListener()
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) 
            {
                FigureManager.scaleShapes(e.getX(), e.getY(), e.getWheelRotation() * 6);
            }
        });
    }

    /**
     * Draws all figures from {@link FigureManager#allFigures}
     * @param graph obejct delivered by paintComponent function
     */
    private void updateFigures(Graphics2D graph)
    {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graph.setRenderingHints(rh);

        for(IFigure figure : FigureManager.allFigures)
        {
            graph.setPaint(figure.getColor());
            graph.fill(figure.getShape());
        }
    }

    /**
     * Calls paintComponent function in base class JPanel and
     * calls {@link Canvas#updateFigures} function
     */
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        updateFigures((Graphics2D) g);
    }
}