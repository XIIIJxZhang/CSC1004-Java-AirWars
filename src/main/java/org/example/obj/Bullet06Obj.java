package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

public class Bullet06Obj extends GameObj{
    public Bullet06Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        if (x > AirWars.boss06Obj.getX() + 60){
            x += 0.5 * speed;
            y += speed;
        }
        else {
            x -= 0.5 * speed;
            y += speed;
        }
        //子弹撞击我方飞机
        if (this.getRec().intersects(this.frame.planeObj.getRec()))
            PlaneObj.urHP = PlaneObj.urHP - 8;
        if ((y > 645) || (Boss06Obj.healthPoint06 == 0)){
            this.x = -1000;
            this.y = 1000;
            GameUtils.removeList.add(this);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
