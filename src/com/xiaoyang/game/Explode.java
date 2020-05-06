package com.xiaoyang.game;

import com.xiaoyang.util.MyUtil;

import java.awt.*;

/**
 * 爆炸效果
 *
 * @author: Mr.Yang
 * @create: 2020-05-03 18:07
 **/
public class Explode {
    public static final int EXPLODE_FRAME_COUNT = 4;
    private static Image[] img;

    //爆炸效果图片的宽度和高度
    private static int explodeWidth;
    private static int explodeHeight;

    static {
        img = new Image[EXPLODE_FRAME_COUNT];
        for (int i = 0; i < EXPLODE_FRAME_COUNT; i++) {
            img[i] = MyUtil.getImage("res/blast" + i + ".png");
        }
    }

    //爆炸效果的属性
    private int x, y;

    //当前播放的帧的下标
    private int index;

    private boolean visible;

    public void draw(Graphics g) {
        if (explodeWidth<=0){
            explodeWidth = img[0].getWidth(null)>>1;
            explodeHeight = img[0].getHeight(null)>>1;
        }

        if (!visible) return;
        g.drawImage(img[index], x-explodeWidth, y-explodeHeight, null);
        index++;
        //播放最后一帧的后 设置不可见
        if (index >= EXPLODE_FRAME_COUNT) {
            visible = false;
            index = 0;
        }
    }

    public Explode() {
        index = 0;
        visible = true;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        index = 0;
        visible = true;
    }

    public static int getExplodeFrameCount() {
        return EXPLODE_FRAME_COUNT;
    }

    public static Image[] getImg() {
        return img;
    }

    public static void setImg(Image[] img) {
        Explode.img = img;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}