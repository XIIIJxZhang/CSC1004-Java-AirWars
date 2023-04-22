package org.example.obj;

import java.awt.*;

public class Bg01Obj extends GameObj{
    public Bg01Obj() {
        super();
    }

    public Bg01Obj(Image img, int x, int y, double speed) {
        super(img, x, y, speed);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        if (y >= 0) y = -3000;
    }
}
