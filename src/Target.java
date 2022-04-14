import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Target extends GameObject {
    public Target(int x, int y, int w, int h, Color c) {
        super(x, y, w, h, c);
    }

    public Target(int x, int y, int w, int h, Color c, int dx, int dy) {
        super(x, y, w, h, c, dx, dy);
    }

    public Target(GameObject g, Color c) {
        super(g);
        color = c;
    }

    @Override
    public void draw(Graphics g) {
        Rectangle rect = getRect();
        g.setColor(color);
        g.fillRect(rect.x * rect.width, rect.y * rect.height, rect.width, rect.height);
    }
}