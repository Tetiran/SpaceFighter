import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements Runnable {

    private static final JFrame frame = new JFrame("Cis120 In Space");
    static int difficulty = 1;
    static void endGame(){
        System.exit(0);
    }

    private static void startGame() {
        final GameField field = new GameField();
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

                GameField.newPlayer();
                startGame();
            }
        });

        final JDialog dialog = new JDialog();
        dialog.setModal(true);
        JTextArea area= new JTextArea(
                "Welcome to CIS 120 in space! \n ");
        area.append("You are a small crew manning a Imperial spaceship sent to fight the " +
                "oncoming invasion.\n");
        area.append("Press newgame to create a newgame and loadgame to load a previous save.\n");
        area.append("When in game press escape to pause and quit the game or save the game.\n");
        area.append("To move press WASD, and to fire your lasers press spacebar. \n");
        area.append("Your ship points in the direction of your cursor and that is the direction " +
                "your lasers will fire. \n");
        area.append("Your ship's shield regenerates over time if you are not hit.\n");
        area.append("You will die when your health gets to zero. You will win when " +
                "all the enemies are killed. Your score is on the top right");

        area.setEditable(false);
        area.setFont(new Font("Serif", Font.ITALIC, 16));
        area.setLineWrap(false);
        //Dimension size= new Dimension(200,100);
        //area.setPreferredSize(size);
        dialog.add(area);
        dialog.pack();

        JButton Directions = new JButton("Directions");
        Directions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
            }
        });

        frame.add(newGame, BorderLayout.NORTH);
        frame.add(loadGame);
        frame.add(Directions, BorderLayout.SOUTH);
        frame.setLocation(1920/2-frame.getWidth(), 1080/2-frame.getHeight());


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}

