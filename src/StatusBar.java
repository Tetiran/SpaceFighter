import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StatusBar<setShieldMeter> extends JPanel {
    private  static final int BAR_WIDTH=1920;
    public   static final int BAR_HEIGHT=30;
    private JLabel healthLabel;
    private JLabel shieldLable;
    private static int healthmeter;
    private static int shieldMeter;
    private static int cash;
    private static BufferedImage healthImg;
    private static final String HEALTHIMG_FILE = "files/Health bar.png";
    private static BufferedImage ShieldImg;
    private static final String SHIELDIMG_FILE = "files/Shield bar.png";




    public StatusBar(){

        try {
            if (healthImg == null) {
                healthImg = ImageIO.read(new File(HEALTHIMG_FILE));

            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        try {
            if (ShieldImg == null) {
                ShieldImg = ImageIO.read(new File(SHIELDIMG_FILE));

            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

    }
    public static void setHealthmeter(int healthmeter){
        StatusBar.healthmeter =healthmeter;
    }
    public static void setShieldMeter(int ShieldMeter){
        StatusBar.shieldMeter =ShieldMeter;
    }
    public static void setCash(int cash){
        StatusBar.cash =cash;
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BAR_WIDTH, BAR_HEIGHT);
    }

    @Override
        public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color barcolor = new Color((100-healthmeter)*(255/100), healthmeter*(255/100), 0);
        g.drawImage(healthImg,10,10,null);
        g.setColor(Color.BLACK);
        g.drawRect(79,9,501,11);
        g.setColor(barcolor);
        g.fillRect(80,10,5*healthmeter,10);

        Color shieldColor = new Color(70, 150, 180);
        g.drawImage(ShieldImg,521+79,10,null);
        g.setColor(Color.BLACK);
        g.drawRect(521+79*2,9,501,11);
        g.setColor(shieldColor);
        g.fillRect(521+159,10,5*shieldMeter,10);

    }
}
