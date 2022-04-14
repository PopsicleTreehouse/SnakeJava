import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Generic GameObject. This has all the BASIC attributes and behaviors that
 * ALL game objects should have. Many of these can be overridden in subclasses.
 * Those behaviors and attributes include:
 * 
 * Location and size (using a Rectangle for that)
 * Color (used for testing but not used in the final version)
 * speed in the x and speed in the y direction
 * The GameObject has the ability to move, detect collisions with
 * another GameObject and the ability to draw itself on a Graphics
 * 
 * @author RHanson
 *
 */
public class GameObject {

    /** rect has info about location and dimension of this game object */
    private Rectangle rect;
    /** Color of this object */
    public Color color;
    /**
     * dx is how far this object moves this Rectangle each time I move
     * dy is how far this object moves the Rectangle each time I move
     * If dy or dx change between moves, it will look like this object is
     * accelerating or decelerating in that direction.
     */
    private int dx, dy;

    public GameObject(int x, int y, int w, int h, Color c) {
        this(x, y, w, h, c, 0, 0);
    }

    public GameObject(int x, int y, int w, int h, Color c, int dx, int dy) {
        rect = new Rectangle(x, y, w, h);
        color = c;
        this.dx = dx;
        this.dy = dy;
    }

    public GameObject(GameObject g) {
        this(g.rect.x, g.rect.y, g.rect.width, g.rect.height, g.color, g.dx, g.dy);
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void move() {
        moveX();
        moveY();
    }

    public void moveY() {
        rect.translate(0, dy);
    }

    public void moveX() {
        rect.translate(dx, 0);
    }

    public Rectangle getRect() {
        return rect;
    }

    /** Pretty basic right now, but can make this way better! */
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(rect.x * rect.width, rect.y * rect.height, rect.width, rect.height);
    }

    public boolean collidedWith(GameObject go) {
        return rect.x == go.rect.x && rect.y == go.rect.y;
    }
}
