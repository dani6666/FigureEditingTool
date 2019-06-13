import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

import javax.swing.*;

/**
 * A window that gathers nformation about file that is going to be edited or created.
 * @author Daniel Włudarczyk
 */
public class FileChooseWindow extends JFrame 
{
    /**
     * Constructor which sets up window and its content.
     * Sets listeners of all buttons in the window.
     */
    public FileChooseWindow() 
    {
        super("File Choose");
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setBounds(200, 100, 220, 110);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel openFilePanel = new JPanel();
            openFilePanel.setLayout(new BoxLayout(openFilePanel, BoxLayout.Y_AXIS));

            JLabel chooseFileLabel = new JLabel("Choose file:");
        
            Vector<String> savedFilesNames = new Vector<String>();

            File dataDirectory = new File("./Data");
            for(File f : dataDirectory.listFiles())
            {
                savedFilesNames.add(f.getName());
            }

            JComboBox savedFilesComboBox = new JComboBox<String>(savedFilesNames);

            JButton openFileButton = new JButton("Open");
                openFileButton.addActionListener((e) -> 
                    {
                        if(savedFilesComboBox.getSelectedItem() != null)
                        {
                            FiguresDisplayWindow window = new FiguresDisplayWindow((String)savedFilesComboBox.getSelectedItem(), false);
                            setVisible(false);
                            window.setVisible(true);
                        }
                        else
                        {
                            MessageWindow.show("There is no selected file to open");
                        }
                    });

            openFilePanel.add(chooseFileLabel);
            openFilePanel.add(savedFilesComboBox);
            openFilePanel.add(openFileButton);
            for (Component comp : openFilePanel.getComponents()) 
            {
                ((JComponent)comp).setAlignmentX(Component.CENTER_ALIGNMENT);
            }



        JPanel createNewFilePanel = new JPanel();
            createNewFilePanel.setLayout(new BoxLayout(createNewFilePanel, BoxLayout.Y_AXIS));

            JLabel inputFileNameLabel = new JLabel("Input file name:");

            JTextField fileNameTextField = new JTextField(1);
                fileNameTextField.setMaximumSize(new Dimension(150,30));


            JButton createNewFileButton = new JButton("Create new");
                createNewFileButton.addActionListener((e) ->
                    {
                            FiguresDisplayWindow window = new FiguresDisplayWindow(fileNameTextField.getText(), true);
                            setVisible(false);
                            window.setVisible(true);
                    });

            createNewFilePanel.add(inputFileNameLabel);
            createNewFilePanel.add(fileNameTextField);
            createNewFilePanel.add(createNewFileButton);
            for (Component comp : createNewFilePanel.getComponents()) 
            {
                ((JComponent)comp).setAlignmentX(Component.CENTER_ALIGNMENT);
            }

        add(createNewFilePanel);
        add(openFilePanel);

        setVisible(true);
    }
}