package org.example;

import org.example.login.LoginDemo;
import org.example.login.UserLogin;
import org.example.obj.*;
import org.example.utils.GameUtils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;
import java.io.IOException;

import static org.example.login.UserLogin.*;

public class AirWars extends JFrame {

    //游戏状态：0 未开始 1 登录界面 2 游戏中 3 转场动画1 4 转场动画2 5 第二关 6 第三关 7 失败 8 第三关
    //暂停模式：10 关卡1 11 关卡2 12 关卡3
    //默认状态：未开始
    public static int state = 1;
    private boolean musicPlayed = false;
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
    public static int Ven01 = 60;
    //定义敌方飞机速度
    public static int Ven02 = 50;


    //背景图01对象
    public static Bg01Obj bg01Obj = new Bg01Obj(GameUtils.bgImg001,0,-3375,4);
    //背景图01对象
    public static Bg02Obj bg02Obj = new Bg02Obj(GameUtils.bgImg002,0,-3375,0.01);
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
    //创建Boss03子弹01
    public static Bullet03Obj bullet0301Obj;
    //创建Boss03子弹02
    public static Bullet03Obj bullet0302Obj;
    //创建Boss04
    public static Boss04Obj boss04Obj;
    //创建Boss04子弹01
    public static Bullet04Obj bullet0401Obj;
    //创建Boss04子弹02
    public static Bullet04Obj bullet0402Obj;
    //创建Boss05
    public static Boss05Obj boss05Obj;
    //创建Boss05子弹01
    public static Bullet05Obj bullet0501Obj;
    //创建Boss05子弹02
    public static Bullet05Obj bullet0502Obj;
    //创建Boss06
    public static Boss06Obj boss06Obj;
    //创建Boss06子弹01
    public static Bullet06Obj bullet0601Obj;
    //创建Boss06子弹02
    public static Bullet06Obj bullet0602Obj;
    //创建敌机对象
    public EnemyObj enemyObj;
    //创建我方飞机对象
    public PlaneObj planeObj = new PlaneObj(GameUtils.urPlaneImg,212,550,26,28,0,this);
    //转场动画1
    public Plane000Obj plane000Obj = new Plane000Obj(GameUtils.urPlaneImg,212,600,26,28,0.5,this);
    //转场动画2
    public Plane00Obj plane00Obj = new Plane00Obj(GameUtils.urPlaneImg,212,600,26,28,0.5,this);

    public void launch(){
        //设置窗口是否可见
        this.setVisible(true);
        //设置窗口大小
        this.setSize(width,height);
        //设置窗口位置
        this.setLocationRelativeTo(null);
        //设置窗口标题
        this.setTitle("飞机大战 AirWars");
        //键盘操作
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 32) {
                    switch (state) {
                        case 10 -> state = 0;
                        case 0 -> state = 10;
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
            if ((state == 2) || (state == 0)){
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
            GameUtils.gameObjList.addAll(GameUtils.explodeObjList);
            for (int i = 0; i < GameUtils.gameObjList.size(); i++)
                GameUtils.gameObjList.get(i).paintSelf(gImage);
            GameUtils.gameObjList.removeAll(GameUtils.removeList);
        }

        if (state == 1) {
            UserLogin userLogin = new UserLogin();
            userLogin.getConnection();
            //界面渲染效果
            String lookAndFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
            try {
                UIManager.setLookAndFeel(lookAndFeel);
                new LoginDemo();
            } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                     IllegalAccessException e) {
                JOptionPane.showMessageDialog(null, "系统异常", "警告", JOptionPane.WARNING_MESSAGE);
            }
        }

// 在paint方法中修改播放音频文件的代码
        if (state == 2) {
            gImage.drawImage(GameUtils.enbgImg, 0, 0, null);

            gImage.setColor(Color.yellow);
            gImage.setFont(new Font("仿宋", Font.BOLD, 40));
            gImage.drawString("Start", 170, 450);

            gImage.setColor(Color.black);
            gImage.setFont(new Font("华文行楷", Font.BOLD, 60));
            gImage.drawString("飞机大战", 100, 120);

            gImage.setColor(Color.orange);
            gImage.setFont(new Font("华文隶书", Font.BOLD, 45));
            gImage.drawString("AirWars", 140, 200);

            // 添加游戏对象到列表
            GameUtils.gameObjList.removeAll(GameUtils.gameObjList);
            GameUtils.gameObjList.add(bg01Obj);
            GameUtils.gameObj02List.add(bg02Obj);
            GameUtils.gameObjList.add(planeObj);
            GameUtils.gameObj02List.add(planeObj);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //游戏未开始情况下点击鼠标左键
                    if (e.getButton() == 1 && state == 2){
                        state = 0;  //游戏开始
                        repaint();
                    }
                }
            });

            // 播放音频文件
            if ((!musicPlayed) && (state == 2)) {
                File audioFile = new File("mus/Game.wav");
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    if (!clip.isRunning()) {
                        clip.start();
                        musicPlayed = true;
                    }
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
            }
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
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //游戏第一关结束情况下点击鼠标左键
                    if (e.getButton() == 1 && state == 4){
                        state = 6;  //游戏继续
                        repaint();
                    }
                }
            });

            //鼠标点击
        }
        if (state == 5){
            try {
                Thread.sleep(25);
            }   catch (InterruptedException e){
                e.printStackTrace();
            }
            repaint();
            GameUtils.gameObj02List.addAll(GameUtils.explodeObjList);
            for (int i = 0; i < GameUtils.gameObj02List.size(); i++)
                GameUtils.gameObj02List.get(i).paintSelf(gImage);
            GameUtils.gameObj02List.removeAll(GameUtils.removeList);
        }
        if (state == 6){
            gImage.drawImage(GameUtils.bgImg002, 0, 0, null);
            gImage.setColor(Color.green);
            gImage.setFont(new Font("楷书",Font.BOLD,60));
            gImage.drawString("Success",125,320);
            gImage.drawString("胜利啦！！",100,380);
        }
        if (state == 7){
            //失败
            try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
                String sql01 = "select Score from airwars.mytable1 where Account = ?";
                PreparedStatement ps = conn.prepareStatement(sql01);
                ps.setString(1, UserLogin.str);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    int oldScore = rs.getInt("Score");
                    if(score > oldScore) {
                        String sql02 = "update airwars.mytable1 set Score = ? where Account = ?";
                        PreparedStatement upd = conn.prepareStatement(sql02);
                        upd.setString(2, UserLogin.str);
                        upd.setString(1, String.valueOf(score));
                        upd.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Your final score is " + score, "Notice", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Your final score is " + score, "Notice", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Your final score is " + score,"Failed",JOptionPane.INFORMATION_MESSAGE);
            }
            gImage.drawImage(GameUtils.epImg,planeObj.getX(),planeObj.getY()-20,null);
            gImage.setColor(Color.RED);
            gImage.setFont(new Font("楷书",Font.BOLD,60));
            gImage.drawString("FAILED",130,380);
            state = 6;
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
            bullet0301Obj = new Bullet03Obj(GameUtils.enBullet03Img, boss03Obj.getX(), boss03Obj.getY() + 50, 21, 59, 5, this);
            bullet0302Obj = new Bullet03Obj(GameUtils.enBullet03Img, boss03Obj.getX() + 120, boss03Obj.getY() + 50, 21, 59, 5, this);
            GameUtils.enBullet0301ObjList.add(bullet0301Obj);
            GameUtils.enBullet0302ObjList.add(bullet0302Obj);
            GameUtils.gameObjList.add(GameUtils.enBullet0301ObjList.get(GameUtils.enBullet0301ObjList.size() - 1));
            GameUtils.gameObjList.add(GameUtils.enBullet0302ObjList.get(GameUtils.enBullet0302ObjList.size() - 1));
        }
        //敌机
        if (count % Ven01 == 0) {
            enemyObj = new EnemyObj(GameUtils.enemyImg,(int) ((Math.random()*10)*36),0,89,70,5,this);
            GameUtils.enemyObjList.add(enemyObj);
            GameUtils.gameObjList.add(GameUtils.enemyObjList.get(GameUtils.enemyObjList.size() - 1));
            enemyCount++;
        }
        //敌机子弹
        if (count % Ven01 == 0){
            int v = 7;
            for(int i = 1; i <= 2; i++){
                GameUtils.enShellObjList.add(new EnShellObj((GameUtils.enShellImg),enemyObj.getX()+40,enemyObj.getY()+60,21,25,v,this));
                GameUtils.gameObjList.add(GameUtils.enShellObjList.get(GameUtils.enShellObjList.size() - 1));
                v += 2;
            }
        }
        //召唤Boss01
        if ((score == 25 || score == 26) && (boss01Obj == null)) {
            boss01Obj = new Boss01Obj(GameUtils.bossImg001,250,25,157,109,5,this);
            GameUtils.gameObjList.add(boss01Obj);
        }

        //召唤Boss02
        if ((score == 75 || score == 76) && (boss02Obj == null)) {
            boss02Obj = new Boss02Obj(GameUtils.bossImg002,250,25,197,134,5,this);
            GameUtils.gameObjList.add(boss02Obj);
        }

        //召唤Boss03
        if ((score == 150 || score == 151) && (boss03Obj == null)) {
            boss03Obj = new Boss03Obj(GameUtils.bossImg003,250,25,241,188,5,this);
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
            bullet0401Obj = new Bullet04Obj(GameUtils.enBullet04Img, boss04Obj.getX(), boss04Obj.getY() + 50, 21, 59, 6, this);
            bullet0402Obj = new Bullet04Obj(GameUtils.enBullet04Img, boss04Obj.getX() + 120, boss04Obj.getY() + 50, 21, 59, 6, this);
            GameUtils.enBullet0401ObjList.add(bullet0401Obj);
            GameUtils.enBullet0402ObjList.add(bullet0402Obj);
            GameUtils.gameObj02List.add(GameUtils.enBullet0401ObjList.get(GameUtils.enBullet0401ObjList.size() - 1));
            GameUtils.gameObj02List.add(GameUtils.enBullet0402ObjList.get(GameUtils.enBullet0402ObjList.size() - 1));
        }
        //Boss05子弹
        if ((count % 60 == 0) && (boss05Obj != null)) {
            bullet0501Obj = new Bullet05Obj(GameUtils.enBullet05Img, boss05Obj.getX(), boss05Obj.getY() + 50, 21, 59, 6.5, this);
            bullet0502Obj = new Bullet05Obj(GameUtils.enBullet05Img, boss05Obj.getX() + 120, boss05Obj.getY() + 50, 21, 59, 6.5, this);
            GameUtils.enBullet0501ObjList.add(bullet0501Obj);
            GameUtils.enBullet0502ObjList.add(bullet0502Obj);
            GameUtils.gameObj02List.add(GameUtils.enBullet0501ObjList.get(GameUtils.enBullet0501ObjList.size() - 1));
            GameUtils.gameObj02List.add(GameUtils.enBullet0502ObjList.get(GameUtils.enBullet0502ObjList.size() - 1));
        }
        //Boss06子弹
        if ((count % 60 == 0) && (boss06Obj != null)) {
            bullet0601Obj = new Bullet06Obj(GameUtils.enBullet06Img, boss06Obj.getX(), boss06Obj.getY() + 50, 21, 59, 7, this);
            bullet0602Obj = new Bullet06Obj(GameUtils.enBullet06Img, boss06Obj.getX() + 120, boss06Obj.getY() + 50, 21, 59, 7, this);
            GameUtils.enBullet0601ObjList.add(bullet0601Obj);
            GameUtils.enBullet0602ObjList.add(bullet0602Obj);
            GameUtils.gameObj02List.add(GameUtils.enBullet0601ObjList.get(GameUtils.enBullet0601ObjList.size() - 1));
            GameUtils.gameObj02List.add(GameUtils.enBullet0602ObjList.get(GameUtils.enBullet0602ObjList.size() - 1));
        }
        //敌机
        if (count % Ven02 == 0) {
            enemyObj = new EnemyObj(GameUtils.enemyImg,(int) ((Math.random()*10)*36),0,89,70,5,this);
            GameUtils.enemyObjList.add(enemyObj);
            GameUtils.gameObj02List.add(GameUtils.enemyObjList.get(GameUtils.enemyObjList.size() - 1));
            enemyCount++;
        }
        //敌机子弹
        if (count % Ven02 == 0){
            int v = 7;
            for(int i = 1; i <= 2; i++){
                GameUtils.enShellObjList.add(new EnShellObj((GameUtils.enShellImg),enemyObj.getX()+40,enemyObj.getY()+60,21,25,v,this));
                GameUtils.gameObj02List.add(GameUtils.enShellObjList.get(GameUtils.enShellObjList.size() - 1));
                v += 2;
            }
        }
        //召唤Boss04
        if ((score == 300 || score == 301) && (boss04Obj == null)) {
            boss04Obj = new Boss04Obj(GameUtils.bossImg004,250,25,255,196,6,this);
            GameUtils.gameObj02List.add(boss04Obj);
        }

        //召唤Boss05
        if ((score == 450 || score == 451) && (boss05Obj == null)) {
            boss05Obj = new Boss05Obj(GameUtils.bossImg005,250,25,226,197,7,this);
            GameUtils.gameObj02List.add(boss05Obj);
        }

        //召唤Boss06
        if ((score == 600 || score == 601) && (boss06Obj == null)) {
            boss06Obj = new Boss06Obj(GameUtils.bossImg006, 250, 25, 157, 109, 8, this);
            GameUtils.gameObj02List.add(boss06Obj);
        }
    }
    public static void main(String[] args) {
        AirWars airWars = new AirWars();
        airWars.launch();
    }
}