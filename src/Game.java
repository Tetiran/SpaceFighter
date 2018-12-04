import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements Runnable {

    private static final JFrame frame = new JFrame("Cis120 In Space");
    private static final GameField field = new GameField();
    static int difficulty = 1;

    public static void startGame() {
        EventScript.loadDoc();
        frame.getContentPane().removeAll();
        frame.dispose();
        frame.setUndecorated(true);
        // Main playing area

        frame.add(field, BorderLayout.CENTER);

        final StatusBar status = new StatusBar();
        frame.add(status, BorderLayout.NORTH);
        field.updateBar(status);
        frame.pack();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        // required to force keyhandeling

        field.grabFocus();
        field.startGame();


    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

    public void run() {

        frame.setLocation(0, 0);

        JButton loadGame = new JButton("Load Game");
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!FileMannager.loadSave()) {
                    JOptionPane.showMessageDialog(frame, "No Saved Game to load");
                } else {
                    startGame();
                }
            }
        });


        JButton newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        final JDialog dialog = new JDialog();
        dialog.setModal(true);
        JTextArea area= new JTextArea(
                "This is an editable JTextArea. " +
                        "A text area is a \"plain\" text component, " +
                        "which means that although it can display text " +
                        "in any font, all of the text is in the same font.");

        area.setEditable(false);
        area.setFont(new Font("Serif", Font.ITALIC, 16));
        area.setLineWrap(true);
        dialog.add(area);

        JButton Directions = new JButton("Directions");
        Directions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
            }
        });

        frame.add(newGame, BorderLayout.NORTH);
        frame.add(loadGame);
        frame.add(Directions, BorderLayout.SOUTH);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}

