import javax.swing.*;
import java.awt.event.*;

public class GameFrame extends JFrame {
    private static GameFrame current = null;

    public GameFrame(GameBackend gb) {
        setTitle("Star War Demo");
        current = this;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(Game.FRAME_WIDTH, Game.FRAME_HEIGHT);
        setLocation(0,0);
        setContentPane(new GamePanel(gb));
        setResizable(false);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        GameBackend.key_w = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        GameBackend.key_s = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        GameBackend.key_a = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        GameBackend.key_d = true;
                        break;
                    case KeyEvent.VK_Z:
                        GameBackend.key_z = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        GameBackend.key_esc = true;
                        break;
                    case KeyEvent.VK_ENTER:
                        GameBackend.key_enter = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        System.out.print("space");
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        GameBackend.key_w = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        GameBackend.key_s = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        GameBackend.key_a = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        GameBackend.key_d = false;
                        break;
                    case KeyEvent.VK_Z:
                        GameBackend.key_z = false;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        GameBackend.key_esc = false;
                        break;
                    case KeyEvent.VK_ENTER:
                        GameBackend.key_enter = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        System.out.print("space");
                        break;
                }
            }

        });
        setFocusable(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int value = JOptionPane.showConfirmDialog(null, "do you want to Leave?");
                if (value == JOptionPane.OK_OPTION) {
                    GameBackend.getCurrent().close();
                    GameFrame.getCurrent().close();
                    System.exit(0);

                } else {
                }
            }
        });
    }

    public static boolean outGameRange(Item item) {
        if(item.x<0||item.y<0||item.x>Game.FRAME_WIDTH ||item.y>Game.FRAME_HEIGHT){
            return true;
        }
        else return false;
    }

    void close() {
        GamePanel.getCurrent().close();
        current = null;
        dispose();
    }

    public static GameFrame getCurrent() {
        return current;
    }

}
