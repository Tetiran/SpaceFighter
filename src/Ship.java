import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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
    private static final int RADIUS = 100;
    private BufferedImage img;
    private static BufferedImage shieldimg;
    private int mainWeaponCooldown;
    private final int WEAPON1COOLDOWN = 20;
    private final int WPNX = 80;
    private final int WPNY = 30;
    private int animCountdown = 8;
    private int animPosition = 0;
    private Sprite spriteSheeet;
    private String SHIELDFILE = "files/Shield.png";
    private Area hitRegion;

    Ship(int posx, int posy, int width, int height, int health, int armor, int shield, int speed, String IMG_FILE) {
        super(posx, posy, width, height);
        this.health = health;
        this.armor = armor;
        this.shield = shield;
        this.speed = speed;
        this.spriteSheeet = new Sprite(IMG_FILE);
        this.setWidth(spriteSheeet.getGrid());
        this.setHeight(spriteSheeet.getGrid());
        this.setImg(spriteSheeet.getSprite(1));
        hitRegion=ParseMeBaby.areaFromImg(img);


        try {
            shieldimg = ImageIO.read(new File(SHIELDFILE));

        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

    }

    //setters
    void setHealth(int health) {
        this.health = health;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    void setShield(int shield) {
        this.shield = shield;
    }

    void setAngle(double angle) {
        this.angle = angle;
    }

    private void setImg(BufferedImage img) {
        this.img = img;
    }

    void setMainWeaponCooldown(int mainWeaponCooldown) {
        this.mainWeaponCooldown = mainWeaponCooldown;
    }

    // getters
    int getHealth() {
        return this.health;
    }

    int getArmor() {
        return this.armor;
    }

    int getShield() {
        return this.shield;
    }

    double getAngle() {
        return this.angle;
    }

    int getMainCooldown() {
        return this.mainWeaponCooldown;
    }

    int getSpeed() {
        return this.speed;
    }

    @Override
    public void damage(int damage) {
        if (shield > 0) {
            this.shield = Math.max(0, shield - damage);
        } else {
            this.health = (int) Math.max(0, (health - Math.ceil((double) damage / armor)));
        }
        this.setMarkedForRemove(health <= 0);
    }

    @Override
    public int getDamage() {
        return 10;
    }


    public void mainAttack() {
        if (this.getMainCooldown() == 0) {

            double angle = this.getAngle();
            this.setMainWeaponCooldown(WEAPON1COOLDOWN);

            // so much god damm trig I want to die
            double newx1 = (WPNX) * Math.cos(angle) - (WPNY) * Math.sin(angle);
            double newy1 = (WPNX) * Math.sin(angle) + (WPNY) * Math.cos(angle);
            double newx2 = (WPNX) * Math.cos(angle) - (-WPNY) * Math.sin(angle);
            double newy2 = (WPNX) * Math.sin(angle) + (-WPNY) * Math.cos(angle);

            GameField.addEntity(new Laser(this.getPosx() + newx1,
                    this.getPosy() + newy1, 32, 32, 10, 20, this.getAngle(), false));
            GameField.addEntity(new Laser(this.getPosx() + newx2,
                    this.getPosy() + newy2, 32, 32, 10, 20, this.getAngle(), false));
        }
    }

    @Override
    public void update() {
        if (mainWeaponCooldown > 0) {
            mainWeaponCooldown--;
        }
        animCountdown--;
        if (animCountdown <= 0) {
            animPosition++;
            if (animPosition < spriteSheeet.getSpriteNumber() - 1) {
                this.setImg(spriteSheeet.getSprite(animPosition));
            } else {
                animPosition = 0;
                this.setImg(spriteSheeet.getSprite(animPosition));
            }
            animCountdown = 10;
        }

    }


    @Override
    public void draw(Graphics g) {
        super.draw(g);
        Area hitCalculate=hitRegion;
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1f, 0f, 0f, 1f, this.getPosx(), this.getPosy());
        AffineTransform ats = new AffineTransform(at);
        AffineTransform atz= new AffineTransform(at);
        at.translate(-this.getWidth() / 2.0, -this.getHeight() / 2.0);

        at.rotate(this.getAngle(), this.getWidth() / 2.0, this.getHeight() / 2.0);
        g2d.drawImage(this.img, at, null);


        ats.translate(-shieldimg.getWidth() / 2.0, -shieldimg.getHeight() / 2.0);


        atz.rotate(this.getAngle(), this.getWidth() / 2.0, this.getHeight() / 2.0);

        hitCalculate=hitCalculate.createTransformedArea(atz);
        AffineTransform atq= new AffineTransform(1f, 0f, 0f, 1f, -this.getWidth() / 2.0, -this.getHeight() / 2.0);
        hitCalculate=hitCalculate.createTransformedArea(atq);
        Shape s =hitCalculate;




        this.setBounds(s);
        if (this.getShield() > 0) {
            float opacity = 0.3f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.drawImage(shieldimg, ats, null);
        }
    }
}
