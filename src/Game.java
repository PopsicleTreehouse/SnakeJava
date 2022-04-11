import java.util.ArrayDeque;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Game {

    public boolean gameOver;
    private int points;
    private Snake snake;
    private GameObject target;
    private GameObject[][] objects;
    // KeyBoard events are asynchronous, so have to place them in a queue which
    // is read each frames
    private ArrayDeque<Integer> actionQueue;
    private static final int MAX_HEIGHT = 16, MAX_WIDTH = 16, CELL_WIDTH = 25, CELL_HEIGHT = 25;
    private static final int DIRECTION_LEFT = 0, DIRECTION_RIGHT = 1, DIRECTION_UP = 2, DIRECTION_DOWN = 3;

    public Game() {
        points = 0;
        gameOver = false;
        objects = new GameObject[MAX_HEIGHT][MAX_WIDTH];
        actionQueue = new ArrayDeque<>();
        snake = new Snake(5, 5, CELL_WIDTH, CELL_HEIGHT, Color.GREEN, 1, 0);
        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int x = 0; x < MAX_WIDTH; x++) {
                GameObject obj = new GameObject(i, x, CELL_WIDTH, CELL_HEIGHT, Color.BLACK);
                objects[i][x] = obj;
            }
        }
        refreshTarget();
    }

    private boolean intersectsSnake(int x, int y) {
        for (Segment seg : snake.getSnake()) {
            Rectangle rect = seg.getRect();
            if (rect.x == x && rect.y == y) {
                return true;
            }
        }
        return false;
    }

    private void refreshTarget() {
        if (target != null) {
            Rectangle rect = target.getRect();
            int x = rect.x;
            int y = rect.y;
            objects[x][y] = new GameObject((GameObject) target);
            objects[x][y].color = Color.BLACK;
        }
        Random rand = new Random();
        int x = rand.nextInt(MAX_WIDTH);
        int y = rand.nextInt(MAX_HEIGHT);
        while (true) {
            if (intersectsSnake(x, y)) {
                x = rand.nextInt(MAX_WIDTH);
                y = rand.nextInt(MAX_HEIGHT);
                continue;
            }
            break;
        }
        target = new Target(objects[x][y], Color.RED);
        objects[x][y] = target;
    }

    /**
     * This is called every time the Timer goes off. Right now, it moves all
     * the Objects and checks for collisions. This is common in games with flying
     * Objects. You can do more, though. Like add items or move to new screens
     * or check to see if the turn is over or...
     */
    public void updateGame() {
        moveObjects();
        checkCollisions();
    }

    private void updateSnakeIfNeeded() {
        if (actionQueue.size() == 0)
            return;
        switch (actionQueue.remove()) {
            case DIRECTION_UP:
                snake.setDy(-1);
                break;
            case DIRECTION_DOWN:
                snake.setDy(1);
                break;
            case DIRECTION_LEFT:
                snake.setDx(-1);
                break;
            case DIRECTION_RIGHT:
                snake.setDx(1);
                break;
        }
    }

    /**
     * get it...
     */
    private void moveObjects() {
        updateSnakeIfNeeded();
        snake.move();
    }

    /**
     * Right now I am checking for collisions between GameObjects
     */
    private void checkCollisions() {
        Segment head = snake.getHead();
        Rectangle rect = head.getRect();
        int x = rect.x;
        int y = rect.y;
        if (head.collidedWith(target)) {
            snake.levelUp();
            refreshTarget();
            points++;
            return;
        }
        if (x < 0 || x >= MAX_WIDTH || y < 0 || y >= MAX_HEIGHT) {
            gameOver = true;
            return;
        }
        for (Segment seg : snake.getBody()) {
            if (head.collidedWith(seg)) {
                gameOver = true;
                return;
            }
        }
    }

    /**
     * Draws all the stuff in the game without changing them
     * No reason to change this unless you wanted a background
     * or something.
     * 
     * @param g
     */
    public void drawTheGame(Graphics g) {
        snake.draw(g);
        g.setColor(Color.BLACK);
        g.drawString("Points: " + points, MAX_WIDTH * CELL_WIDTH + 30, CELL_HEIGHT - 5);
        for (GameObject[] row : this.objects) {
            for (GameObject col : row) {
                col.draw(g);
            }
        }
    }

    public void ltHit() {
        actionQueue.add(DIRECTION_LEFT);
    }

    public void rtHit() {
        actionQueue.add(DIRECTION_RIGHT);
    }

    public void upHit() {
        actionQueue.add(DIRECTION_UP);
    }

    public void downHit() {
        actionQueue.add(DIRECTION_DOWN);
    }

}
