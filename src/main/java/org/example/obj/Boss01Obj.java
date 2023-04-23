package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

import static org.example.AirWars.boss01Obj;

public class Boss01Obj extends GameObj{
    public static int healthPoint01 = 20;
    public Boss01Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        if (x > 283 || x < 10)
            speed = -speed;
        x += speed;
        for (ShellObj shellObj: GameUtils.shellObjList) {
            if (this.getRec().intersects(shellObj.getRec())){
                ExplodeObj explodeObj = new ExplodeObj(x,y);
                GameUtils.explodeObjList.add(explodeObj);
                GameUtils.removeList.add(explodeObj);
                shellObj.setX(-1000);
                shellObj.setY(1000);
                GameUtils.removeList.add(shellObj);
                healthPoint01--;
            }
            if (healthPoint01 == 0) {
                healthPoint01 = -1;
                gImage.drawImage(GameUtils.epImg,boss01Obj.getX(),boss01Obj.getY()-20,null);
                boss01Obj.setX(-2222);
                boss01Obj.setY(2222);
                GameUtils.removeList.add(boss01Obj);
                AirWars.score = AirWars.score + 10;
                PlaneObj.urHP = 10;
                AirWars.Ven = 50;
            }
        }
        //血条白色背景
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,10, 10);
        //血条的绘制
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,healthPoint01 * 100 / 20, 10);
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
