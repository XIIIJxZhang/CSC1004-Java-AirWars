package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;
import  java.awt.*;

public class EnemyObj extends GameObj{
    public int enhp = 2;
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
                ExplodeObj explodeObj = new ExplodeObj(x,y);
                GameUtils.explodeObjList.add(explodeObj);
                GameUtils.removeList.add(explodeObj);
                shellObj.setX(-1787);
                shellObj.setY(1787);
                GameUtils.removeList.add(shellObj);
                enhp--;
                if (enhp == 0) {
                    gImage.drawImage(GameUtils.epImg,shellObj.getX(),shellObj.getY()-20,null);
                    this.x = -2080;
                    this.y = 2080;
                    GameUtils.removeList.add(this);
                    AirWars.score++;
                }
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
