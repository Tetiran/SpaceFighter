import java.awt.*;

public class EnemyShip extends Ship{

    private static final String IMG_FILE = "files/EnemyShipGreen.png";

    public EnemyShip(int posx, int posy, int width, int height, int health, int armor, int shield, int speed) {
        super(posx, posy, width, height, health, armor, shield, speed,IMG_FILE);
    }




    @Override
    public void update() {
        super.update();
        this.mainAttack();

    }



    @Override
    public void draw(Graphics g) {
        super.draw(g);

    }
}
