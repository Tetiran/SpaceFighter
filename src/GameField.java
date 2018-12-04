import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private static LinkedList<Entity> entitiestoadd = new LinkedList<>();


    private static BufferedImage img;
    private static PlayerShip player;
    private StatusBar status;
    private EnemyShip enemy;
    private double asteroidSpawn=0;
    private double LARGEPROABAILITY= .3;
    private static boolean bossPresent;
    private static double bossHealth;
    private static double BossMax;
    private static String Bossname;


    public static void setBoss(boolean boss,String name, int health, int healthmax){
        bossPresent=boss;
        bossHealth=health;
        BossMax=healthmax;
        Bossname=name;


    }

    public static void addEntity(Entity entity){
        if(entity != null) {
            entitiestoadd.add(entity);
        }

    }
    public void startGame(){
        setBoss(true,"Space Kracken",500,500);
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        addKeyListener( new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                pressed.add(e.getKeyChar());
            }
            @Override
            public  void keyReleased(KeyEvent e) {
                pressed.remove(e.getKeyChar());
            }

        });

    }

    public GameField() {



        player = new PlayerShip(500, 500,32,32,100,1,100, 5);
        enemy = new EnemyShip(300, 300,32,32,100,1,100, 5);

        entities.add(enemy);
        entities.add(player);

        this.setFocusable(true);




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
    public void updateBar(StatusBar status){
        this.status=status;
    }

    private void tick(){

        if(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()==null){
            pressed.clear();
        }

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
            player.mainAttack();
        }

        entities.addAll(entitiestoadd);
        entitiestoadd.clear();

        Iterator<Entity> iter = entities.iterator();
        while (iter.hasNext()) {
            Entity ent =iter.next();
            ent.update();
            for (Entity entity : entities) {
                if(ent != entity) {
                    if (!(ent instanceof Laser) || !(entity instanceof Laser)) {
                        if (ent.checkCollision(entity)) {
                            ent.damage(entity.getDamage());
                            entity.damage(ent.getDamage());
                        }
                    }
                }
            }
            if(ent.getRemoveMark()){
                iter.remove();
            } else if(ent.getPosx()>2000||ent.getPosx()<-1000){
                iter.remove();
            } else if(ent.getPosy()>2000||ent.getPosy()<-1000){
                iter.remove();
            }
        }

        // asteroid random spawn
        asteroidSpawn--;
        if(asteroidSpawn<=0){
            if (Math.random() <LARGEPROABAILITY) {
                double posx=Math.random()*1500-500;
                double posy=Math.random()*1000-900;
                double angle=Math.random()*Math.PI;
                double rollangle=Math.random()*.05;
                double speed=Math.random()*3+1;
                Asteroid asteroid= new Asteroid(posx,posy,32,32,angle,speed,rollangle);
                addEntity(asteroid);

            } else {
                double posx=Math.random()*1500-500;
                double posy=Math.random()*1000-900;
                double angle=Math.random()*Math.PI;
                double rollangle=Math.random()*.1;
                double speed=Math.random()*8+2;
                AsteroidChunck asteroid= new AsteroidChunck(posx,posy,32,32,angle,speed,rollangle);
                addEntity(asteroid);

            }

            asteroidSpawn=100+Math.random()*200;
        }

        player.updateCursor(this.getMousePosition());
        repaint();
        status.repaint();
    }
    public static Point.Double getPlayer(){
        return new Point.Double(player.getPosx(),player.getPosy());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img, 0, 0, this);
        player.draw(g);

        for (Entity entity : entities) {
            entity.draw(g);
            Graphics2D g2d = (Graphics2D) g.create();
            //g2d.draw(entity.getBounds());
        }
        if(bossPresent){
            //draw bossbar
            Graphics2D g2d = (Graphics2D) g.create();
            Color barcolor = new Color(31, 49, 204);
            Font f=new Font("LucidaTypewriterBold", Font.PLAIN, 30);
            g2d.setFont(f);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(Bossname,(1920/2)-metrics.stringWidth(Bossname)/2,30);
            g.setColor(Color.BLACK);
            g.drawRect((1920/2)-250, 39, 501, 11);
            g.setColor(barcolor);
            g.fillRect((1920/2)-250+1, 40, (int)( 500*bossHealth/BossMax), 10);

        }


    }
}