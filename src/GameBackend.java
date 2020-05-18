
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameBackend {
    private static GameBackend current = null;
    private List<Monster> monsters = null;
    private static Player player = null;
    public static boolean key_w;
    public static boolean key_a;
    public static boolean key_s;
    public static boolean key_d;
    public static boolean key_esc;
    public static boolean key_z;
    public static boolean key_enter;

    public GameBackend() {
        current = this;
        monsters = Collections.synchronizedList(new ArrayList<Monster>());
        player=new Player();
    }


    public void gameLoading(int current_round) {
        monsters.forEach(monster -> {
            monster.stop();
        });
        player.bullets.clear();
        monsters.clear();
        GameParser.parseRound(current_round);
        player = new Player();
        GamePanel.getCurrent().renderLoad(current_round);
    }

    public int gameStart() {
        player.start();
        monsters.forEach(monster -> {monster.start();});
        GamePanel.getCurrent().renderGame();
        while(!monsters.isEmpty()){
            if(player.isDead())
                return Game.PLAYER_DIE;
        };
        return Game.PLAYER_ALIVE;
    }

    public static GameBackend getCurrent() {
        return current;
    }

    public Player getPlayer() {
        if (player == null)
            player = new Player();
        return player;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void addMonster(Monster monster) {
//        System.out.println("add...");
        monsters.add(monster);
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }

    public void close() {
        player.stop();
        monsters.forEach(monster -> {monster.stop();});
        current=null;
    }

    public int chooseMode() {
        int choose=Game.START;
        GamePanel.getCurrent().renderTitle(choose);
        while(!key_enter) {
            if(key_w||key_s){

                if (key_s)choose+=1;
                if (key_w)choose-=1;
                if(choose>2)choose=2;
                if(choose<0)choose=0;
                GamePanel.getCurrent().renderTitle(choose);
            }
            delay();
        }
        return choose;
    }
    public static void delay(){
        try {
            Thread.sleep(Game.KEYBOARD_REPAINT_SPEED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
