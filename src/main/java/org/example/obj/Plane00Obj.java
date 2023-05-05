package org.example.obj;

import org.example.AirWars;
import org.example.utils.GameUtils;

import java.awt.*;

public class Plane00Obj extends GameObj{
    public Plane00Obj() {
        super();
    }

    public Plane00Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        if (y >= 0){
            y -= speed;
            speed++;
        }
        if (y < 0){
            this.x = 525;
            this.y = -525;
            GameUtils.removeList.add(this);
            gImage.setColor(Color.green);
            gImage.setFont(new Font("楷书",Font.BOLD,60));
            gImage.drawString("大爷~还没玩够？",120,320);
            gImage.drawString("Infinite Mode",100,380);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
