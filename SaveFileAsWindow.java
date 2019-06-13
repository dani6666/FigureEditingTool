import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Dialog window where user can choose a name of the new file 
 * which will be created based on current state of all figures.
 * @author Daniel Włudarczyk
 */
public class SaveFileAsWindow extends JFrame
{
    /**
     * Constructor that set up window and
     * button click listener.
     */
    public SaveFileAsWindow()
    {
        super("Save file as");
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBounds(200, 100, 200, 120);

        JLabel enterNameLabel = new JLabel("Enter file name");

        JTextField fileNameTextField = new JTextField();

        JButton saveButton = new JButton("Save");
            saveButton.addActionListener((e) ->
                {
                    SaveManager.createNewFile(fileNameTextField.getText());
                    SaveManager.updateExisting();
                    setVisible(true);
                    dispose();
                });

        add(enterNameLabel);
        add(fileNameTextField);
        add(saveButton);
        for (Component comp : getComponents()) 
        {
            ((JComponent)comp).setAlignmentX(Component.CENTER_ALIGNMENT);
        }        
    }
}