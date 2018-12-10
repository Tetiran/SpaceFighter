import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

class BoundaryGen {
    static Area areaFromImg(BufferedImage img){
        Area temp=new Area();
        int[][] holder = new int[img.getWidth()][img.getHeight()];

        for(int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                if(img.getRGB(i, j)!=0) {
                    temp.add(new Area(new Rectangle(i, j, 1, 1)));
                }
            }
        }

        return temp;
    }
}
