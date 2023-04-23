package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

import static org.example.AirWars.*;

public class Boss03Obj extends GameObj{
    public static int healthPoint03 = 75;
    public Boss03Obj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
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
                shellObj.setX(-1005);
                shellObj.setY(1005);
                GameUtils.removeList.add(shellObj);
                healthPoint03--;
            }
            if (healthPoint03 == 0) {
                healthPoint03 = -1;
                gImage.drawImage(GameUtils.epImg,boss03Obj.getX(),boss03Obj.getY()-20,null);
                GameUtils.removeList.add(boss03Obj);
                AirWars.score = AirWars.score + 30;
                PlaneObj.urHP = 10;
                state = 3;
            }
        }
        //血条白色背景
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,10, 10);
        //血条的绘制
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,healthPoint03 * 100 / 75, 10);
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}