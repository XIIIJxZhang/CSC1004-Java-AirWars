package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

import static org.example.AirWars.boss04Obj;

public class Boss04Obj extends GameObj{
    public static int healthPoint04 = 100;
    public Boss04Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
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
                ExplodeObj explodeObj = new ExplodeObj(x+50,y);
                GameUtils.explodeObjList.add(explodeObj);
                GameUtils.removeList.add(explodeObj);
                shellObj.setX(-1004);
                shellObj.setY(1004);
                GameUtils.removeList.add(shellObj);
                healthPoint04--;
            }
            if (healthPoint04 == 0) {
                healthPoint04 = -1;
                gImage.drawImage(GameUtils.epImg,boss04Obj.getX(),boss04Obj.getY()-20,null);
                boss04Obj.setX(-2222);
                boss04Obj.setY(2222);
                GameUtils.removeList.add(boss04Obj);
                AirWars.score = AirWars.score + 70;
                PlaneObj.urHP = 12;
                AirWars.Ven02 = 33;
            }
        }
        //血条白色背景
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,10, 10);
        //血条的绘制
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,healthPoint04 * 100 / 50, 10);
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}

