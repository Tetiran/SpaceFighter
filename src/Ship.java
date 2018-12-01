import java.awt.*;

public class Ship extends Entity {
    private int health;
    private int armor;
    private int shield;
    private boolean markedForDelete;
    private int animationState;
    private int speed;
    private double angle;

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

    public boolean dealDamage(int damage){
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

    @Override
    public void draw(Graphics g) {
    }
}
