
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameBackend {
    private static GameBackend current = null;
    private List<Monster> monsters = null;
    private static Player player = null;
    public boolean key_w;
    public boolean key_a;
    public boolean key_s;
    public boolean key_d;
    public boolean key_esc;
    public boolean key_space;
    public boolean key_enter;

    public GameBackend() {
        current = this;
        monsters = Collections.synchronizedList(new ArrayList<Monster>());

        GameFrame.getCurrent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        key_w = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        key_s = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        key_a = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        key_d = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        key_space = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        key_esc = true;
                        break;
                    case KeyEvent.VK_ENTER:
                        key_enter = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        key_w = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        key_s = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        key_a = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        key_d = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        key_space = false;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        key_esc = false;
                        break;
                    case KeyEvent.VK_ENTER:
                        key_enter = false;
                        break;
                }
            }
        });
        GameFrame.getCurrent().setFocusable(true);
    }


    public void gameLoading(int current_round) {
        monsters.forEach(monster -> {monster.stop();});
        monsters.clear();
        GameParser.parseRound(current_round);
        player=new Player();
    }

    public int gameStart() {
        return Game.NORMAL;
    }

    public static GameBackend getCurrent() {
        return current;
    }

    public Player getPlayer() {
        if(player==null)
            player=new Player();
        return player;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }

    public void close() {
    }

    public int chooseMode() {
        return 0;
    }
}
