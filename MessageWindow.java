import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Window to display error's details.
 * @author Daniel Włudarczyk
 */
public class MessageWindow extends JFrame
{
    /**
     * Creates window, sets its size and content.
     * @param msg message to be displayed within the wndow
     */
    private MessageWindow(String msg) 
    {
        super("Message");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 100+msg.length()*8, 120);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        Label messageLabel = new Label(msg);
        Button okButton = new Button("Ok");
        okButton.addActionListener((e) ->
            {
                setVisible(false);
                dispose();    
            });

        add(messageLabel);
        add(okButton);
        for (Component comp : getComponents()) 
        {
            ((JComponent)comp).setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        setVisible(true);
    }

    public static void show(String msg)
    {
        MessageWindow win = new MessageWindow(msg);
    }
}