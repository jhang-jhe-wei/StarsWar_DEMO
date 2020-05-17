import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame {
    private static GameFrame current = null;
    Timer repaint = null;

    public GameFrame(GameBackend gb) {
        setTitle("Star War Demo");
        current = this;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(Game.Frame_Width, Game.Frame_Height);
        setLocation(0, 0);
        setContentPane(new GamePanel(gb));
        setResizable(false);
        setVisible(true);
        repaint = new Timer();
        repaint.schedule(new TimerTask() {

            @Override
            public void run() {

                GamePanel.getCurrent().repaint();
            }
        }, 0, Game.REPAINT_SPEED);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int value = JOptionPane.showConfirmDialog(null, "do you want to Leave?");
                if (value == JOptionPane.OK_OPTION) {
                    GameFrame.getCurrent().close();
                    System.exit(0);

                } else {
                }
            }
        });
    }

    public static boolean outGameRange(Item item) {
        if(item.x<0||item.y<0||item.x>Game.Frame_Width||item.y>Game.Frame_Height){
            return true;
        }
        else return false;
    }

    void close() {
        GamePanel.getCurrent().close();
        repaint.cancel();
        current = null;
        dispose();
    }

    public static GameFrame getCurrent() {
        return current;
    }

}
