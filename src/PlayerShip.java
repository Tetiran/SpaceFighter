import java.awt.*;
import java.awt.geom.Area;
import java.util.LinkedList;

public class PlayerShip extends Ship {
    private static final String IMG_FILE = "files/playership.png";


    public PlayerShip(int posx, int posy, int width, int height,
                      int health, int armor, int shield, int speed) {
        super(posx, posy, width, height, health, armor, shield, speed, IMG_FILE);

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

    }

    @Override
    public void damage(int damage) {

        if (this.getShield() > 0) {
            this.setShield(Math.max(0, this.getShield() - damage));

        } else {

            this.setHealth((int) Math.max(0,
                    this.getHealth() - Math.ceil((double) damage / this.getArmor())));

        }

    }

    @Override
    public void update() {
        super.update();

        StatusBar.setHealthmeter(this.getHealth());
        StatusBar.setShieldMeter(this.getShield());

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
