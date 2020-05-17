import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends Item {
    int power;
    Item creater;
    Timer move = null;

    public Bullet(Monster creater, int x, int y, int dx, int dy, ImageIcon icon, int power) {
        super(x, y, dx, dy, icon);
        this.creater = creater;
        this.power = power;
        move = new Timer();
        start();
    }

    public Bullet(Player creater, int x, int y, int dx, int dy, ImageIcon icon, int power) {
        super(x, y, dx, dy, icon);
        this.creater = creater;
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

    void updata() {
        x += dx;
        y += dy;
        if (creater.getClass().toString().equals(Monster.class.toString())) {
            if (collide(GameBackend.getCurrent().getPlayer())) {
                GameBackend.getCurrent().getPlayer().beAttacked(power);
                stop();
                GameBackend.getCurrent().getMonsters().get(GameBackend.getCurrent().getMonsters().indexOf(creater)).removeBullet(this);
            }
            if (GameFrame.outGameRange(this)) {
                stop();
                GameBackend.getCurrent().getMonsters().get(GameBackend.getCurrent().getMonsters().indexOf(creater)).removeBullet(this);
            }
        } else {
            GameBackend.getCurrent().getMonsters().forEach(monster -> {
                if (collide(monster)) {
                    monster.beAttacked(power);
                    stop();
                    GameBackend.getCurrent().getPlayer().removeBullet(this);
                }
                if (GameFrame.outGameRange(this)) {
                    stop();
                    GameBackend.getCurrent().getPlayer().removeBullet(this);
                }
            });

        }

    }

    void stop() {
        move.cancel();
    }

    @Override
    public void render(Graphics g) {

    }

}
