import javax.swing.*;

public class Game {
    public static final long BULLET_SPEED = 2000;

    public static final int PLAY_INITT_X = 100;
    public static final int PLAY_INITT_Y = 100;
    public static final int PLAY_INITT_DX = 10;
    public static final int PLAY_INITT_DY = 10;
    public static final ImageIcon PLAY_INITT_ICON = new ImageIcon();

    public static final int NORMAL = 0;
    public static final int HELP = 2;
    public static final int EXIT = 1;

    public static final int GAME_ALIVE = 0;
    public static final int GAME_EXIT = 1;

    public static final int MAX_ROUND = 2;
    public static final long MONSTER_MOVE_SPEED = 2000;
    public static final long MONSTER_ATTACK_SPEED = 500;
    public static final long REPAINT_SPEED = 120;
    public static final int MONSTER_BULLET_DELTA = 20;
    public static final ImageIcon MONSTER_BULLET_ICON = new ImageIcon();
    public static final int MONSTER_BULLET_POWER = 10;
    public static int Frame_Width = 1280;
    public static int Frame_Height = 720;

    public static void Play() {
        int gameState = GAME_ALIVE;
        createGame();
        while (gameState == GAME_ALIVE) {
            switch (ChooseGameMode()) {
                case EXIT:
                    gameState = closeGame();
                    break;
                case NORMAL:
                    gameState = startGame();
                    break;
                case HELP:
                    descriptionGame();
                    break;
            }
        }
    }

    private static void descriptionGame() {
        GamePanel.getCurrent().renderHelp();
    }

    private static int startGame() {
        int current_round = 0;
        while (current_round < MAX_ROUND) {
            GameBackend.getCurrent().gameLoading(current_round);
            if (GameBackend.getCurrent().gameStart() == EXIT) return GAME_EXIT;
        }
        return GAME_ALIVE;
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
