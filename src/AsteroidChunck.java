import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class AsteroidChunck extends Entity {

    private double angle;
    private double speed;
    private double rollAngleIncer;
    private double rollAngle;
    private BufferedImage img;
    private Sprite spriteSheeet;
    private String IMG_FILE= "files/asteroid small.png";
    private int animPosition=0;
    private int animCountdown=12;
    private boolean playDeath=false;
    private Area hitRegion;

    AsteroidChunck(double posx, double posy, int width, int height, double angle, double speed, double rollAngleIncer) {
        super(posx, posy, width, height);
        this.angle=angle;
        this.speed=speed;
        this.rollAngleIncer=rollAngleIncer;
        rollAngle=Math.random()*Math.PI*2;
        this.spriteSheeet =new Sprite(IMG_FILE);
        img = spriteSheeet.getSprite(0);
        this.setWidth(img.getWidth());
        this.setHeight(img.getHeight());
        hitRegion= BoundaryGen.areaFromImg(img);
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
    @Override
    public void damage(int damage){
        if(damage>0) {
            playDeath = true;
        }
    }
    @Override
    public int getDamage(){
        if(playDeath){
            return 0;
        }
        return 10;
    }


    @Override
    public void draw(Graphics g) {
        super.draw(g);
        Graphics2D g2d = (Graphics2D) g.create();

        AffineTransform at = new AffineTransform(1f,0f,0f,1f,this.getPosx(),this.getPosy());
        at.translate(-this.getWidth()/2.0,-this.getHeight()/2.0);
        at.rotate(rollAngle, this.getWidth()/2.0, this.getHeight()/2.0);
        g2d.drawImage(this.img, at, null);
        AffineTransform transform = new AffineTransform();
        transform.translate(this.getPosx(), this.getPosy());
        transform.rotate(angle);
        Area hitBound=hitRegion;
        hitBound=hitBound.createTransformedArea(at);
        this.setBounds(hitBound);

    }
}
