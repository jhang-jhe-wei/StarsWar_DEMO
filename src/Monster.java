import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

public class Monster extends Item {
    List<Bullet> bullets = null;
    Player player;
    int health;
    Timer move = null;
    Timer attack = null;

    public Monster(int x, int y, int dx, int dy, int health, ImageIcon icon) {
        super(x, y, dx, dy, icon);
        this.health = health;
        player = GameBackend.getCurrent().getPlayer();
        bullets = Collections.synchronizedList(new ArrayList<Bullet>());
        move = new Timer();
        attack = new Timer();
    }

    private void attack() {
        addBullet(new Bullet(this, x, y, Game.MONSTER_BULLET_DELTA, Game.MONSTER_BULLET_DELTA, Game.MONSTER_BULLET_ICON, Game.MONSTER_BULLET_POWER));
    }

    void updata() {
        x += dx;
        y += dy;
        if (collide(GameBackend.getCurrent().getPlayer())) {
            GameBackend.getCurrent().getPlayer().beAttacked(health);
            stop();
            GameBackend.getCurrent().removeMonster(this);
        }
        if(GameFrame.outGameRange(this)){
            dx*=-1;
            dy*=-1;
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    @Override
    public void render(Graphics g) {

    }

    public void start() {
        move.schedule(new TimerTask() {
            @Override
            public void run() {
                updata();
            }
        }, 0, Game.MONSTER_MOVE_SPEED);
        attack.schedule(new TimerTask() {
            @Override
            public void run() {
                attack();
            }
        }, 0, Game.MONSTER_ATTACK_SPEED);
    }

    public void beAttacked(int damage){
        health-=damage;
        if(health<=0) {
            stop();
            GameBackend.getCurrent().removeMonster(this);
        }
    }

    public void stop() {
        bullets.forEach(bullet -> {
            bullet.stop();
        });
        move.cancel();
        attack.cancel();
    }

}
