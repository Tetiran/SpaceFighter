import java.awt.*;

public class EnemyShip extends Ship {

    private static final String IMG_FILE = "files/EnemyShipGreen.png";
    private double accuracy = 0;
    private int mainWeaponCooldown;
    private final int WEAPON1COOLDOWN = 50;
    private final int WPNX = 80;
    private final int WPNY = 30;
    private double lastTheta = 0;
    private double followDis;


    public EnemyShip(int posx, int posy, int width, int height, int health, int armor, int shield, int speed) {
        super(posx, posy, width, height, health, armor, shield, speed, IMG_FILE);
        accuracy = (Math.random() * (1.0 / Game.difficulty) * .2 + .05);
        followDis = Math.random() * 400 + 500;
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
        //System.out.println(this.getAngle());
        this.setAngle(Math.atan2(player.y - this.getPosy(), player.x - this.getPosx()) + diff * 5);
        lastTheta = Math.atan2(player.y - this.getPosy(), player.x - this.getPosx());
        this.mainAttack();

        double distance = Point.distance(player.getX(), player.getY(), this.getPosx(), this.getPosy());
        if (distance > followDis) {
            this.setPosx(this.getPosx() + Math.cos(this.getAngle()) * this.getSpeed());
            this.setPosy(this.getPosy() + Math.sin(this.getAngle()) * this.getSpeed());
        } else {
            this.setPosx(this.getPosx() - Math.cos(this.getAngle()) * this.getSpeed());
            this.setPosy(this.getPosy() - Math.sin(this.getAngle()) * this.getSpeed());

        }

    }

    /*
    @Override
    public void mainAttack(){
        if(this.getMainCooldown()==0) {

            double angle = this.getAngle();
            this.setMainWeaponCooldown(WEAPON1COOLDOWN);

            // so much god damm trig I want to die
            double newx1 = (WPNX) * Math.cos(angle) - (WPNY) * Math.sin(angle);
            double newy1 = (WPNX) * Math.sin(angle) + (WPNY) * Math.cos(angle);
            double newx2 = (WPNX) * Math.cos(angle) - (-WPNY) * Math.sin(angle);
            double newy2 = (WPNX) * Math.sin(angle) + (-WPNY) * Math.cos(angle);

            GameField.addEntity(new Laser(this.getPosx() + newx1,
                    this.getPosy() + newy1, 32, 32, 10, 20, this.getAngle()));
            GameField.addEntity(new Laser(this.getPosx() + newx2,
                    this.getPosy() + newy2, 32, 32, 10, 20, this.getAngle()));
        }
    }
*/


    @Override
    public void draw(Graphics g) {
        super.draw(g);

    }
}
