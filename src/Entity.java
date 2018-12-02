import java.awt.*;

public abstract class Entity {
    private double posx;
    private double posy;
    private int width;
    private int height;
    private boolean markedForRemove;

    public Entity(double posx, double posy, int width, int height) {
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

    public void setMarkedForRemove(boolean x) {
        this.markedForRemove = x;

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

    public boolean getRemoveMark() {
        return this.markedForRemove;
    }

    public abstract void update();

    public abstract void draw(Graphics g);
}
