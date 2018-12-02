import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    private  static final int BAR_WIDTH=1920;
    public   static final int BAR_HEIGHT=50;
    private JLabel healthLabel;
    private JLabel shieldLable;



    public StatusBar(){
        healthLabel = new JLabel("Health");
        shieldLable = new JLabel("Shield");
        this.add(healthLabel,BorderLayout.EAST);
        this.add(shieldLable,BorderLayout.CENTER);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BAR_WIDTH, BAR_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
