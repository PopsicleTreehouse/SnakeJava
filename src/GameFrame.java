import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

public class GameFrame extends JFrame implements KeyListener {
    private Game game = new Game();
    private Timer timer;
    private boolean gameStarted = false;
    private final File highscoresFile = new File("./highscores.csv");
    public static final int WIDTH = 500, HEIGHT = 500, REFRESH = 140;

    // where the game objects are displayed
    private JPanel panel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (game.gameOver) {
                g.setFont(g.getFont().deriveFont(20.0f));
                g.drawString("Highscores:", 10, 20);
                String[] highscores = getHighscores();
                for (int i = 0; i < highscores.length; i++) {
                    String highscore = highscores[i];
                    if (highscore == null)
                        break;
                    g.drawString(String.valueOf(i + 1) + ". " + highscore, 10, i * 60 + 80);
                }
            } else
                game.drawTheGame(g);
            panel.getToolkit().sync();
        }
    };

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
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        timer = new Timer(REFRESH, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                game.updateGame();
                if (game.gameOver) {
                    promptForHighscore();
                    timer.stop();
                    return;
                }
                panel.repaint();
            }
        });
        setVisible(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(this);
    }

    private void promptForHighscore() {
        String name = JOptionPane.showInputDialog("Enter your name");
        addHighscoreToFile(game.points, name);
        panel.repaint();
    }

    private String[] getHighscores() {
        String[] ret = new String[3];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(highscoresFile));
            String line = reader.readLine();
            int index = 0;
            while (line != null && index < 3) {
                String data = String.join(" ", line.split(","));
                ret[index++] = data;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private void addHighscoreToFile(int score, String name) {
        if (name == null)
            return;
        try {
            highscoresFile.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(highscoresFile));
            String line = reader.readLine();
            ArrayList<String> allLines = new ArrayList<>();
            int index = 0;
            while (line != null) {
                allLines.add(line);
                String[] data = line.split(",");
                int checkedScore = Integer.parseInt(data[1]);
                if (checkedScore >= game.points)
                    index++;
                line = reader.readLine();
            }
            reader.close();
            allLines.add(index, name + "," + game.points);
            byte[] newBytes = String.join("\n", allLines).getBytes();
            FileOutputStream outputStream = new FileOutputStream(highscoresFile);
            outputStream.write(newBytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameStarted) {
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
            case KeyEvent.VK_ENTER:
                if (game.gameOver) {
                    gameStarted = false;
                    game = new Game();
                    panel.repaint();
                    return;
                }
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
