import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Bullet extends Item {
    int power;
    Timer move = null;

    public Bullet(int x, int y, int dx, int dy,int width,int height, ImageIcon icon, int power) {
        super(x, y, dx, dy,width,height, icon);
        this.power = power;
        move = new Timer();
        start();
    }

    public void start() {
        move.schedule(new TimerTask() {
            @Override
            public void run() {
                updata();
            }
        }, 0, Game.BULLET_SPEED);
    }

    public abstract void updata();

    void stop() {
        move.cancel();
    }

    @Override
    public void render(Graphics g) {
        int adjust_x = x - width / 2;
        int adjust_y = y - height / 2;
        g.drawImage(icon.getImage(), adjust_x, adjust_y, width, height, null);
    }

}
