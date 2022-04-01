import java.awt.Color;

public class Segment extends GameObject {
    public Segment(int x, int y, int w, int h, Color c) {
        super(x, y, w, h, c);
    }

    public Segment(int x, int y, int w, int h, Color c, int dx, int dy) {
        super(x, y, w, h, c, dx, dy);
    }

    public Segment(Segment seg) {
        super((GameObject) seg);
    }
}