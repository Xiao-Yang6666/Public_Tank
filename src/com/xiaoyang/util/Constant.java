package com.xiaoyang.util;

import java.awt.*;
import java.security.PublicKey;

/**
 * @author: Mr.Yang
 * @create: 2020-05-02 14:37
 **/
public class Constant {
    /*******************************游戏窗口相关**************/
    public static final String GAME_TITLE = "坦克大战";

    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 600;

    //动态获取电脑屏幕的宽高
    public static final int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static final int FRAME_X = SCREEN_W - FRAME_WIDTH >> 1;
    public static final int FRAME_Y = SCREEN_H - FRAME_HEIGHT >> 1;


    /********************************游戏菜单相关的**************/
    public static final int STATE_MENU = 0;
    public static final int STATE_HELP = 1;
    public static final int STATE_ABOUT = 2;
    public static final int STATE_RUN = 3;
    public static final int STATE_OVER = 4;

    public static final String[] MENUS = {
            "开始游戏",
            "继续游戏",
            "游戏帮助",
            "游戏关于",
            "退出游戏",
    };

    //字体
    public static final Font FONT = new Font("宋体", Font.BOLD, 24);
    public static final Font NAME_FONT = new Font("宋体", Font.BOLD, 12);

    //刷新率 刷新间隔 ms
    public static final int REPAINT_INTERVAL = 30;

    //敌人的ai控制的间隔 ms
    public static final int ENEMY_AI_INTERVAL = 2000;
    //敌人发射子弹的概率
    public static final double ENEMT_FIRE_PERCENT = 0.05;

    //最多多少个敌人
    public static final int ENEMY_MAX_COUNT = 10;
    //产生的间隔 ms
    public static final int ENEMY_BORN_INTERVAL = 5000;


}
