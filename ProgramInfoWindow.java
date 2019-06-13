import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Window that displays program's information
 * @author Daniel Włudarczyk
 */
public class ProgramInfoWindow extends JFrame 
{
    /**
     * Set up window and its content.
     * Sets button click listener.
     */
    public ProgramInfoWindow() 
    {
        super("Program info");
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBounds(200, 100, 280, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea info = new JTextArea("Program : Figure Editing Tool \n Author : Daniel Wludarczyk \n Usage: Editing and saving\n simple geometry obejcts");
            info.setEditable(false);

        JButton okButton = new JButton("OK");
            okButton.addActionListener((e) ->
                {
                    setVisible(false);
                    dispose();
                });

        add(info);
        add(okButton);
        for (Component comp : getComponents()) 
        {
            ((JComponent)comp).setAlignmentX(Component.CENTER_ALIGNMENT);
        }
    }
}