import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Set;

public class Ship extends Entity {
    private int health;
    private int armor;
    private int shield;
    private boolean markedForDelete;
    private int animationState;
    private int speed;
    private double angle;
    private static final int RADIUS=100;
    private BufferedImage img;
    private int mainWeaponCooldown;

    public Ship(int posx, int posy, int width, int height, int health, int armor, int shield, int speed) {
        super(posx, posy, width, height);
        this.health=health;
        this.armor=armor;
        this.shield=shield;
        this.speed=speed;

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

    public int gatMainWeaponCooldown() {
        return this.mainWeaponCooldown;
    }

    public boolean damageShip(int damage){
        if(shield>0){
            shield= Math.max(0, shield-damage);
            return false;
        }
        health=Math.min(0, health/armor);
        return health <= 0;
    }
    void moveShip(int direction) {
        switch (direction) {
            case 1:
                this.setPosy(this.getPosy()-speed);
                break;
            case 2:
                this.setPosx(this.getPosx()+speed);
                break;
            case 3:
                this.setPosy(this.getPosy()+speed);
                break;
            case 4:
                this.setPosx(this.getPosx()-speed);
                break;
        }
    }

    public LinkedList<Entity> mainAttack(){
    return null;
    }

    @Override
    public void update() {
        if(mainWeaponCooldown>0){
            mainWeaponCooldown--;
        }

    }


    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1f,0f,0f,1f,this.getPosx(),this.getPosy());
        g.setColor(Color.ORANGE);
        at.translate(-this.getWidth()/2.0,-this.getHeight()/2.0);

        at.rotate(this.getAngle(), this.getWidth()/2.0, this.getHeight()/2.0);
        g2d.drawImage(this.img, at, null);

        g.drawOval((int) this.getPosx()-RADIUS/2, (int) this.getPosy()-RADIUS/2,RADIUS,RADIUS);
    }
}
