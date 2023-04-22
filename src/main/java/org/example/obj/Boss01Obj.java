package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

public class Boss01Obj extends GameObj{
    public static int healthPoint = 50;
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
                healthPoint--;
            }
            if (healthPoint <= 0) {
                GameUtils.removeList.add(AirWars.boss01Obj);
            }
        }
        //血条白色背景
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,10, 10);
        //血条的绘制
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,healthPoint * 100 / 50, 10);
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
