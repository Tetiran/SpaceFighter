import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Laser extends Entity {
    private double speed;
    private int damage;
    private double angle;
    private static final String IMG_FILE = "files/redlaser.png";
    private static int WIDTH=24;
    private static int HEIGHT=8;
    private int spawnCoolDown=60;




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
    public void damage(int damage){
        if(spawnCoolDown==0) {
            this.setMarkedForRemove(true);
        }
    }

    @Override
    public int getDamage(){
        if(spawnCoolDown==0) {
            return 10;
        }
        return 0;

    }

    @Override
    public void update(){
        if(spawnCoolDown>0){
            spawnCoolDown--;
        }
        this.setPosx(this.getPosx()+Math.cos(angle)*speed);
        this.setPosy(this.getPosy()+Math.sin(angle)*speed);

        //delete if out of bounds
        if(this.getPosx()>1500||this.getPosx()<-50){
            this.setMarkedForRemove(true);
        }
        if(this.getPosy()>1500||this.getPosy()<-50){
            this.setMarkedForRemove(true);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1f,0f,0f,1f,this.getPosx(),this.getPosy());
        at.translate(-this.getWidth()/2.0,-this.getHeight()/2.0);
        at.rotate(angle, this.getWidth()/2.0, this.getHeight()/2.0);
        g2d.drawImage(this.img, at, null);

        g.setColor(Color.ORANGE);
        Rectangle2D rect = new Rectangle2D.Double(-WIDTH/2., -HEIGHT/2., WIDTH, HEIGHT);
        AffineTransform transform = new AffineTransform();
        transform.translate(this.getPosx(), this.getPosy());
        transform.rotate(angle);

        Shape rotatedRect = transform.createTransformedShape(rect);
        this.setBounds(rotatedRect);
        //g2d.draw(rotatedRect);

    }
}
