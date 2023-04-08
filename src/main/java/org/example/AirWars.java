package org.example;
import org.example.obj.*;
import org.example.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.MessageFormat;


public class AirWars extends JFrame {

    //游戏状态：0 未开始 1 登录界面 2 游戏中 3 暂停 4 爆炸 5 下一关 6 暴走 7 失败 8 体验模式

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

    //背景图对象
    BgObj bgObj = new BgObj(GameUtils.bgImg001,0,-3375,3);
    //创建Boss
    public BossObj bossObj;
    //创建敌机对象
    public EnemyObj enemyObj;
    //创建我方飞机对象
    public PlaneObj planeObj = new PlaneObj(GameUtils.urPlaneImg,220,550,26,28,0,this);

    public void launch(){
        //设置窗口是否可见
        this.setVisible(true);
        //设置窗口大小
        this.setSize(width,height);
        //设置窗口位置
        this.setLocationRelativeTo(null);
        //设置窗口标题
        this.setTitle("飞机大战 AirWars");

        GameUtils.gameObjList.add(bgObj);
        GameUtils.gameObjList.add(planeObj);
        //GameUtils.gameObjList.add(bossObj);

        //键盘操作
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 32) {
                    switch (state) {
                        case 3 -> state = 2;
                        case 2 -> state = 3;
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
            //游戏暂停已完成.....
        }
        if (state == 4){
            //爆炸(useless)
            gImage.drawImage(GameUtils.epImg,planeObj.getX(),planeObj.getY()-20,null);
        }
        if (state == 5){
            //下一关
            gImage.drawImage(GameUtils.epImg,bossObj.getX(),bossObj.getY()-20,null);
            gImage.setColor(Color.green);
            gImage.setFont(new Font("楷书",Font.BOLD,60));
            gImage.drawString("Success",125,320);
            gImage.drawString("下一关！！",100,380);
        }
        if (state == 6){
            //待开发......
        }
        if (state == 7){
            //失败
            gImage.drawImage(GameUtils.epImg,planeObj.getX(),planeObj.getY()-20,null);
            gImage.setColor(Color.RED);
            gImage.setFont(new Font("楷书",Font.BOLD,60));
            gImage.drawString("FAILED",137,380);
        }

        if (state != 7)
            GameUtils.drawWord(gImage,score+"分",Color.green,40,30,100);
        g.drawImage(offScreenImage,0,0,null);
        count++;
    }

    void createObj(){
        //我方子弹
        if (count % 15 == 0) {
            GameUtils.shellObjList.add(new ShellObj(GameUtils.urShellImg, planeObj.getX() + 13, planeObj.getY() - 16, 14, 29, 8, this));
            GameUtils.gameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size() - 1));
        }
        //Boss子弹
        if ((count % 45 == 0) && (bossObj != null)) {
            GameUtils.enBulletObjList.add(new BulletObj((GameUtils.enBulletImg), bossObj.getX() + 60, bossObj.getY() + 50, 25, 47, 5, this));
            GameUtils.gameObjList.add(GameUtils.enBulletObjList.get(GameUtils.enBulletObjList.size() - 1));
        }
        //敌机
        if (count % 80 == 0) {
            enemyObj = new EnemyObj(GameUtils.enemyImg,(int) ((Math.random()*10)*36),0,89,70,5,this);
            GameUtils.enemyObjList.add(enemyObj);
            GameUtils.gameObjList.add(GameUtils.enemyObjList.get(GameUtils.enemyObjList.size() - 1));
            enemyCount++;
        }
        //敌机子弹
        if (count % 80 == 0){
            int v = 7;
            for(int i = 1; i <= 2; i++){
                GameUtils.enShellObjList.add(new EnShellObj((GameUtils.enShellImg),enemyObj.getX()+40,enemyObj.getY()+60,21,25,v,this));
                GameUtils.gameObjList.add(GameUtils.enShellObjList.get(GameUtils.enShellObjList.size() - 1));
                v = v + 2;
            }
        }
        //召唤Boss
        if ((score != 0) && (score % 50 == 0) && (bossObj == null)) {
            bossObj = new BossObj(GameUtils.bossImg001,250,25,157,109,5,this);
            GameUtils.gameObjList.add(bossObj);
        }
    }
    public static void main(String[] args) {
        AirWars airWars = new AirWars();
        airWars.launch();
    }

}