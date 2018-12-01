import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerShip extends Ship {
    public static final String IMG_FILE = "files/playership.png";

    private static BufferedImage img;

    public PlayerShip(int posx, int posy, int width, int height,
                      int health, int armor, int shield, int speed) {
        super(posx, posy, width,height, health, armor, shield,speed);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));

            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        this.setWidth(img.getWidth());
        this.setHeight(img.getHeight());
    }
    public void updateCursor(Point p){

        double angle = Math.atan2(p.y - this.getPosy(), p.x - this.getPosx());



        this.setAngle(angle);
    }
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1f,0f,0f,1f,this.getPosx(),this.getPosy());
        System.out.println(this.getHealth()/2);
        g.setColor(Color.ORANGE);
        at.translate(-this.getWidth()/2.0,-this.getHeight()/2.0);

        at.rotate(this.getAngle(), this.getWidth()/2.0, this.getHeight()/2.0);
        g2d.drawImage(img, at, null);
        g.drawRect((int) this.getPosx()-this.getWidth()/2, (int) this.getPosy()-this.getHeight()/2,30,30);
    }
}
