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


public class GameField extends JPanel {
    private static final String IMG_FILE = "files/background.jpg";
    private static final int INTERVAL = 15;
    private static int numEnemies;
    private static LinkedList<Entity> entitiestoadd = new LinkedList<>();
    private static BufferedImage img;
    private static PlayerShip player;
    private static boolean bossPresent;
    private static double bossHealth;
    private static double BossMax;
    private static String Bossname;
    private Timer timer;
    private static int score = 0;
    private Set<Character> pressed = new TreeSet<>();
    private static LinkedList<Entity> entities = new LinkedList<>();
    private StatusBar status;
    private double asteroidSpawn = 0;
    private double LARGEPROABAILITY = .3;
    private boolean endgame;
    private int endgameCount = 5;


    GameField() {




        this.setFocusable(true);


        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }


        this.setPreferredSize(new Dimension(1920, 1080));
    }

    static void setBoss(boolean boss, String name, int health, int healthmax) {
        bossPresent = boss;
        bossHealth = health;
        BossMax = healthmax;
        Bossname = name;

    }

    static void addEntity(Entity entity) {
        if (entity != null) {
            entitiestoadd.add(entity);
        }
    }

    static void setGameTime(int time) {
        EventScript.setTime(time);

    }
    static int getGameTime() {
        return EventScript.getTime();

    }

    static void setScore(int loadScore) {
        score=loadScore;

    }

    static int getScore() {
        return score;

    }
    static void newPlayer() {
        player = new PlayerShip(500.0, 500.0, 32, 32, 500, 1, 200, 7);
        entities.add(player);

    }

    static void setPlayer(PlayerShip playerz) {
        player=playerz;
        entities.add(player);

    }

    static LinkedList<Entity> getEntites() {
        return entities;

    }

    static Point.Double getPlayer() {
        return new Point.Double(player.getPosx(), player.getPosy());
    }

    void startGame() {
        //setBoss(true,"Space Kracken",500,500);
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                pressed.add(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressed.remove(e.getKeyChar());
            }

        });

    }

    void updateBar(StatusBar status) {
        this.status = status;
    }

    private void tick() {
        if (player.getHealth() <= 0) {
            timer.stop();
            final JFrame frame = new JFrame("");
            JOptionPane.showMessageDialog(frame, "I almost didn't program a way to die " +
                    "because it's so hard too...      Congratulation  ");
            Game.endGame();

        }
        endgame = EventScript.advanceScript();


        if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == null) {
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
        if (pressed.contains('\u001B')) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            timer.stop();
            pressed.remove('\u001B');
            final JFrame frame = new JFrame("Game Paused");
            final JPanel options = new JPanel();
            FlowLayout flow = new FlowLayout();
            options.setLayout(flow);
            JButton saveGame = new JButton("Save Game");
            saveGame.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FileMannager.saveSave();
                }
            });
            JButton quit = new JButton("Quit Game");
            quit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Game.endGame();
                }
            });
            options.add(saveGame);
            options.add(quit);
            frame.add(options);
            frame.pack();
            frame.setLocation(1920/2-frame.getWidth(), 1080/2-frame.getHeight());
            frame.setResizable(false);
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    timer.start();
                }
            });
        }

        entities.addAll(entitiestoadd);
        entitiestoadd.clear();


        Iterator<Entity> iter = entities.iterator();
        numEnemies = 0;
        while (iter.hasNext()) {
            Entity ent = iter.next();
            ent.update();
            if (ent instanceof EnemyShip) {
                numEnemies++;
            }


            for (Entity entity : entities) {

                if (!(ent instanceof Laser) || !(entity instanceof Laser)) {
                    if (ent != entity) {
                        //this increases speed by 100x
                        if ((Math.abs(ent.getPosx() - entity.getPosx()) < 200) &&
                                (Math.abs(ent.getPosy() - entity.getPosy()) < 200)) {
                            if (ent.checkCollision(entity)) {
                                ent.damage(entity.getDamage());
                                entity.damage(ent.getDamage());
                                if (ent instanceof Laser) {
                                    if (((Laser) ent).getOwner()) {
                                        score += ent.getDamage();
                                        StatusBar.setScore(score);
                                    }
                                } else if (entity instanceof Laser) {
                                    if (((Laser) entity).getOwner()) {
                                        score += entity.getDamage();
                                        StatusBar.setScore(score);
                                    }
                                }

                            }
                        }
                    }
                }
            }
            if (ent.getRemoveMark()) {
                iter.remove();
            } else if (ent.getPosx() > 3000 || ent.getPosx() < -1000) {
                iter.remove();
            } else if (ent.getPosy() > 2000 || ent.getPosy() < -1000) {
                iter.remove();
            }
        }
        if (endgame && (numEnemies == 0)) {
            endgameCount--;
            if (endgameCount == 0) {
                timer.stop();
                final JFrame frame = new JFrame("");
                JOptionPane.showMessageDialog(frame, "you won the game. What a god");
                FileMannager.deleteSave();
                Game.endGame();
            }
        }

        // asteroid random spawn
        asteroidSpawn--;
        if (asteroidSpawn <= 0) {
            if (Math.random() < LARGEPROABAILITY) {
                double posx = Math.random() * 1500 - 500;
                double posy = Math.random() * 1000 - 900;
                double angle = Math.random() * Math.PI;
                double rollangle = Math.random() * .05;
                double speed = Math.random() * 3 + 1;
                Asteroid asteroid = new Asteroid(posx, posy, 32, 32, angle, speed, rollangle);
                addEntity(asteroid);

            } else {
                double posx = Math.random() * 1500 - 500;
                double posy = Math.random() * 1000 - 900;
                double angle = Math.random() * Math.PI;
                double rollangle = Math.random() * .1;
                double speed = Math.random() * 8 + 2;
                AsteroidChunck asteroid = new AsteroidChunck(posx, posy, 32, 32, angle, speed, rollangle);
                addEntity(asteroid);

            }

            asteroidSpawn = 100 + Math.random() * 200;
        }

        player.updateCursor(this.getMousePosition());
        repaint();
        status.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img, 0, 0, this);
        player.draw(g);

        for (Entity entity : entities) {
            entity.draw(g);
            Graphics2D g2d = (Graphics2D) g.create();
        }
        if (bossPresent) {
            //draw bossbar
            Graphics2D g2d = (Graphics2D) g.create();
            Color barcolor = new Color(31, 49, 204);
            Font f = new Font("LucidaTypewriterBold", Font.PLAIN, 30);
            g2d.setFont(f);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(Bossname, (1920 / 2) - metrics.stringWidth(Bossname) / 2, 30);
            g.setColor(Color.BLACK);
            g.drawRect((1920 / 2) - 250, 39, 501, 11);
            g.setColor(barcolor);
            g.fillRect((1920 / 2) - 250 + 1, 40, (int) (500 * bossHealth / BossMax), 10);

        }

    }
}