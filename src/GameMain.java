import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_SPACE;

public class GameMain implements Runnable{
    public void run() {

        final JFrame frame = new JFrame("Cis120 In Space");
        frame.setLocation(0, 0);


        // Main playing area
        final GameField field = new GameField();
        frame.add(field, BorderLayout.CENTER);

        final StatusBar status = new StatusBar();
        //frame.add(status, BorderLayout.NORTH);

        frame.setResizable( false );
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GameMain());
    }
}

