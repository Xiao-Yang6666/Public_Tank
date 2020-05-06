package com.xiaoyang.game.tank;

import com.xiaoyang.game.GameFrame;
import com.xiaoyang.util.Constant;
import com.xiaoyang.util.EnemyTanksPool;
import com.xiaoyang.util.MyUtil;

import java.awt.*;

/**
 * @author: Mr.Yang
 * @create: 2020-05-03 15:37
 **/
public class EnemyTank extends Tank {
    private static Image[] enemyTankImg;

    static {
        enemyTankImg = new Image[4];
        enemyTankImg[0] = MyUtil.getImage("res/enemy1U.png");
        enemyTankImg[1] = MyUtil.getImage("res/enemy1D.png");
        enemyTankImg[2] = MyUtil.getImage("res/enemy1L.png");
        enemyTankImg[3] = MyUtil.getImage("res/enemy1R.png");
    }

    //计时器
    private long aiTime;

    private EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
        //敌人创建的时候开始计时
        aiTime = System.currentTimeMillis();
    }

    public EnemyTank() {
        aiTime = System.currentTimeMillis();
    }

    /**
     * 用于创建敌人坦克
     *
     * @return
     */
    public static Tank createEnemy() {
        int x = MyUtil.getRandomNumber(0, 2) == 0 ? getRADIUS() + GameFrame.leftBarH
                : Constant.FRAME_WIDTH - getRADIUS() - GameFrame.rightBarH;
        int y = GameFrame.titleBarH + getRADIUS();
        int dir = DIR_DOWN;
        Tank enemy = EnemyTanksPool.get();
        enemy.setX(x);
        enemy.setY(y);
        enemy.setDir(dir);
        enemy.setEnemy(true);
        enemy.setState(STATE_MOVE);
        enemy.setColor(Color.RED);
        enemy.setHp(Tank.DEFAULT_HP);
        enemy.setName(MyUtil.getName());
        return enemy;
    }

    /**
     * 绘制敌人坦克
     *
     * @param g
     */
    @Override
    public void drawImg(Graphics g) {
        ai();
        g.drawImage(enemyTankImg[getDir()], getX() - getRADIUS(), getY() - getRADIUS(), null);
    }

    /**
     * 敌人坦克的AI
     */
    public void ai() {
        if (System.currentTimeMillis() - aiTime > Constant.ENEMY_AI_INTERVAL) {
            //间隔5秒随机一个状态
            setDir(MyUtil.getRandomNumber(DIR_UP, DIR_RIGHT + 1));
            setState(MyUtil.getRandomNumber(1, 2) == 0 ? STATE_STAND : STATE_MOVE);
            aiTime = System.currentTimeMillis();
        }
        //比较小的概率开火
        if (Math.random() < Constant.ENEMT_FIRE_PERCENT) {
            fire();
        }
    }
}