import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

// main game state
public class GameField extends  JPanel{
    private int score;
    private static final String IMG_FILE = "files/background.jpg";
    private static final int INTERVAL = 15;
    private Set<Character> pressed = new TreeSet<>();
    private LinkedList<Entity> entities = new LinkedList<>();


    private static BufferedImage img;
    private PlayerShip player;

    public GameField() {
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();


        player = new PlayerShip(500, 500,32,32,100,100,100, 5);
        setFocusable(true);


        addKeyListener( new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pressed.add(e.getKeyChar());
            }
            @Override
            public  void keyReleased(KeyEvent e) {
                pressed.remove(e.getKeyChar());
            }

        });

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }




        this.setPreferredSize( new Dimension( 1920, 1080 ) );
    }

    private void tick(){
        if (pressed.contains('w')) {
            player.moveShip(1);
        }
        if (pressed.contains('d')) {
            player.moveShip(2);
        }
        if (pressed.contains('s')) {
            player.moveShip(3);
        }
        if (pressed.contains('a')) {
            player.moveShip(4);
        }
        if (pressed.contains(' ')) {
            LinkedList<Entity> ent =player.mainAttack();
            if (ent != null){
                entities.addAll(ent);
            }

        }

        Iterator<Entity> iter = entities.iterator();
        while (iter.hasNext()) {
            Entity ent =iter.next();
            ent.update();
            if(ent.getRemoveMark()){
                iter.remove();
            }
        }
        player.update();
        player.updateCursor(this.getMousePosition());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
        player.draw(g);
        for (Entity entity : entities) {
            entity.draw(g);
        }

    }
}
