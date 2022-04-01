import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GameFrame extends JFrame implements KeyListener {
    private Game game = new Game();
    private boolean gameStarted = false;

    // starting dimensions of window (pixels)
    public static final int WIDTH = 500, HEIGHT = 500, REFRESH = 140;

    // where the game objects are displayed
    private JPanel panel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            game.drawTheGame(g);
            // tried to get rid of some stuttering, changing REFRESH
            // improved this issue
            panel.getToolkit().sync();
        }
    };
    private Timer timer;// timer that runs the game

    public GameFrame(String string) {
        super(string);
        setUpStuff();
    }

    /**
     * Sets up the panel, timer, other initial objects in the game.
     * The Timer goes off every REFRESH milliseconds. Every time the
     * Timer goes off, the game is told to update itself and then the
     * view is refreshed.
     */
    private void setUpStuff() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.add(panel);
        this.pack();
        timer = new Timer(REFRESH, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                game.updateGame();
                if (game.gameOver) {
                    timer.stop();
                    return;
                }
                panel.repaint();
            }
        });
        // timer.start();
        this.setVisible(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!gameStarted) {
            gameStarted = true;
            timer.start();
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                game.upHit();
                break;
            case KeyEvent.VK_DOWN:
                game.downHit();
                break;
            case KeyEvent.VK_LEFT:
                game.ltHit();
                break;
            case KeyEvent.VK_RIGHT:
                game.rtHit();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
