import javax.swing.*;
import java.awt.*;

public abstract class Item {
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;
    protected ImageIcon icon;

    public Item(int x, int y, int dx, int dy,ImageIcon icon) {

        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.width = icon.getIconWidth();
        this.height = icon.getIconHeight();
        this.icon = icon;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean collide(Item item){
        double x_distance=(double) x-(double)item.x;
        double y_distance=(double)y-(double)item.y;
        double distance=Math.sqrt(Math.pow(x_distance,2)+Math.pow(y_distance,2));
        int max_width=Math.max(width,item.width);
        int max_height=Math.max(height,item.height);
        int radius=Math.min(max_width,max_height);
        if(distance<radius)
        return true;
        else return false;
    }

    public abstract void render(Graphics g);

    @Override
    public String toString() {
        return "Item{" +
                "x=" + x +
                ", y=" + y +
                ", dx=" + dx +
                ", dy=" + dy +
                ", width=" + width +
                ", height=" + height +
                ", icon=" + icon +
                '}';
    }

}
