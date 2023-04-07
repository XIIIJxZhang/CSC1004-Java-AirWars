package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

public class EnemyObj extends GameObj{
    public EnemyObj() {
        super();
    }

    public EnemyObj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        //对我方飞机与敌机碰撞检测
        if(this.getRec().intersects(this.frame.planeObj.getRec())){
            PlaneObj.urHP --;
            if( PlaneObj.urHP <= 0)
                AirWars.state = 7;
        }
        //对子弹和敌方飞机进行检测
        for (ShellObj shellObj: GameUtils.shellObjList) {
            if ((this.getRec().intersects(shellObj.getRec()))){
                gImage.drawImage(GameUtils.epImg,shellObj.getX(),shellObj.getY()-20,null);
                ExplodeObj explodeObj = new ExplodeObj(x,y);
                GameUtils.explodeObjList.add(explodeObj);
                GameUtils.removeList.add(explodeObj);
                shellObj.setX(-1000);
                shellObj.setY(1000);
                this.x = -2000;
                this.y = 2000;
                GameUtils.removeList.add(shellObj);
                GameUtils.removeList.add(this);
                AirWars.score++;
            }
        }
        // if ((this.getRec().intersects(PlaneObj.planeobj.getRec())))
        //游戏优化
        if (y > 645){
            this.x = -3000;
            this.y = 3000;
            GameUtils.removeList.add(this);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
