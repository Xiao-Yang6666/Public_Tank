package com.xiaoyang.game;

import com.xiaoyang.game.tank.Tank;
import com.xiaoyang.util.Constant;
import com.xiaoyang.util.MyUtil;

import java.awt.*;

/**
 * 子弹类
 *
 * @author: Mr.Yang
 * @create: 2020-05-03 02:21
 **/
public class Bullet {
    //默认子弹速度
    private static final int DEFAULT_SPEED = Tank.DEFAULT_SPEED << 1;
    //子弹半径
    private static final int RADIUS = 4;

    //获取子弹的图片
    private static Image bulletImage;

    static {
        bulletImage = MyUtil.getImage("res/bullet.png");
    }

    private int x, y;
    private int speed = DEFAULT_SPEED;
    private int atk;
    private int dir;

    private boolean visible = true;

    public Bullet(int x, int y, int atk, int dir) {
        this.x = x;
        this.y = y;
        this.atk = atk;
        this.dir = dir;
        visible = true;
    }

    //创建一个无参构造方法(池塘使用)
    public Bullet() {
    }

    /**
     * 炮弹的绘制方法
     *
     * @param g
     */
    public void draw(Graphics g) {
        if (!visible) return;

        logic();
        g.drawImage(bulletImage, x - RADIUS, y - RADIUS, null);
    }

    /**
     * 子弹的逻辑
     */
    private void logic() {
        move();
    }

    /**
     * 子弹的移动
     */
    private void move() {
        switch (dir) {
            case Tank.DIR_UP:
                y -= speed;
                if (y < 0) {
                    visible = false;
                }
                break;
            case Tank.DIR_DOWN:
                y += speed;
                if (y > Constant.FRAME_HEIGHT) {
                    visible = false;
                }
                break;
            case Tank.DIR_LEFT:
                x -= speed;
                if (x < 0) {
                    visible = false;
                }
                break;
            case Tank.DIR_RIGHT:
                x += speed;
                if (x > Constant.FRAME_WIDTH) {
                    visible = false;
                }
                break;
        }
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean exhibit) {
        visible = exhibit;
    }
}