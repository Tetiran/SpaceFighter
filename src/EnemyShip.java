import java.awt.*;

public class EnemyShip extends Ship {

    private static final String IMG_FILE = "files/EnemyShipGreen.png";
    private final int WEAPON1COOLDOWN = 50;
    private final int WPNX = 80;
    private final int WPNY = 30;
    private double accuracy = 0;
    private int mainWeaponCooldown;
    private double lastTheta = 0;
    private double followDis;


    public EnemyShip(int posx, int posy, int width, int height, int health, int armor, int shield, int speed) {
        super(posx, posy, width, height, health, armor, shield, speed, IMG_FILE);
        accuracy = (Math.random() * (1.0 / Game.difficulty) * .2 + .05);
        followDis = Math.random() * 400 + 300;
        this.setMainWeaponCooldown(WEAPON1COOLDOWN);
    }


    @Override
    public void update() {
        super.update();
        //update angle to face player
        Point.Double player = GameField.getPlayer();

        this.setAngle(Math.atan2(player.y - this.getPosy(), player.x - this.getPosx()));
        double diff = Math.abs(this.getAngle() - lastTheta);
        if (diff > Math.PI) {
            diff = Math.abs(diff - Math.PI * 2);
        }
        if (lastTheta < this.getAngle()) {
            diff = -diff;
        }

        this.setAngle(Math.atan2(player.y - this.getPosy(), player.x - this.getPosx()) + diff * 5);
        lastTheta = Math.atan2(player.y - this.getPosy(), player.x - this.getPosx());
        this.mainAttack();

        double distance = Point.distance(player.getX(), player.getY(), this.getPosx(), this.getPosy());
        if (distance - 100 > followDis) {
            this.setPosx(this.getPosx() + Math.cos(this.getAngle()) * this.getSpeed());
            this.setPosy(this.getPosy() + Math.sin(this.getAngle()) * this.getSpeed());
        } else if (distance + 100 < followDis) {
            this.setPosx(this.getPosx() - Math.cos(this.getAngle()) * this.getSpeed());
            this.setPosy(this.getPosy() - Math.sin(this.getAngle()) * this.getSpeed());

        }

    }


    @Override
    public void draw(Graphics g) {
        super.draw(g);

    }
}
