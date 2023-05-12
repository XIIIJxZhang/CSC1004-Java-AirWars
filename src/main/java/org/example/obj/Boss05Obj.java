package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

import static org.example.AirWars.boss05Obj;

public class Boss05Obj extends GameObj{
    public static int healthPoint05 = 20;
    public Boss05Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
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
                shellObj.setX(-1005);
                shellObj.setY(1005);
                GameUtils.removeList.add(shellObj);
                healthPoint05--;
            }
            if (healthPoint05 == 0) {
                healthPoint05 = -1;
                gImage.drawImage(GameUtils.epImg,boss05Obj.getX(),boss05Obj.getY()-20,null);
                boss05Obj.setX(-2222);
                boss05Obj.setY(2222);
                GameUtils.removeList.add(boss05Obj);
                AirWars.score = AirWars.score + 80;
                PlaneObj.urHP = 12;
                AirWars.Ven02 = 30;
            }
        }
        //血条白色背景
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,10, 10);
        //血条的绘制
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,healthPoint05 * 100 / 20, 10);
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}

