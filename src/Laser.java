import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Laser extends Entity {
    private double speed;
    private int damage;
    private double angle;
    private static final String IMG_FILE = "files/redlaser.png";
    private static int WIDTH = 24;
    private static int HEIGHT = 8;
    private int spawnCoolDown = 8;
    private boolean userLaser;
    private Area hitRegion;
    private AffineTransform at;



    private BufferedImage img;


    Laser(double posx, double posy, int width, int height, int damage, double speed, double angle, boolean userLaser) {
        super(posx, posy, width, height);
        this.damage = damage;
        this.speed = speed;
        this.angle = angle;
        this.userLaser=userLaser;
        at= new AffineTransform();
        at.rotate(angle, this.getWidth() / 2.0, this.getHeight() / 2.0);


        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));

            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        this.setWidth(img.getWidth());
        this.setHeight(img.getHeight());
        hitRegion= BoundaryGen.areaFromImg(img);
    }


    boolean getOwner() {
        return this.userLaser;
    }

    @Override
    public void damage(int damage) {
        if (spawnCoolDown == 0) {
            this.setMarkedForRemove(true);
        }
    }

    @Override
    public int getDamage() {
        if (spawnCoolDown == 0) {
            return 10;
        }
        return 0;

    }
    public Double getAngle() {
        return angle;

    }

    @Override
    public void update() {
        if (spawnCoolDown > 0) {
            spawnCoolDown--;
        }
        this.setPosx(this.getPosx() + Math.cos(angle) * speed);
        this.setPosy(this.getPosy() + Math.sin(angle) * speed);


    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1f, 0f, 0f, 1f,
                this.getPosx(), this.getPosy());
        at.rotate(angle, this.getWidth() / 2.0, this.getHeight() / 2.0);
        at.translate(-this.getWidth() / 2.0, -this.getHeight() / 2.0);


        g2d.drawImage(this.img, at, null);

        Area hitCalculate = hitRegion;
        hitCalculate = hitCalculate.createTransformedArea(at);

        this.setBounds(hitCalculate);


    }
}
