import javafx.scene.shape.Ellipse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Asteroid extends Entity {
    private double angle;
    private double speed;
    private double rollAngleIncer;
    private double rollAngle;
    private BufferedImage img;
    private Sprite spriteSheeet;
    private String IMG_FILE= "files/asteroid large.png";
    private int animPosition=0;
    private int animCountdown=12;
    private boolean playDeath=false;
    private boolean chuckSummon=false;
    private Area hitRegion;


    Asteroid(double posx, double posy, int width, int height, double angle,
             double speed, double rollAngleIncer) {
        super(posx, posy, width, height);
        this.angle=angle;
        this.speed=speed;
        this.rollAngleIncer=rollAngleIncer;
        rollAngle=Math.random()*Math.PI*2;
        this.spriteSheeet =new Sprite(IMG_FILE);
        img = spriteSheeet.getSprite(0);
        this.setWidth(img.getWidth());
        this.setHeight(img.getHeight());
        hitRegion=ParseMeBaby.areaFromImg(img);
    }

    @Override
    public void update() {
        this.setPosx(this.getPosx()+Math.cos(angle)*speed);
        this.setPosy(this.getPosy()+Math.sin(angle)*speed);
        rollAngle+=rollAngleIncer;
        if(rollAngle>2*Math.PI){
            rollAngle=-2*Math.PI;
        }

        if(playDeath) {
            if(chuckSummon){
                int numChuncks=(int)(Math.random()*4+4);
                for (int i=0; i<numChuncks; i++){
                    deathSummon();

                }
                chuckSummon=false;
            }
            animCountdown--;
            if (animCountdown <= 0) {
                animPosition++;
                if (animPosition < spriteSheeet.getSpriteNumber() - 1) {
                    img = spriteSheeet.getSprite(animPosition);
                } else {
                    this.setMarkedForRemove(true);
                }
                animCountdown = 7;
            }
        }

    }


    private void deathSummon(){
        double posx=this.getPosx()+Math.random()*(this.getWidth()+2*50);
        double posy=this.getPosy()+Math.random()*(this.getHeight()+2*50);
        double angle=Math.random()*2*Math.PI;
        double rollangle=Math.random()*.2;
        double speed=Math.random()*6+5;
        AsteroidChunck asteroid= new AsteroidChunck(posx,posy,32,32,angle,speed,rollangle);
        GameField.addEntity(asteroid);
    }
    @Override
    public void damage(int damage){

        if(damage>0) {
            if(!playDeath) {
                chuckSummon = true;
            }
            playDeath = true;
        }
    }
    @Override
    public int getDamage(){
        if(playDeath){
            return 0;
        }
        return 50;
    }


    @Override
    public void draw(Graphics g) {
        super.draw(g);

        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1f,0f,0f,1f,this.getPosx(),this.getPosy());
        at.translate(-this.getWidth()/2.0,-this.getHeight()/2.0);
        at.rotate(rollAngle, this.getWidth()/2.0, this.getHeight()/2.0);
        g2d.drawImage(this.img, at, null);

        //Ellipse2D ellipse = new Ellipse2D.Double(this.getPosx()-this.getWidth()/2., this.getPosy()-this.getWidth()/2., this.getWidth(), this.getHeight());
        AffineTransform transform = new AffineTransform();
        transform.translate(this.getPosx(), this.getPosy());
        transform.rotate(angle);
        Area hitBound=hitRegion;
        hitBound=hitBound.createTransformedArea(at);
        this.setBounds(hitBound);

    }
}
