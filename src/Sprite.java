import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Sprite {

    private BufferedImage spriteSheet;

    Sprite(String file) {
        try {
            this.spriteSheet = ImageIO.read(new File(file));


        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

    }

    int getSpriteNumber() {
        return spriteSheet.getHeight() / spriteSheet.getWidth();
    }

    int getGrid() {
        return spriteSheet.getWidth();
    }

    BufferedImage getSprite(int position) {
        return this.spriteSheet.getSubimage(0, this.getGrid() * position, this.getGrid(), this.getGrid());
    }

}