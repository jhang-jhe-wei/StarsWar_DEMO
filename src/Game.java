import javax.swing.*;
import java.awt.*;

public class Game {
    public static final long BULLET_SPEED = 20;

    public static final int PLAYER_INITT_X = 100;
    public static final int PLAYER_INITT_Y = 500;
    public static final int PLAYER_INITT_DX = 10;
    public static final int PLAYER_INITT_DY =10;
    public static final ImageIcon PLAY_INITT_ICON = new ImageIcon("picture\\player.jpg");

    public static final int START = 0;
    public static final int HELP = 1;
    public static final int EXIT = 2;

    public static final int GAME_ALIVE = 0;
    public static final int GAME_EXIT = 1;

    public static final int MAX_ROUND = 2;
    public static final long MONSTER_MOVE_SPEED = 50;
    public static final long MONSTER_ATTACK_SPEED = 1000;
    public static final long REPAINT_SPEED = 20;
    public static final int MONSTER_BULLET_DELTA = 20;
    public static final ImageIcon MONSTER_BULLET_ICON = new ImageIcon("picture\\monster_bullet.png");
    public static final ImageIcon PLAY_BULLET_ICON = new ImageIcon("picture\\play_bullet.png");
    public static final ImageIcon MONSTER = new ImageIcon("picture\\monster.png");
    public static final int MONSTER_BULLET_POWER = 10;
    public static final int HEALTH_BAR_PADDING = 0;
    public static final int HEALTH_BAR_WIDTH = 50;
    public static final int HEALTH_BAR_HEIGHT = 10;
    public static final int PLAYER_INIT_HEALTH = 200;
    public static final int PLAYER_MAX_HEALTH = 200;
    public static final ImageIcon COVERIMAGE = new ImageIcon("picture\\title.gif");
    public static final long KEYBOARD_REPAINT_SPEED = 100;
    public static final ImageIcon BACKGROUP_IMAGE = new ImageIcon("picture\\backgrounp.gif");
    public static final int PLAYER_INIT_WIDTH = 150;
    public static final int PLAYER_INIT_HEIGHT = 150;
    public static final int PLAYER_BULLET_WIDTH = 30;
    public static final int PLAYER_BULLET_HEIGHT = 30;
    public static final int PLAYER_BULLET_DELTA = 20;
    public static final int MONSTER_BULLET_WIDTH = 50;
    public static final int MONSTER_BULLET_HEIGHT = 50;
    public static final long PLAYER_MOVE_SPEED = 12;
    public static final int MONSTER_MAX_HEALTH = 100;
    public static final long PLAYER_ATTACK_SPEED = 50;
    public static final int PLAYER_DIE = 1;
    public static final int PLAYER_ALIVE = 0;
    public static final int GAME_OVER = 1;
    public static final int GAME_FINISH = 0;
    public static final ImageIcon WINNER_IMAGE = new ImageIcon("picture\\win.jpg");
    public static final ImageIcon LOSER_IMAGE = new ImageIcon("picture\\gameover.jpg");
    public static final ImageIcon LOADING_IMAGE = new ImageIcon("picture\\pass.gif");
    public static final long LOADING_TIME = 2000;
    public static final ImageIcon TITLEIMAGE = new ImageIcon("picture\\titleword.png");
    public static final int MONSTER_INIT_WIDTH = 100;
    public static final int MONSTER_INIT_HEIGHT = 100;
    public static final int MONSTER_INIT_HEALTH = 50;
    public static int FRAME_WIDTH = 1280;
    public static int FRAME_HEIGHT = 720;

    public static void Play() {
        int gameState = GAME_ALIVE;
        createGame();
        while (gameState == GAME_ALIVE) {
            switch (ChooseGameMode()) {
                case EXIT:
                    gameState = closeGame();
                    break;
                case START:
                    int result=startGame();
                    gameState = endGame(result);
                    break;
                case HELP:
                    gameState = descriptionGame();
                    break;
            }
        }
    }

    private static int endGame(int result) {
        GamePanel.getCurrent().renderEnd(result);
        while(!GameBackend.key_enter)GameBackend.delay();
        GameBackend.delay();
        return GAME_ALIVE;
    }

    private static int descriptionGame() {
        GamePanel.getCurrent().renderHelp();
        while (true) {
            if (GameBackend.key_esc) {
                return GAME_ALIVE;
            }
            GameBackend.delay();
        }

    }

    private static int startGame() {
        int current_round = 0;
        while (current_round < MAX_ROUND) {
            GameBackend.getCurrent().gameLoading(current_round);
            if (GameBackend.getCurrent().gameStart() == PLAYER_DIE) return GAME_OVER;
            current_round++;
        }
        return GAME_FINISH;
    }

    private static int closeGame() {
        GameBackend.getCurrent().close();
        GamePanel.getCurrent().close();
        return GAME_EXIT;
    }

    private static void createGame() {
        new GameFrame(new GameBackend());
    }

    private static int ChooseGameMode() {
        return GameBackend.getCurrent().chooseMode();
    }

}
