package com.xiaoyang.game.tank;

import com.xiaoyang.game.Bullet;
import com.xiaoyang.game.Explode;
import com.xiaoyang.game.GameFrame;
import com.xiaoyang.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 坦克类
 *
 * @author: Mr.Yang
 * @create: 2020-05-02 17:14
 **/
public abstract class Tank {
    //四个方向
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LEFT = 2;
    public static final int DIR_RIGHT = 3;

    //半径
    private static final int RADIUS = 20;
    //默认速度(每一帧的速度) 30ms
    public static final int DEFAULT_SPEED = 4;
    //坦克的状态
    public static final int STATE_STAND = 0;
    public static final int STATE_MOVE = 1;
    public static final int STATE_DIE = 2;
    //坦克的初始生命
    public static final int DEFAULT_HP = 1000;

    private int x, y;

    private String name;
    private int hp = DEFAULT_HP;
    private int atk;
    private static final int ATK_MAX = 100;
    private static final int ATK_MIN = 50;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int state = STATE_STAND;
    private Color color;
    private boolean enemy;

    //血条对象
    private BloodBar bar = new BloodBar();

    //炮弹
    private List<Bullet> bullets = new ArrayList();
    //保存所有爆炸效果
    private List<Explode> explodes = new ArrayList<>();

    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        initTank();
    }

    public Tank() {
        initTank();
    }

    //初始化坦克
    protected void initTank() {
        color = Color.WHITE;
        name = MyUtil.getName();
        atk = MyUtil.getRandomNumber(ATK_MIN, ATK_MAX);
    }


    /**
     * 绘制坦克
     *
     * @param g
     */
    public void draw(Graphics g) {
        logic();

        drawImg(g);

        drawBullets(g);

        drawName(g);

        bar.draw(g);
    }

    /*通过图片绘制坦克*/
    public abstract void drawImg(Graphics g);

    /**
     * 将当前坦克发射的子弹绘制出来
     *
     * @param g
     */
    void drawBullets(Graphics g) {
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        //将不可见的子弹移除并归还给对象池
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (!bullet.getVisible()) {
                Bullet remove = bullets.remove(i);
                i--;
                BulletsPool.theReturn(remove);
            }
        }
    }

    /**
     * 绘制出坦克的名字
     *
     * @param g
     */
    private void drawName(Graphics g) {
        g.setColor(color);
        g.setFont(Constant.NAME_FONT);
        g.drawString(name, x - RADIUS - (RADIUS >> 1), y - RADIUS - 22);
    }

    //坦克的逻辑处理
    void logic() {
        switch (state) {
            case STATE_STAND:
                break;
            case STATE_MOVE:
                move();
                break;
            case STATE_DIE:
                break;
        }
    }

    //坦克的移动处理
    private void move() {
        switch (dir) {
            case DIR_UP:
                y -= speed;
                if (y <= RADIUS + GameFrame.titleBarH)
                    y = RADIUS + GameFrame.titleBarH;
                break;
            case DIR_DOWN:
                y += speed;
                if (y >= Constant.FRAME_HEIGHT - RADIUS - GameFrame.downBarH)
                    y = Constant.FRAME_HEIGHT - RADIUS - GameFrame.downBarH;
                break;
            case DIR_LEFT:
                x -= speed;
                if (x <= RADIUS + GameFrame.leftBarH)
                    x = RADIUS + GameFrame.leftBarH;
                break;
            case DIR_RIGHT:
                x += speed;
                if (x >= Constant.FRAME_WIDTH - RADIUS - GameFrame.rightBarH)
                    x = Constant.FRAME_WIDTH - RADIUS - GameFrame.rightBarH;
                break;
        }
    }

    /**
     * 坦克开火的方法
     * 创建子弹对象，并放在lisk集合中；
     */
    public void fire() {
        int bulletX = x;
        int bulletY = y;
        switch (dir) {
            case Tank.DIR_UP:
                bulletY -= RADIUS;
                break;
            case Tank.DIR_DOWN:
                bulletY += RADIUS;
                break;
            case Tank.DIR_LEFT:
                bulletX -= RADIUS;
                break;
            case Tank.DIR_RIGHT:
                bulletX += RADIUS;
                break;
        }
        //从对象池中获取对象
        Bullet bullet = BulletsPool.get();
        //给子弹赋值
        bullet.setX(bulletX);
        bullet.setY(bulletY);
        bullet.setAtk(atk);
        bullet.setDir(dir);
        bullet.setVisible(true);

        bullets.add(bullet);
    }


    //坦克和敌人子弹的碰撞
    public void collideBullets(List<Bullet> bullets) {
        //遍历所有子弹和当前坦克进行碰撞检测
        for (Bullet bullet : bullets) {
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();

            if (MyUtil.isCollide(x, y, RADIUS, bulletX, bulletY)) {
                //子弹消失
                bullet.setVisible(false);
                //坦克受到伤害
                hurt(bullet);
                //爆炸效果
                Explode explode = ExplodesPool.get();
                explode.setX(x);
                explode.setY(y);
                explode.setVisible(true);
                explodes.add(explode);
            }
        }
    }

    /**
     * 坦克受到伤害
     *
     * @param bullet 子弹
     */
    private void hurt(Bullet bullet) {
        final int atk = bullet.getAtk();
        hp -= atk;
        if (hp < 0) {
            hp = 0;
            die();
        }
    }

    //死亡后
    protected void die() {
        //敌人坦克死亡 归还对象池
        if (isEnemy()) {
            EnemyTanksPool.theReturn(this);
        } else {//玩家死亡 todo

        }
    }

    /**
     * 判断坦克是否死亡
     * @return
     */
    public boolean isDie() {
        return hp <= 0;
    }

    /**
     * 绘制当前坦克的所有的爆炸效果
     *
     * @param g
     */
    public void drawExplodes(Graphics g) {
        for (Explode explode : explodes) {
            explode.draw(g);
        }
        //将不可见的爆炸效果还回池塘
        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            if (!explode.isVisible()) {
                explodes.remove(i);
                i--;
                ExplodesPool.theReturn(explode);
            }
        }
    }


    //内部类，坦克的血量
    class BloodBar {
        public static final int BAR_LENGTH = RADIUS * 3;
        public static final int BAR_HEIGHT = 5;

        public void draw(Graphics g) {
            //绘制血条背景
            g.setColor(Color.YELLOW);
            g.fillRect(x - RADIUS - (RADIUS >> 1), y - RADIUS - BAR_HEIGHT * 2, BAR_LENGTH, BAR_HEIGHT);

            //绘制血条
            g.setColor(color);
            g.fillRect(x - RADIUS - (RADIUS >> 1), y - RADIUS - BAR_HEIGHT * 2, hp * BAR_LENGTH / DEFAULT_HP, BAR_HEIGHT);

            //绘制血条边框
            g.setColor(Color.GRAY);
            g.drawRect(x - RADIUS - (RADIUS >> 1), y - RADIUS - BAR_HEIGHT * 2, BAR_LENGTH, BAR_HEIGHT);
        }

    }


    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public static int getRADIUS() {
        return RADIUS;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Color getColor(Color red) {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }
}