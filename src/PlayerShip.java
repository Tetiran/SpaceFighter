import java.awt.*;
import java.util.LinkedList;

public class PlayerShip extends Ship {
    private static final String IMG_FILE = "files/playership.png";
    private final int WEAPON1COOLDOWN=20;
    private final int WPNX =60;
    private final int WPNY =30;
    private  int animCountdown=10;
    private int animPosition=0;



    private static Sprite spriteSheeet=new Sprite(IMG_FILE);

    public PlayerShip(int posx, int posy, int width, int height,
                      int health, int armor, int shield, int speed) {
        super(posx, posy, width,height, health, armor, shield,speed);

        this.setWidth(spriteSheeet.getGrid());
        this.setHeight(spriteSheeet.getGrid());
        this.setImg(spriteSheeet.getSprite(1));
    }

    @Override
    public void update() {
        super.update();
        StatusBar.setHealthmeter(this.getHealth());
        StatusBar.setShieldMeter(this.getShield());
        animCountdown--;
        if(animCountdown<=0) {
            animPosition++;
            if(animPosition<spriteSheeet.getSpriteNumber()-1) {
                this.setImg(spriteSheeet.getSprite(animPosition));
                System.out.println(animPosition);
            }
            else {
                animPosition=0;
                this.setImg(spriteSheeet.getSprite(animPosition));
            }
            animCountdown=10;
        }
    }
    public void updateCursor(Point p){
        if(p != null) {
            this.setAngle(Math.atan2(p.y - this.getPosy(), p.x - this.getPosx()));
        }
        else {
            this.setAngle(this.getAngle());
        }
    }

    @Override
    public LinkedList<Entity> mainAttack(){
        if(this.gatMainWeaponCooldown()>0){
            return null;
        }
        double angle=this.getAngle();
        LinkedList<Entity> adder =new LinkedList<Entity>();
        this.setMainWeaponCooldown(WEAPON1COOLDOWN);
        // so much god damm trig
        double newx1 = (WPNX)*Math.cos(angle) - (WPNY)*Math.sin(angle);
        double newy1 = (WPNX)*Math.sin(angle) + (WPNY)*Math.cos(angle);
        double newx2 = (WPNX)*Math.cos(angle) - (-WPNY)*Math.sin(angle);
        double newy2 = (WPNX)*Math.sin(angle) + (-WPNY)*Math.cos(angle);

        adder.add(new Laser(this.getPosx()+ newx1,
                this.getPosy()+newy1,32,32,100,20, this.getAngle()));
        adder.add(new Laser(this.getPosx()+ newx2,
                this.getPosy()+newy2,32,32,100,20, this.getAngle()));

        return adder;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

    }
}
