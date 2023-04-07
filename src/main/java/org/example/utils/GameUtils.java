package org.example.utils;

import org.example.obj.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    //打开界面
    public static Image enbgImg = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\entranceBg.jpg").getImage();
    //关卡一背景
    public static Image bgImg001 = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\set01Bg.jpg").getImage();
    //Boss001图片
    public static Image bossImg001 = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\boss001.png").getImage();
    //爆炸图片GIF
    public static Image epImg = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\explode.gif").getImage();
    //我方战斗机图片
    public static Image urPlaneImg = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\urPlane.png").getImage();
    //我方子弹的图片
    public static Image urShellImg = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\urShell.png").getImage();
    //敌方子弹图片
    public static Image enBulletImg = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\enBullet.png").getImage();
    //敌机图片
    public static Image enemyImg = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\enemy.png").getImage();
    //敌机子弹图片
    public static Image enShellImg = new ImageIcon("E:\\E for Education\\大学文件夹\\大一下\\CSC1004\\CSC1004-Java-AirWars\\imgs\\enShell.png").getImage();

    //我方子弹集合
    public static List<GameObj> gameObjList = new ArrayList<>();
    //删除消失的集合
    public static List<GameObj> removeList = new ArrayList<>();
    //所有游戏物体集合
    public static List<ShellObj> shellObjList = new ArrayList<>();
    //敌机集合
    public static List<EnemyObj> enemyObjList = new ArrayList<>();
    //Boss子弹集合
    public static List<BulletObj> enBulletObjList = new ArrayList<>();
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
