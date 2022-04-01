import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;

public class Snake {
    private Segment head;
    private ArrayList<Segment> body;
    private Color bodyColor;

    public Snake(int x, int y, int w, int h, Color c, int dx, int dy) {
        bodyColor = c;
        head = new Segment(x, y, w, h, Color.BLUE, dx, dy);
        body = new ArrayList<Segment>();
        body.add(head);
        for (int i = 0; i < 3; i++) {
            Segment seg = new Segment(x - i - 1, y, w, h, c, dx, dy);
            body.add(seg);
        }
    }

    public void draw(Graphics g) {
        for (Segment seg : body) {
            Rectangle rect = seg.getRect();
            g.setColor(seg.color);
            g.fillRect(rect.x * rect.width, rect.y * rect.height, rect.width,
                    rect.height);
        }
    }

    public void levelUp() {
        Segment newTail = new Segment(body.get(body.size() - 1));
        int dx = newTail.getDx();
        int dy = newTail.getDy();
        newTail.getRect().translate(dx, dy);
        body.add(newTail);
    }

    public void move() {
        Segment inserted = new Segment(head);
        inserted.move();
        head.color = bodyColor;
        body.add(0, inserted);
        head = inserted;
        body.remove(body.size() - 1);
    }

    public void setDx(int dx) {
        if (head.getDx() == 0) {
            head.setDx(dx);
            head.setDy(0);
        }
    }

    public void setDy(int dy) {
        if (head.getDy() == 0) {
            head.setDy(dy);
            head.setDx(0);
        }
    }

    public ArrayList<Segment> getSnake() {
        return body;
    }

    public List<Segment> getBody() {
        return body.subList(1, body.size());
    }

    public Segment getHead() {
        return body.get(0);
    }

}
