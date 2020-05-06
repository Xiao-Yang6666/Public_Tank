package com.xiaoyang.game;

import com.xiaoyang.game.tank.EnemyTank;
import com.xiaoyang.game.tank.MyTank;
import com.xiaoyang.game.tank.Tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.xiaoyang.util.Constant.*;

/**
 * 游戏的主窗口类
 * 所有游戏中需要展示的内容
 *
 * @author: Mr.Yang
 * @create: 2020-05-02 14:37
 **/
public class GameFrame extends Frame implements Runnable {
    //1:声明一张和屏幕大小一致的图片
    private BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    //游戏状态
    public static int gameState;
    //菜单指向
    public static int menuIndex;
    //窗口边框
    public static int titleBarH;
    public static int leftBarH;
    public static int rightBarH;
    public static int downBarH;


    //定义坦克对象
    private Tank myTank;
    //定义一个敌人坦克对象
    private Tank enemyTank;
    //定义一个敌人坦克集合
    private List<Tank> enemies = new ArrayList();

    /**
     * 对窗口进行初始化
     */
    public GameFrame() {
        //初始化窗口
        initFrame();
        //窗口监听
        initEventListener();
        //启动刷新率线程
        new Thread(this).start();
    }

    /**
     * 对游戏进行初始化
     */
    public void initGame() {
        gameState = STATE_MENU;
    }

    /**
     * 属性进行初始化
     */
    private void initFrame() {
        //设置标题
        setTitle(GAME_TITLE);
        //设置窗口大小
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        //设置显示位置
        setLocation(FRAME_X, FRAME_Y);
        //设置窗口不可改变
        setResizable(false);
        //设置窗口可见
        setVisible(true);
        //四面边框
        titleBarH = getInsets().top;
        leftBarH = getInsets().left;
        rightBarH = getInsets().right;
        downBarH = getInsets().bottom;
    }

    /**
     * 所有绘制的内容，不能主动调用
     * 只能通过repaint();去回调该方法
     * <p>
     * 通过双缓冲技术解决页面闪烁
     *
     * @param g1
     */
    public void update(Graphics g1) {
        //2:获取到图片的画笔
        Graphics g = bufImg.createGraphics();

        //3:先全部绘制到图片上
        g.setFont(FONT);
        switch (gameState) {
            case STATE_MENU:
                drawMenu(g);
                break;
            case STATE_HELP:
                drawHelp(g);
                break;
            case STATE_ABOUT:
                drawAbout(g);
                break;
            case STATE_RUN:
                drawRun(g);
                break;
            case STATE_OVER:
                drawOver(g);
                break;
        }

        //4:使用系统画笔将该图片绘制到屏幕上
        g1.drawImage(bufImg, 0, 0, null);
    }

    /**
     * 结束界面
     *
     * @param g
     */
    private void drawOver(Graphics g) {

    }

    /**
     * 游戏运行界面
     *
     * @param g
     */
    private void drawRun(Graphics g) {
        //绘制背景
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        //绘制自己的坦克
        myTank.draw(g);
        //绘制敌人坦克
        drawEnemies(g);
        //子弹和坦克的碰撞
        bulletCollideTank();
        //爆炸效果
        drawExplodes(g);
    }

    //绘制所有敌人坦克
    private void drawEnemies(Graphics g){
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            //如果坦克死亡就不再绘制 并移出集合
            if (enemy.isDie()){
                enemies.remove(i);
                i--;
                continue;
            }
            enemy.draw(g);
        }
    }


    /**
     * 关于界面
     *
     * @param g
     */
    private void drawAbout(Graphics g) {

    }

    /**
     * 游戏帮助界面
     *
     * @param g
     */
    private void drawHelp(Graphics g) {

    }

    /**
     * 菜单界面
     *
     * @param g 画笔对象
     */
    private void drawMenu(Graphics g) {
        //绘制背景
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        //绘制选项
        final int STR_WIDTH = 80;
        int x = FRAME_WIDTH - STR_WIDTH >> 1;
        int y = FRAME_HEIGHT / 3;
        final int DIS = 45;
        for (int i = 0; i < MENUS.length; i++) {
            if (i == menuIndex) {//选中的选项变成红色
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(MENUS[i], x, y + DIS * i);
        }
    }

    /**
     * 初始化监听事件
     */
    private void initEventListener() {
        //对窗口进行监听
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //按键监听
        addKeyListener(new KeyAdapter() {
            //按键按下的时候回调的方法
            @Override
            public void keyPressed(KeyEvent e) {
                //获取按下键的键值
                int keyCode = e.getKeyCode();
                switch (gameState) {
                    case STATE_MENU:
                        keyPressedEventMenu(keyCode);
                        break;
                    case STATE_HELP:
                        keyPressedEventHelp(keyCode);
                        break;
                    case STATE_ABOUT:
                        keyPressedEventAbout(keyCode);
                        break;
                    case STATE_RUN:
                        keyPressedEventRun(keyCode);
                        break;
                    case STATE_OVER:
                        keyPressedEventOver(keyCode);
                        break;
                }
            }

            //按键松开的时候回调的方法
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (gameState == STATE_RUN) {
                    keyReleasedRun(keyCode);
                }
            }
        });
    }

    //四键统计 解决松开按键立正的 BUG
    private int upSum = -1;
    private int deSum = -1;
    private int lepSum = -1;
    private int riSum = -1;

    //游戏界面 按键松开的逻辑
    private void keyReleasedRun(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                upSum--;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                deSum--;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                lepSum--;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                riSum--;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

        }

        if (upSum < 0 && deSum < 0 && lepSum < 0 && riSum < 0) {
            myTank.setState(Tank.STATE_STAND);
        }
    }


    /**
     * 结束界面按键逻辑
     *
     * @param keyCode
     */
    private void keyPressedEventOver(int keyCode) {

    }

    /**
     * 游戏界面按键逻辑
     *
     * @param keyCode
     */
    private void keyPressedEventRun(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                myTank.setDir(Tank.DIR_UP);
                myTank.setState(Tank.STATE_MOVE);
                if (upSum < 0) {
                    upSum++;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                myTank.setDir(Tank.DIR_DOWN);
                myTank.setState(Tank.STATE_MOVE);
                if (deSum < 0) {
                    deSum++;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                myTank.setDir(Tank.DIR_LEFT);
                myTank.setState(Tank.STATE_MOVE);
                if (lepSum < 0) {
                    lepSum++;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                myTank.setDir(Tank.DIR_RIGHT);
                myTank.setState(Tank.STATE_MOVE);
                if (riSum < 0) {
                    riSum++;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case KeyEvent.VK_SPACE:
                //发射子弹
                myTank.fire();
                break;
        }
    }

    /**
     * 关于界面按键逻辑
     *
     * @param keyCode
     */
    private void keyPressedEventAbout(int keyCode) {

    }

    /**
     * 帮助界面按键逻辑
     *
     * @param keyCode
     */
    private void keyPressedEventHelp(int keyCode) {

    }

    /**
     * 菜单界面按键逻辑
     *
     * @param keyCode
     */
    private void keyPressedEventMenu(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (--menuIndex < 0) {
                    menuIndex = MENUS.length - 1;
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (++menuIndex > MENUS.length - 1) {
                    menuIndex = 0;
                }
                break;
            case KeyEvent.VK_ENTER:
                newGame();
                break;
        }
    }

    /**
     * 开始新游戏的方法
     */
    private void newGame() {
        gameState = STATE_RUN;
        myTank = new MyTank(400, 200, Tank.DIR_UP);
        //enemyTank = new EnemyTank(100,100,Tank.DIR_DOWN);
        //使用一个单独的线程用于控制产生敌人的产生
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (enemies.size() < ENEMY_MAX_COUNT) {
                        Tank enemy = EnemyTank.createEnemy();
                        enemies.add(enemy);
                    }
                    try {
                        Thread.sleep(ENEMY_BORN_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 创建新线程刷新页面
     */
    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(REPAINT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 子弹碰撞
     */
    private void bulletCollideTank(){
        //敌人坦克碰撞我的子弹
        for (Tank enemy : enemies) {
            enemy.collideBullets(myTank.getBullets());
        }
        //我的坦克碰撞敌人的子弹
        for (Tank enemy : enemies) {
            myTank.collideBullets(enemy.getBullets());
        }
    }

    //所有的坦克上的爆炸效果
    private void drawExplodes(Graphics g){
        //敌人的爆炸效果
        for (Tank enemy : enemies) {
            enemy.drawExplodes(g);
        }
        myTank.drawExplodes(g);
    }
}
