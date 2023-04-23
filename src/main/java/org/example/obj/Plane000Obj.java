package org.example.obj;

import org.example.AirWars;
import org.example.utils.GameUtils;

import java.awt.*;

public class Plane000Obj extends GameObj{
    public Plane000Obj() {
        super();
    }

    public Plane000Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
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
            gImage.drawString("Success",120,320);
            gImage.drawString("下一关！!",100,380);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }

}
