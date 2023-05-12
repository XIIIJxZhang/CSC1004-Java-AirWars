package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

import static org.example.AirWars.*;

public class Boss02Obj extends GameObj{
    public static int healthPoint02 = 50;
    public Boss02Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
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
                ExplodeObj explodeObj = new ExplodeObj(x+70,y+20);
                GameUtils.explodeObjList.add(explodeObj);
                GameUtils.removeList.add(explodeObj);
                shellObj.setX(-1001);
                shellObj.setY(1001);
                GameUtils.removeList.add(shellObj);
                healthPoint02--;
            }
            if (healthPoint02 == 0) {
                healthPoint02 = -1;
                gImage.drawImage(GameUtils.epImg,boss02Obj.getX(),boss02Obj.getY()-20,null);
                boss02Obj.setX(-2553);
                boss02Obj.setY(2553);
                GameUtils.removeList.add(boss02Obj);
                AirWars.score = AirWars.score + 30;
                PlaneObj.urHP = 15;
                Vur = 12;
                Ven01 = 40;
            }
        }
        //血条白色背景
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,10, 10);
        //血条的绘制
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,healthPoint02 * 100 / 50, 10);
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
