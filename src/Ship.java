import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Ship extends Entity {
    private int health;
    private int armor;
    private int shield;
    private int animationState;
    private int speed;
    private double angle;
    private static final int RADIUS=100;
    private BufferedImage img;
    private static BufferedImage shieldimg;
    private int mainWeaponCooldown;
    private final int WEAPON1COOLDOWN=20;
    private final int WPNX =80;
    private final int WPNY =30;
    private  int animCountdown=8;
    private int animPosition=0;
    private Sprite spriteSheeet;
    private String SHIELDFILE="files/Shield.png";

    public Ship(int posx, int posy, int width, int height, int health, int armor, int shield, int speed, String IMG_FILE) {
        super(posx, posy, width, height);
        this.health=health;
        this.armor=armor;
        this.shield=shield;
        this.speed=speed;
        this.spriteSheeet =new Sprite(IMG_FILE);
        this.setWidth(spriteSheeet.getGrid());
        this.setHeight(spriteSheeet.getGrid());
        this.setImg(spriteSheeet.getSprite(1));

        try {
            shieldimg = ImageIO.read(new File(SHIELDFILE));

        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

    }

    //setters
    public void setHealth(int health) {
        this.health = health;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setShield(int shield){
        this.shield=shield;
    }

    public void setAngle(double angle){
        this.angle=angle;
    }

    public void setImg(BufferedImage img){
        this.img=img;
    }

    public void setMainWeaponCooldown(int mainWeaponCooldown){
        this.mainWeaponCooldown=mainWeaponCooldown;
    }

    // getters
    public int getHealth() {
        return this.health;
    }

    public int getArmor() {
        return this.armor;
    }

    public int getShield() {
        return this.shield;
    }

    public double getAngle() {
        return this.angle;
    }

    public int getMainCooldown() {
        return this.mainWeaponCooldown;
    }

    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void damage(int damage){
        if(shield>0){
            this.shield= Math.max(0, shield-damage);
        }
        this.health = (int) Math.max(0, (health-Math.ceil((double)damage/armor)));

        this.setMarkedForRemove(health <= 0);
    }
    @Override
    public int getDamage(){
        return 10;
    }


    public void mainAttack(){
        if(this.getMainCooldown()==0) {

            double angle = this.getAngle();
            LinkedList<Entity> adder = new LinkedList<Entity>();
            this.setMainWeaponCooldown(WEAPON1COOLDOWN);

            // so much god damm trig I want to die
            double newx1 = (WPNX) * Math.cos(angle) - (WPNY) * Math.sin(angle);
            double newy1 = (WPNX) * Math.sin(angle) + (WPNY) * Math.cos(angle);
            double newx2 = (WPNX) * Math.cos(angle) - (-WPNY) * Math.sin(angle);
            double newy2 = (WPNX) * Math.sin(angle) + (-WPNY) * Math.cos(angle);

            GameField.addEntity(new Laser(this.getPosx() + newx1,
                    this.getPosy() + newy1, 32, 32, 100, 5, this.getAngle()));
            GameField.addEntity(new Laser(this.getPosx() + newx2,
                    this.getPosy() + newy2, 32, 32, 100, 5, this.getAngle()));
        }
    }

    @Override
    public void update() {
        if(mainWeaponCooldown>0){
            mainWeaponCooldown--;
        }
        animCountdown--;
        if(animCountdown<=0) {
            animPosition++;
            if(animPosition<spriteSheeet.getSpriteNumber()-1) {
                this.setImg(spriteSheeet.getSprite(animPosition));
            }
            else {
                animPosition=0;
                this.setImg(spriteSheeet.getSprite(animPosition));
            }
            animCountdown=10;
        }

    }


    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1f,0f,0f,1f,this.getPosx(),this.getPosy());

        at.translate(-this.getWidth()/2.0,-this.getHeight()/2.0);

        at.rotate(this.getAngle(), this.getWidth()/2.0, this.getHeight()/2.0);
        g2d.drawImage(this.img, at, null);
        g.setColor(Color.ORANGE);
        Ellipse2D ellipse = new Ellipse2D.Double(this.getPosx()-RADIUS/2.0, this.getPosy()-RADIUS/2.0,RADIUS,RADIUS);
        AffineTransform ats = new AffineTransform(1f,0f,0f,1f,this.getPosx(),this.getPosy());
        ats.translate(-shieldimg.getWidth()/2.0,-shieldimg.getHeight()/2.0);
        //g2d.draw(ellipse);
        this.setBounds(ellipse);
        if (this.getShield()>0) {
            float opacity = 0.5f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.drawImage(shieldimg,ats,null);
        }
    }
}
