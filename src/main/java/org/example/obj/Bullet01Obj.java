package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

public class Bullet01Obj extends GameObj{
    public Bullet01Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        //子弹撞击我方飞机
        if (this.getRec().intersects(this.frame.planeObj.getRec()))
            PlaneObj.urHP = PlaneObj.urHP - 3;
        if ((y > 645) || (Boss01Obj.healthPoint01 == -1)){
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