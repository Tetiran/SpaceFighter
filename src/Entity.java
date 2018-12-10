import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    private double posx;
    private double posy;
    private int width;
    private int height;
    private boolean markedForRemove;
    private Shape bounds;
    private int damage;
    private Area intesrct;


    Entity(double posx, double posy, int width, int height) {
        this.posx = posx;
        this.posy = posy;

    }
    //setters
    public void setPosx(double posx) {
        this.posx = posx;

    }

    public void setPosy(double posy) {
        this.posy = posy;

    }
    public void setWidth(int width) {
        this.width = width;

    }
    public void setHeight(int height) {
        this.height = height;
    }

    void setMarkedForRemove(boolean x) {
        this.markedForRemove = x;

    }
    void setBounds(Shape bounds) {
        this.bounds = bounds;

    }
    public void setDamage(int damage){
        this.damage=damage;
    }

    boolean checkCollision(Entity check){
        if(check.getBounds() != null&& this.getBounds() != null) {
            Area checkArea = new Area(check.getBounds());
            checkArea.intersect(new Area(this.getBounds()));
            return !checkArea.isEmpty();
        }
        return false;

    }

    public void damage(int damage){
    }

    // getters
    public double getPosx() {
        return this.posx;
    }

    public double getPosy() {
        return this.posy;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    boolean getRemoveMark() {
        return this.markedForRemove;
    }

    private Shape getBounds() {

        return this.bounds;
    }
    public int getDamage(){
        return this.damage;
    }

    public abstract void update();

    public  void  draw(Graphics g){
/*
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.ORANGE);
        if(bounds!=null) {
            g2d.draw(bounds);
        }
*/
    }
}
