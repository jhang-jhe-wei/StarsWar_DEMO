import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

class Player_Bullet extends Bullet {

    List<Player_Bullet> player_bullets;

    public Player_Bullet(List<Player_Bullet> player_bullets, int x, int y, int dx, int dy,int width,int height, ImageIcon icon, int power) {
        super(x, y, dx, dy,width,height, icon, power);
        this.player_bullets = player_bullets;
    }

    @Override
    public void updata() {
        x += dx;
        y -= dy;
        try {
            GameBackend.getCurrent().getMonsters().forEach(monster -> {
                if (collide(monster)) {
                    monster.beAttacked(power);
                    stop();
                    player_bullets.remove(this);
                }
                if (GameFrame.outGameRange(this)) {
                    stop();
                    player_bullets.remove(this);
                }
            });
        } catch (ConcurrentModificationException e) {
        }
        catch (NullPointerException e){}
    }
}

public class Player extends Item {
    private int health;
    private boolean isDead;
    Timer move = null;
    Timer attack = null;
    List<Player_Bullet> bullets = null;

    public Player() {
        this(Game.PLAYER_INITT_X, Game.PLAYER_INITT_Y, Game.PLAYER_INITT_DX, Game.PLAYER_INITT_DY,Game.PLAYER_INIT_WIDTH,Game.PLAYER_INIT_HEIGHT, Game.PLAY_INITT_ICON);
    }

    public Player(int x, int y, int dx, int dy,int width,int height, ImageIcon icon) {
        super(x, y, dx, dy,width,height, icon);
        health = Game.PLAYER_INIT_HEALTH;
        bullets = Collections.synchronizedList(new ArrayList<Player_Bullet>());
        move = new Timer();
        attack = new Timer();
    }

    private void attack() {
        if (GameBackend.key_z) {
            addBullet(new Player_Bullet(bullets, x, y, 0, Game.PLAYER_BULLET_DELTA, Game.PLAYER_BULLET_WIDTH, Game.PLAYER_BULLET_HEIGHT, Game.PLAY_BULLET_ICON, Game.MONSTER_BULLET_POWER));
        }
    }

    void updata() {
        if (GameBackend.key_d&x<Game.FRAME_WIDTH-width/4)
            x += dx;
        if (GameBackend.key_a&&x>0+width/4)
            x -= dx;
        if (GameBackend.key_w&&y>0+height/4)
            y -= dy;
        if (GameBackend.key_s&&y<Game.FRAME_HEIGHT-height/4)
            y += dy;
    }

    public List<Player_Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Player_Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Player_Bullet bullet) {
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
        g.fillRect(x - width / 4, y + height/2 + Game.HEALTH_BAR_PADDING, width/2 * health / Game.PLAYER_MAX_HEALTH, Game.HEALTH_BAR_HEIGHT);

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
        }, 0, Game.PLAYER_MOVE_SPEED);
        attack.schedule(new TimerTask() {
            @Override
            public void run() {
                attack();
            }
        }, 0, Game.PLAYER_ATTACK_SPEED);
    }


    public void stop() {
        bullets.forEach(bullet -> {
            bullet.stop();
        });
        move.cancel();
        attack.cancel();
    }

    public void beAttacked(int damage) {
        health -= damage;
        if (health <= 0)
            isDead = true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

}
