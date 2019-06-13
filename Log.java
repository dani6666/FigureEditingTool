import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JTextArea;

/**
 * TextArea shown as a log of the program in {@link FiguresDisplayWindow}.
 * The class handles initialization and input to the log.
 * @author Daniel Włudarczyk
 */
public class Log extends JTextArea
{
    private LinkedList<String> _logContent;

    /**
     * Constructor that set up log properties
     * and default text
     */
    public Log()
    {
        super();
        setBackground(Color.LIGHT_GRAY);
        setEditable(false);
        _logContent=new LinkedList<>();
        for(int i=0;i<5;i++)
        {
            _logContent.add(" ");
        }
        _logContent.add("Log:");
        updateLog();
    }

    
    /**
     * Function that displays given content in the {@link FigureManager#log}
     * @param s message to display
     */
    public void addToLog(String s)
    {
        _logContent.removeFirst();
        _logContent.add(s);
        updateLog();
    }

    private void updateLog()
    {
        setText("");
        for(int i=0;i<_logContent.size()-1;i++)
        {
            append(_logContent.get(i)+"\n");
        }
        append(_logContent.getLast());
    }
}