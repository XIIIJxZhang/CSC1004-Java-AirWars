package org.example.obj;
import org.example.AirWars;
import org.example.utils.GameUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaneObj extends GameObj{
    //己方战机血量：1
    static int urHP = 3;

    @Override
    public Image getImg() {
        return super.getImg();
    }

    public PlaneObj() {
        super();
    }

    public PlaneObj(Image img, int x, int y, int width, int height, double speed, AirWars frame) {
        super(img, x, y, width, height, speed, frame);
        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                PlaneObj.super.x = e.getX() - 15;
                PlaneObj.super.y = e.getY() - 20;
            }
        });
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        //尝试对己方飞机和敌方飞机检测
        for (EnemyObj enemyObj: GameUtils.enemyObjList){
            if ((this.getRec().intersects(enemyObj.getRec()))){
                gImage.drawImage(GameUtils.epImg,this.getX(),this.getY()-20,null);
                enemyObj.setX(-1012);
                enemyObj.setY(1012);
                GameUtils.removeList.add(enemyObj);
                AirWars.score--;
                urHP--;
            }
        }
        if ((this.frame.boss01Obj != null) && (this.getRec().intersects(this.frame.boss01Obj.getRec())))
            AirWars.state = 7;
        if (urHP == 0)
            AirWars.state = 7;
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
