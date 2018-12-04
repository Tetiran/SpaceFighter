import java.awt.*;

public class Boss extends Entity {
    private int health;
    private int maxhealth;
    private String name;
    public Boss(double posx, double posy, int width, int height, int health, int maxhealth) {
        super(posx, posy, width, height);
        this.health=health;
        this.maxhealth=maxhealth;
    }

    @Override
    public void update() {
        GameField.setBoss(true,name,this.health, this.maxhealth);

    }
    public void setHealth(int health){
        this.health=health;

    }

    @Override
    public void draw(Graphics g) {

    }
}
