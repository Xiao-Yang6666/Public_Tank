package com.xiaoyang.game.tank;

import com.xiaoyang.util.MyUtil;

import java.awt.*;

/**
 * @author: Mr.Yang
 * @create: 2020-05-03 15:24
 **/
public class MyTank extends Tank {
    private static Image[] myTankImg;
    //获取自身坦克的图片
    static {
        myTankImg = new Image[4];
        myTankImg[0] = MyUtil.getImage("res/U.png");
        myTankImg[1] = MyUtil.getImage("res/D.png");
        myTankImg[2] = MyUtil.getImage("res/L.png");
        myTankImg[3] = MyUtil.getImage("res/R.png");
    }

    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
    }

    //绘制自己的坦克
    @Override
    public void drawImg(Graphics g) {
        g.drawImage(myTankImg[getDir()], getX() - getRADIUS(), getY() - getRADIUS(), null);
    }

}