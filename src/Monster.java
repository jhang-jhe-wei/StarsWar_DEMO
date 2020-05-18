import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

class Monster_Bullet extends Bullet {

    List<Monster_Bullet> monster_bullets;

    public Monster_Bullet(List<Monster_Bullet> monster_bullets, int x, int y, int dx, int dy,int width,int height, ImageIcon icon, int power) {
        super(x, y, dx, dy,width,height, icon, power);
        this.monster_bullets = monster_bullets;
    }

    @Override
    public void updata() {
        x += dx;
        y += dy;
        if (collide(GameBackend.getCurrent().getPlayer())) {
            GameBackend.getCurrent().getPlayer().beAttacked(power);
            stop();
            monster_bullets.remove(this);
        }
        if (GameFrame.outGameRange(this)) {
            stop();
            monster_bullets.remove(this);
        }
    }
}

public class Monster extends Item {
    List<Monster_Bullet> bullets = null;
    Player player;
    int health;
    Timer move = null;
    Timer attack = null;

    public Monster(int x, int y, int dx, int dy,int width,int height, int health, ImageIcon icon) {
        super(x, y, dx, dy,width,height, icon);
        this.health = health;
        player = GameBackend.getCurrent().getPlayer();
        bullets = Collections.synchronizedList(new ArrayList<Monster_Bullet>());
        move = new Timer();
        attack = new Timer();
    }

    private void attack() {
        addBullet(new Monster_Bullet(bullets, x, y, 0, Game.MONSTER_BULLET_DELTA, Game.MONSTER_BULLET_WIDTH,Game.MONSTER_BULLET_HEIGHT,Game.MONSTER_BULLET_ICON, Game.MONSTER_BULLET_POWER));
    }

    void updata() {
        x += dx;
        y += dy;
        if (collide(GameBackend.getCurrent().getPlayer())) {
            GameBackend.getCurrent().getPlayer().beAttacked(health);
            stop();
            GameBackend.getCurrent().removeMonster(this);
        }
        if (GameFrame.outGameRange(this)) {
            dx *= -1;
            dy *= -1;
        }
    }

    public List<Monster_Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Monster_Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Monster_Bullet bullet) {
        bullets.remove(bullet);
    }

    @Override
    public void render(Graphics g) {
        int adjust_x = x - width / 2;
        int adjust_y = y - height / 2;
        g.drawImage(icon.getImage(), adjust_x, adjust_y, width, height, null);
        g.setColor(Color.red);
        g.fillRect(x - width / 4, y + height/2 + Game.HEALTH_BAR_PADDING, width/2, Game.HEALTH_BAR_HEIGHT);
        g.setColor(Color.GREEN);
        g.fillRect(x - width / 4, y + height/2 + Game.HEALTH_BAR_PADDING, width/2 * health / Game.MONSTER_MAX_HEALTH, Game.HEALTH_BAR_HEIGHT);

        bullets.forEach(bullet -> {
            bullet.render(g);
        });
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

    public void beAttacked(int damage) {
        health -= damage;
        if (health <= 0) {
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
