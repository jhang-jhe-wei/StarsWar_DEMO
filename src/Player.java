import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Player extends Item{
    private int health;
    private boolean isDead;
    Timer move = null;
    Timer attack = null;
    List<Bullet> bullets = null;
    public Player(){
        this(Game.PLAY_INITT_X, Game.PLAY_INITT_Y, Game.PLAY_INITT_DX, Game.PLAY_INITT_DY, Game.PLAY_INITT_ICON);
    }
    public Player(int x, int y, int dx, int dy,ImageIcon icon) {
        super(x, y, dx, dy,icon);
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


    public void stop() {
        bullets.forEach(bullet -> {
            bullet.stop();
        });
        move.cancel();
        attack.cancel();
    }
    public void beAttacked(int damage){
        health-=damage;
        if(health<=0)
            isDead=true;
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
