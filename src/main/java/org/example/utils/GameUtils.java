package org.example.utils;

import org.example.obj.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    //打开界面
    public static Image enbgImg = new ImageIcon("imgs\\entranceBg.jpg").getImage();
    //关卡一背景
    public static Image bgImg001 = new ImageIcon("imgs\\set01Bg.jpg").getImage();
    //关卡二背景
    public static Image bgImg002 = new ImageIcon("imgs\\set02Bg.jpg").getImage();
    //Boss001图片
    public static Image bossImg001 = new ImageIcon("imgs\\boss001.png").getImage();
    //Boss002图片
    public static Image bossImg002 = new ImageIcon("imgs\\boss002.png").getImage();
    //Boss003图片
    public static Image bossImg003 = new ImageIcon("imgs\\boss003.png").getImage();
    //爆炸图片GIF
    public static Image epImg = new ImageIcon("imgs\\explode.gif").getImage();
    //我方战斗机图片
    public static Image urPlaneImg = new ImageIcon("imgs\\urPlane.png").getImage();
    //转场
    public static Image urPlane000Img = new ImageIcon("imgs\\urPlane.png").getImage();
    //我方子弹的图片
    public static Image urShellImg = new ImageIcon("imgs\\urShell.png").getImage();
    //敌方子弹图片
    public static Image enBullet01Img = new ImageIcon("imgs\\enBullet01.png").getImage();
    //敌方子弹图片
    public static Image enBullet02Img = new ImageIcon("imgs\\enBullet02.png").getImage();
    //敌方子弹图片
    public static Image enBullet03Img = new ImageIcon("imgs\\enBullet03.png").getImage();
    //敌机图片
    public static Image enemyImg = new ImageIcon("imgs\\enemy.png").getImage();
    //敌机子弹图片
    public static Image enShellImg = new ImageIcon("imgs\\enShell.png").getImage();

    //所有游戏物体集合
    public static List<GameObj> gameObjList = new ArrayList<>();
    //删除消失的集合
    public static List<GameObj> removeList = new ArrayList<>();
    //我方子弹集合
    public static List<ShellObj> shellObjList = new ArrayList<>();
    //敌机集合
    public static List<EnemyObj> enemyObjList = new ArrayList<>();
    //Boss01子弹集合
    public static List<Bullet01Obj> enBullet01ObjList = new ArrayList<>();
    //Boss02子弹集合
    public static List<Bullet02Obj> enBullet02ObjList = new ArrayList<>();
    //Boss01子弹集合
    public static List<Bullet03Obj> enBullet03ObjList = new ArrayList<>();
    //敌方子弹集合
    public static List<EnShellObj> enShellObjList = new ArrayList<>();
    //敌机爆炸集合
    public static List<ExplodeObj> explodeObjList = new ArrayList<>();
    // public static List<PlaneObj> planeObjList = new ArrayList<>();

    //绘制计分面板
    public static void drawWord(Graphics gImage, String str, Color color,int size, int x, int y){
        gImage.setColor(color);
        gImage.setFont(new Font("隶书",Font.BOLD,size));
        gImage.drawString(str,x,y);
    }
}
