package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

public class EnShellObj extends GameObj{
    public EnShellObj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        //子弹撞击我方飞机
        if (this.getRec().intersects(this.frame.planeObj.getRec())) {
            PlaneObj.urHP--;
            this.x = -666;
            this.y = 666;
            GameUtils.removeList.add(this);
        }
        //敌方子弹撞击我方子弹
        for (ShellObj shellObj: GameUtils.shellObjList) {
            if ((this.getRec().intersects(shellObj.getRec()))){
                ExplodeObj explodeObj = new ExplodeObj(x,y);
                GameUtils.explodeObjList.add(explodeObj);
                GameUtils.removeList.add(explodeObj);
                shellObj.setX(-1380);
                shellObj.setY(1380);
                this.x = -2560;
                this.y = 2560;
                GameUtils.removeList.add(shellObj);
                GameUtils.removeList.add(this);
                AirWars.score++;
            }
        }
        if (y > 576){
            this.x = -1444;
            this.y = 1444;
            GameUtils.removeList.add(this);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
