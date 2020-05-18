import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.GenericArrayType;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
    private static GamePanel current = null;
    private GameBackend gb = null;
    private BufferedImage myImage;
    private Graphics graphics;
    Timer repaint = null;
    private int mode_choose;

    public GamePanel(GameBackend gb) {
        super();
        this.gb = gb;
        current = this;
        repaint = new java.util.Timer();
        myImage = new BufferedImage(Game.FRAME_WIDTH, Game.FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = myImage.getGraphics();
    }

    public static GamePanel getCurrent() {
        return current;
    }


    public void renderHelp() {
        repaint.cancel();
        repaint = new java.util.Timer();
        repaint.schedule(new TimerTask() {

            @Override
            public void run() {
                graphics.clearRect(0, 0, getWidth(), getHeight());
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, getWidth(), getHeight());
                graphics.drawImage(Game.COVERIMAGE.getImage(), 0, 0, Game.FRAME_WIDTH, Game.FRAME_HEIGHT, null);
                graphics.setColor(Color.GRAY);
                graphics.setFont(new Font("serif", Font.BOLD, 40));
                graphics.drawString("方向鍵控制上下左右", getWidth() / 2 - 180, getHeight() / 2 - 30);
                graphics.drawString("Z鍵攻擊", getWidth() / 2 - 80, getHeight() / 2 + 20);
                repaint();
            }
        }, 0, Game.REPAINT_SPEED);
    }

    public void renderTitle(int choose) {
        repaint.cancel();
        repaint = new java.util.Timer();
        repaint.schedule(new TimerTask() {

            @Override
            public void run() {
                graphics.clearRect(0, 0, getWidth(), getHeight());
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, getWidth(), getHeight());
                graphics.drawImage(Game.COVERIMAGE.getImage(), 0, 0, Game.FRAME_WIDTH, Game.FRAME_HEIGHT, null);
                graphics.drawImage(Game.TITLEIMAGE.getImage(), Game.FRAME_WIDTH/2-150, Game.FRAME_HEIGHT/2-150, 300,300, null);
                graphics.setColor(Color.blue);
                graphics.setFont(new Font("serif", Font.BOLD, 80));
                switch (choose) {
                    case 0:
                        graphics.drawString("START", getWidth() / 2 - 120, getHeight());
                        break;
                    case 1:
                        graphics.drawString("HELP", getWidth() / 2 - 80, getHeight());
                        break;
                    case 2:
                        graphics.drawString("EXIT", getWidth() / 2 - 80, getHeight());
                        break;
                }
                repaint();
            }
        }, 0, Game.REPAINT_SPEED);

    }

    public void renderGame() {
        repaint.cancel();
        repaint = new java.util.Timer();
        repaint.schedule(new TimerTask() {

            @Override
            public void run() {
                graphics.clearRect(0, 0, getWidth(), getHeight());
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, getWidth(), getHeight());
                graphics.drawImage(Game.BACKGROUP_IMAGE.getImage(), 0, 0, Game.FRAME_WIDTH, Game.FRAME_HEIGHT, null);
                gb.getPlayer().render(graphics);
                gb.getMonsters().forEach(monster -> {
                    monster.render(graphics);
                });
                repaint();
            }
        }, 0, Game.REPAINT_SPEED);
    }

    public void renderEnd(int result) {
        repaint.cancel();
        repaint = new java.util.Timer();
        repaint.schedule(new TimerTask() {

            @Override
            public void run() {
                graphics.clearRect(0, 0, getWidth(), getHeight());
                if (result == Game.GAME_FINISH)
                    graphics.drawImage(Game.WINNER_IMAGE.getImage(), 0, 0, Game.FRAME_WIDTH, Game.FRAME_HEIGHT, null);
                else
                    graphics.drawImage(Game.LOSER_IMAGE.getImage(), 0, 0, Game.FRAME_WIDTH, Game.FRAME_HEIGHT, null);
                repaint();
            }
        }, 0, Game.REPAINT_SPEED);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }

    void close() {
        repaint.cancel();
        current = null;
    }


    public void renderLoad(int current_round) {
        repaint.cancel();
        repaint = new java.util.Timer();
        repaint.schedule(new TimerTask() {

            @Override
            public void run() {
                graphics.clearRect(0, 0, getWidth(), getHeight());
                graphics.drawImage(Game.LOADING_IMAGE.getImage(), 0, 0, Game.FRAME_WIDTH, Game.FRAME_HEIGHT, null);
                graphics.setColor(Color.GRAY);
                graphics.setFont(new Font("serif", Font.BOLD, 80));
                graphics.drawString("ROUND : "+Integer.toString(current_round), getWidth() / 2 - 200, getHeight() / 2 + 20);
                repaint();
            }
        }, 0, Game.REPAINT_SPEED);
        try {
            Thread.sleep(Game.LOADING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
