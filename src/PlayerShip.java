import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class PlayerShip extends Ship {
    private static final String IMG_FILE = "files/playership.png";
    private final int WEAPON1COOLDOWN=20;
    private final int WPNX =60;
    private final int WPNY =30;



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
        this.setImg(img);
    }

    public void updateCursor(Point p){
        if(p != null) {
            this.setAngle(Math.atan2(p.y - this.getPosy(), p.x - this.getPosx()));
        }
        else {
            this.setAngle(this.getAngle());
        }
    }

    @Override
    public LinkedList<Entity> mainAttack(){
        if(this.gatMainWeaponCooldown()>0){
            return null;
        }
        double angle=this.getAngle();
        LinkedList<Entity> adder =new LinkedList<Entity>();
        this.setMainWeaponCooldown(WEAPON1COOLDOWN);
        // so much god damm trig
        double newx1 = (WPNX)*Math.cos(angle) - (WPNY)*Math.sin(angle);
        double newy1 = (WPNX)*Math.sin(angle) + (WPNY)*Math.cos(angle);
        double newx2 = (WPNX)*Math.cos(angle) - (-WPNY)*Math.sin(angle);
        double newy2 = (WPNX)*Math.sin(angle) + (-WPNY)*Math.cos(angle);

        adder.add(new Laser(this.getPosx()+ newx1,
                this.getPosy()+newy1,32,32,100,20, this.getAngle()));
        adder.add(new Laser(this.getPosx()+ newx2,
                this.getPosy()+newy2,32,32,100,20, this.getAngle()));

        return adder;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

    }
}
