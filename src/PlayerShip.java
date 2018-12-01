import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerShip extends Ship {
    public static final String IMG_FILE = "files/playership.png";

    private static BufferedImage img;

    public PlayerShip(int posx, int posy, int width, int height,
                      int health, int armor, int shield, int speed) {
        super(posx, posy, width,height, health, armor, shield,speed);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPosx(), this.getPosy(), null);
    }
}
