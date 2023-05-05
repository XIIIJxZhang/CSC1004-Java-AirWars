package org.example;
import org.example.obj.*;
import org.example.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AirWars extends JFrame {

    //游戏状态：0 未开始 1 登录界面 2 游戏中 3 转场动画1 4 转场动画2 5 第二关 6 第三关 7 失败 8 第三关
    //暂停模式：10 关卡1 11 关卡2 12 关卡3
    //默认状态：未开始
    public static int state = 0;
    Image offScreenImage = null;

    int width = 450;
    int height =800;
    //定义游戏重绘次数
    int count = 1;
    //计分面板
    public static int score = 0;
    //敌机出现数量
    int enemyCount = 0;
    //定义我方子弹速度
    public static int Vur = 10;
    //定义敌方飞机速度
    public static int Ven = 60;

    //背景图01对象
    public static Bg01Obj bg01Obj = new Bg01Obj(GameUtils.bgImg001,0,-3375,4);
    //背景图01对象
    public static Bg02Obj bg02Obj = new Bg02Obj(GameUtils.bgImg002,0,-3375,8);
    //创建Boss01
    public static Boss01Obj boss01Obj;
    //创建Boss02
    public static Boss02Obj boss02Obj;
    //创建Boss02子弹01
    public static Bullet02Obj bullet0201Obj;
    //创建Boss02子弹02
    public static Bullet02Obj bullet0202Obj;
    //创建Boss03
    public static Boss03Obj boss03Obj;
    //创建Boss04
    public static Boss04Obj boss04Obj;
    //创建Boss05
    public static Boss05Obj boss05Obj;
    //创建Boss06
    public static Boss06Obj boss06Obj;
    //创建敌机对象
    public EnemyObj enemyObj;
    //创建我方飞机对象
    public PlaneObj planeObj = new PlaneObj(GameUtils.urPlaneImg,212,550,26,28,0,this);
    //转场动画1
    public Plane000Obj plane000Obj = new Plane000Obj(GameUtils.urPlaneImg,212,600,26,28,0.5,this);
    //转场动画2
    public Plane000Obj plane00Obj = new Plane000Obj(GameUtils.urPlaneImg,212,600,26,28,0.5,this);

    public void launch(){
        //设置窗口是否可见
        this.setVisible(true);
        //设置窗口大小
        this.setSize(width,height);
        //设置窗口位置
        this.setLocationRelativeTo(null);
        //设置窗口标题
        this.setTitle("飞机大战 AirWars");

        GameUtils.gameObjList.add(bg01Obj);
        GameUtils.gameObj02List.add(bg02Obj);
        GameUtils.gameObjList.add(planeObj);
        GameUtils.gameObj02List.add(planeObj);

        //键盘操作
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 32) {
                    switch (state) {
                        case 10 -> state = 2;
                        case 2 -> state = 10;
                        default -> {
                        }
                    }
                }

                if (e.getKeyCode() == 32) {
                    switch (state) {
                        case 11 -> state = 5;
                        case 5 -> state = 11;
                        default -> {
                        }
                    }
                }

                if (e.getKeyCode() == 32) {
                    switch (state) {
                        case 6 -> state = 12;
                        case 12 -> state = 6;
                        default -> {
                        }
                    }
                }
            }
        });

        while(true){
            if (state == 2){
                createObj();
                repaint();
            }
            if (state == 5){
                create02Obj();
                repaint();
            }
            try {
                Thread.sleep(25);
            }   catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null)
            offScreenImage = createImage(width,height);

        Graphics gImage = offScreenImage.getGraphics();
        gImage.fillRect(0,0,width,height);

        //游戏未开始时
        if (state == 0){
            //鼠标点击
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //游戏未开始情况下点击鼠标左键
                    if (e.getButton() == 1 && state == 0){
                        state = 2;  //游戏开始
                        repaint();
                    }
                }
            });
            gImage.drawImage(GameUtils.enbgImg,0,0,null);
            gImage.setColor(Color.yellow);
            gImage.setFont(new Font("仿宋",Font.BOLD,40));
            gImage.drawString("Start",170,450);

            gImage.setColor(Color.black);
            gImage.setFont(new Font("华文行楷",Font.BOLD,60));
            gImage.drawString("飞机大战",100,120);

            gImage.setColor(Color.orange);
            gImage.setFont(new Font("华文隶书",Font.BOLD,45));
            gImage.drawString("AirWars",140,200);
        }

        if (state == 1){
            //登录界面还在调试......
        }
        if (state == 2){
            //游戏开始
            GameUtils.gameObjList.addAll(GameUtils.explodeObjList);
            for (int i = 0; i < GameUtils.gameObjList.size(); i++)
                GameUtils.gameObjList.get(i).paintSelf(gImage);
            GameUtils.gameObjList.removeAll(GameUtils.removeList);
        }
        if (state == 3){
            try {
                Thread.sleep(25);
            }   catch (InterruptedException e){
                e.printStackTrace();
            }
            GameUtils.gameObjList.removeAll(GameUtils.gameObjList);
            GameUtils.gameObjList.add(bg01Obj);
            GameUtils.gameObjList.add(plane000Obj);
            repaint();
            GameUtils.gameObjList.addAll(GameUtils.explodeObjList);
            for (int i = 0; i < GameUtils.gameObjList.size(); i++)
                GameUtils.gameObjList.get(i).paintSelf(gImage);
            GameUtils.gameObjList.removeAll(GameUtils.removeList);
            //鼠标点击
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //游戏第一关结束情况下点击鼠标左键
                    if (e.getButton() == 1 && state == 3){
                        state = 5;  //游戏继续
                        repaint();
                    }
                }
            });
        }
        if (state == 4){
            try {
                Thread.sleep(25);
            }   catch (InterruptedException e){
                e.printStackTrace();
            }
            GameUtils.gameObjList.removeAll(GameUtils.gameObjList);
            GameUtils.gameObjList.add(bg02Obj);
            GameUtils.gameObjList.add(plane00Obj);
            repaint();
            GameUtils.gameObjList.addAll(GameUtils.explodeObjList);
            for (int i = 0; i < GameUtils.gameObjList.size(); i++)
                GameUtils.gameObjList.get(i).paintSelf(gImage);
            GameUtils.gameObjList.removeAll(GameUtils.removeList);
            //鼠标点击
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //游戏第一关结束情况下点击鼠标左键
                    if (e.getButton() == 1 && state == 3){
                        state = 5;  //游戏继续
                        repaint();
                    }
                }
            });
        }
        if (state == 5){
            GameUtils.gameObj02List.addAll(GameUtils.explodeObjList);
            for (int i = 0; i < GameUtils.gameObj02List.size(); i++)
                GameUtils.gameObj02List.get(i).paintSelf(gImage);
            GameUtils.gameObj02List.removeAll(GameUtils.removeList);
        }
        if (state == 6){
            //待开发......
        }
        if (state == 7){
            //失败
            gImage.drawImage(GameUtils.epImg,planeObj.getX(),planeObj.getY()-20,null);
            gImage.setColor(Color.RED);
            gImage.setFont(new Font("楷书",Font.BOLD,60));
            gImage.drawString("FAILED",130,380);
        }

        if (state != 7)
            GameUtils.drawWord(gImage,score+"分",Color.green,40,30,100);

        g.drawImage(offScreenImage,0,0,null);
        count++;
    }

    void createObj(){
        //我方子弹
        if (count % Vur == 0) {
            int m = 8;
            for (int i = 0; i < 2; i++) {
                m += 2;
                GameUtils.shellObjList.add(new ShellObj(GameUtils.urShellImg, planeObj.getX() + 13, planeObj.getY() - 16, 14, 29, m, this));
                GameUtils.gameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size() - 1));
            }
        }
        //Boss01子弹
        if ((count % 60 == 0) && (boss01Obj != null)) {
            GameUtils.enBullet01ObjList.add(new Bullet01Obj((GameUtils.enBullet01Img), boss01Obj.getX() + 60, boss01Obj.getY() + 50, 20, 45, 5, this));
            GameUtils.gameObjList.add(GameUtils.enBullet01ObjList.get(GameUtils.enBullet01ObjList.size() - 1));
        }
        //Boss02子弹
        if ((count % 60 == 0) && (boss02Obj != null)) {
            bullet0201Obj = new Bullet02Obj(GameUtils.enBullet02Img, boss02Obj.getX(), boss02Obj.getY() + 50, 21, 59, 5, this);
            bullet0202Obj = new Bullet02Obj(GameUtils.enBullet02Img, boss02Obj.getX() + 120, boss02Obj.getY() + 50, 21, 59, 5, this);
            GameUtils.enBullet0201ObjList.add(bullet0201Obj);
            GameUtils.enBullet0202ObjList.add(bullet0202Obj);
            GameUtils.gameObjList.add(GameUtils.enBullet0201ObjList.get(GameUtils.enBullet0201ObjList.size() - 1));
            GameUtils.gameObjList.add(GameUtils.enBullet0202ObjList.get(GameUtils.enBullet0202ObjList.size() - 1));
        }
        //Boss03子弹
        if ((count % 60 == 0) && (boss03Obj != null)) {
            GameUtils.enBullet03ObjList.add(new Bullet03Obj((GameUtils.enBullet03Img), boss03Obj.getX() + 60, boss03Obj.getY() + 50, 37, 40, 5, this));
            GameUtils.gameObjList.add(GameUtils.enBullet03ObjList.get(GameUtils.enBullet03ObjList.size() - 1));
        }
        //敌机
        if (count % Ven == 0) {
            enemyObj = new EnemyObj(GameUtils.enemyImg,(int) ((Math.random()*10)*36),0,89,70,5,this);
            GameUtils.enemyObjList.add(enemyObj);
            GameUtils.gameObjList.add(GameUtils.enemyObjList.get(GameUtils.enemyObjList.size() - 1));
            enemyCount++;
        }
        //敌机子弹
        if (count % Ven == 0){
            int v = 7;
            for(int i = 1; i <= 2; i++){
                GameUtils.enShellObjList.add(new EnShellObj((GameUtils.enShellImg),enemyObj.getX()+40,enemyObj.getY()+60,21,25,v,this));
                GameUtils.gameObjList.add(GameUtils.enShellObjList.get(GameUtils.enShellObjList.size() - 1));
                v += 2;
            }
        }
        //召唤Boss01
        if ((score == 25) && (boss01Obj == null)) {
            boss01Obj = new Boss01Obj(GameUtils.bossImg001,250,25,157,109,5,this);
            GameUtils.gameObjList.add(boss01Obj);
        }

        //召唤Boss02
        if ((score == 100) && (boss02Obj == null)) {
            boss02Obj = new Boss02Obj(GameUtils.bossImg002,250,25,197,134,5,this);
            GameUtils.gameObjList.add(boss02Obj);
        }

        //召唤Boss03
        if ((score == 250) && (boss03Obj == null)) {
            boss03Obj = new Boss03Obj(GameUtils.bossImg003,250,25,258,180,5,this);
            GameUtils.gameObjList.add(boss03Obj);
        }
    }

    void create02Obj(){
        //我方子弹
        if (count % Vur == 0) {
            int m = 8;
            for (int i = 0; i < 2; i++) {
                m += 2;
                GameUtils.shellObjList.add(new ShellObj(GameUtils.urShellImg, planeObj.getX() + 13, planeObj.getY() - 16, 14, 29, m, this));
                GameUtils.gameObj02List.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size() - 1));
            }
        }
        //Boss04子弹
        if ((count % 60 == 0) && (boss04Obj != null)) {
            GameUtils.enBullet01ObjList.add(new Bullet01Obj((GameUtils.enBullet01Img), boss04Obj.getX() + 60, boss04Obj.getY() + 50, 20, 45, 5, this));
            GameUtils.gameObj02List.add(GameUtils.enBullet01ObjList.get(GameUtils.enBullet01ObjList.size() - 1));
        }
        //Boss05子弹
        if ((count % 60 == 0) && (boss05Obj != null)) {
            GameUtils.enBullet0201ObjList.add(new Bullet02Obj((GameUtils.enBullet02Img), boss05Obj.getX() + 60, boss05Obj.getY() + 50, 21, 59, 5, this));
            GameUtils.gameObj02List.add(GameUtils.enBullet0201ObjList.get(GameUtils.enBullet0201ObjList.size() - 1));
        }
        //Boss06子弹
        if ((count % 60 == 0) && (boss06Obj != null)) {
            GameUtils.enBullet03ObjList.add(new Bullet03Obj((GameUtils.enBullet03Img), boss06Obj.getX() + 60, boss06Obj.getY() + 50, 37, 40, 5, this));
            GameUtils.gameObj02List.add(GameUtils.enBullet03ObjList.get(GameUtils.enBullet03ObjList.size() - 1));
        }
        //敌机
        if (count % Ven == 0) {
            enemyObj = new EnemyObj(GameUtils.enemyImg,(int) ((Math.random()*10)*36),0,89,70,5,this);
            GameUtils.enemyObjList.add(enemyObj);
            GameUtils.gameObj02List.add(GameUtils.enemyObjList.get(GameUtils.enemyObjList.size() - 1));
            enemyCount++;
        }
        //敌机子弹
        if (count % Ven == 0){
            int v = 7;
            for(int i = 1; i <= 2; i++){
                GameUtils.enShellObjList.add(new EnShellObj((GameUtils.enShellImg),enemyObj.getX()+40,enemyObj.getY()+60,21,25,v,this));
                GameUtils.gameObj02List.add(GameUtils.enShellObjList.get(GameUtils.enShellObjList.size() - 1));
                v += 2;
            }
        }
        //召唤Boss04
        if ((score == 400) && (boss04Obj == null)) {
            boss04Obj = new Boss04Obj(GameUtils.bossImg004,250,25,157,109,5,this);
            GameUtils.gameObj02List.add(boss04Obj);
        }

        //召唤Boss05
        if ((score == 550) && (boss05Obj == null)) {
            boss05Obj = new Boss05Obj(GameUtils.bossImg005,250,25,157,109,5,this);
            GameUtils.gameObj02List.add(boss05Obj);
        }

        //召唤Boss06
        if ((score == 700) && (boss06Obj == null)) {
            boss06Obj = new Boss06Obj(GameUtils.bossImg006, 250, 25, 157, 109, 5, this);
            GameUtils.gameObj02List.add(boss06Obj);
        }
    }
    public static void main(String[] args) {
        AirWars airWars = new AirWars();
        airWars.launch();
    }
}