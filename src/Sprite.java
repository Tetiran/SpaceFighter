import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

        private  BufferedImage spriteSheet;

        public Sprite(String file) {
            try {
                    this.spriteSheet = ImageIO.read(new File(file));


            } catch (IOException e) {
                System.out.println("Internal Error:" + e.getMessage());
            }
        }

        public int getSpriteNumber(){
            return spriteSheet.getHeight()/spriteSheet.getWidth();
        }

        public int getGrid(){
        return spriteSheet.getWidth();
        }

        public BufferedImage getSprite(int position) {
            return this.spriteSheet.getSubimage(0, this.getGrid()*position, this.getGrid(), this.getGrid());
        }

}

