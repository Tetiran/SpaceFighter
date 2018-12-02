import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Laser extends Entity {
    private double speed;
    private int damage;
    private double angle;
    private static final String IMG_FILE = "files/redlaser.png";


    private BufferedImage img;



    public Laser(double posx, double posy, int width, int height, int damage, double speed, double angle) {
        super(posx, posy, width, height);
        this.damage=damage;
        this.speed=speed;
        this.angle=angle;

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

    @Override
    public void update(){
        this.setPosx(this.getPosx()+Math.cos(angle)*speed);
        this.setPosy(this.getPosy()+Math.sin(angle)*speed);

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1f,0f,0f,1f,this.getPosx(),this.getPosy());
        at.translate(-this.getWidth()/2.0,-this.getHeight()/2.0);
        at.rotate(angle, this.getWidth()/2.0, this.getHeight()/2.0);
        g2d.drawImage(this.img, at, null);

    }
}
