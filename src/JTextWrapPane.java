import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

public class JTextWrapPane extends JTextPane {

    boolean wrapState = true;
    JTextArea j = new JTextArea();

    public JTextWrapPane(StyledDocument doc) {
        super(doc);
    }
    
	public boolean getScrollableTracksViewportWidth() {
        return wrapState;
    }


    public void setLineWrap(boolean wrap) {
        wrapState = wrap;
    }


    public boolean getLineWrap(boolean wrap) {
        return wrapState;
    }
}  