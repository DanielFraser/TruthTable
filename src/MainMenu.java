import java.awt.*;
import javax.swing.*;

public class MainMenu {
    

    public void addComponentToPane(Container pane) {
    	
        JTabbedPane tabbedPane = new JTabbedPane();

        //Create the "cards".
        JPanel card1 = new TruthTablePanel();
        JPanel card2 = new ProofsPanel();
        JPanel card3 = new SequencesPanel();
        JPanel card4 = new booleanMatrix();
        JPanel card5 = new ClosuresPanel();
        
        tabbedPane.addTab("Truth Table", card1);
        tabbedPane.addTab("Proofs", card2);
        tabbedPane.addTab("Sequences", card3);
        tabbedPane.addTab("Boolean Matrices", card4);
        tabbedPane.addTab("Relation Closures", card5);
        
        pane.add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TabDemo");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MainMenu main = new MainMenu();
        main.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.setBounds(100, 100, 1000, 850);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}