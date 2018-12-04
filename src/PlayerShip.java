import java.awt.*;
import java.awt.geom.Area;
import java.util.LinkedList;

public class PlayerShip extends Ship {
    private static final String IMG_FILE = "files/playership.png";
    private int shieldCoolDown=150;
    private int shieldMax;
    private int healthMax;


    public PlayerShip(int posx, int posy, int width, int height,
                      int health, int armor, int shield, int speed) {
        super(posx, posy, width, height, health, armor, shield, speed, IMG_FILE);
        this.shieldMax=shield;
        this.healthMax=health;



    }

    public void setShieldMax(int shieldMax) {
        this.shieldMax=shieldMax;

    }

    void moveShip(int direction) {

        switch (direction) {
            case 1:
                this.setPosy(this.getPosy() - this.getSpeed());
                break;
            case 2:
                this.setPosx(this.getPosx() + this.getSpeed());
                break;
            case 3:
                this.setPosy(this.getPosy() + this.getSpeed());
                break;
            case 4:
                this.setPosx(this.getPosx() - this.getSpeed());
                break;
        }
        if(this.getPosx()>1950){
            this.setPosx(1950);
        } else if(this.getPosx()<-30){
            this.setPosx(-30);
        }
        if(this.getPosy()>1070){
            this.setPosy(1070);
        } else if(this.getPosy()<-30){
            this.setPosy(-30);
        }

    }

    @Override
    public void damage(int damage) {
        if(damage>0) {
            shieldCoolDown=150;

            if (this.getShield() > 0) {
                this.setShield(Math.max(0, this.getShield() - damage));

            } else {

                this.setHealth((int) Math.max(0,
                        this.getHealth() - Math.ceil((double) damage / this.getArmor())));

            }
        }
    }

    @Override
    public void update() {
        super.update();
        if(shieldCoolDown>0) {
            shieldCoolDown--;
        } else if(this.getShield()<shieldMax){
            this.setShield(this.getShield()+1);
        }
        StatusBar.setHealthmeter(this.getHealth());
        StatusBar.setShieldMeter(this.getShield());
        StatusBar.setHealthmeterMax(this.healthMax);
        StatusBar.setShieldMeterMax(this.shieldMax);

    }

    public void updateCursor(Point p) {
        if (p != null) {
            this.setAngle(Math.atan2(p.y - this.getPosy(), p.x - this.getPosx()));
        } else {
            this.setAngle(this.getAngle());
        }
    }

    @Override
    public void mainAttack() {
        super.mainAttack();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

    }
}
