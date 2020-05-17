import javax.swing.*;

public class GamePanel extends JPanel {
    private static GamePanel current=null;

    public GamePanel(GameBackend gb) {

    }

    public static GamePanel getCurrent() {
        return current;
    }

    public void close() {
        current=null;
    }

    public void renderHelp() {
    }
}
