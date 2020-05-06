package com.xiaoyang.util;

import com.xiaoyang.game.tank.EnemyTank;
import com.xiaoyang.game.tank.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * 敌人坦克对象池
 *
 * @author: Mr.Yang
 * @create: 2020-05-04 16:23
 **/
public class EnemyTanksPool {
    //池塘的默认大小
    public static final int DEFAULT_POOL_SIZE = 20;
    public static final int POOL_MAX_SIZE = 20;
    //用于保存所有的坦克对象 (池塘)
    private static List<Tank> pool = new ArrayList<>();

    //在类加载的时候创建20个坦克出来
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new EnemyTank());
        }
    }

    /**
     * 从池塘中获取一个爆炸对象
     *
     * @return
     */
    public static Tank get() {
        Tank tank = null;
        //池塘中没有对象了
        if (pool.size() == 0) {
            tank = new EnemyTank();
        } else {//还有对象就从第一个位置拿走
            tank = pool.remove(0);
        }
        return tank;
    }

    //坦克死亡之后，归还到池塘中
    public static void theReturn(Tank tank) {
        //达到池塘的上限后不再归还
        if (pool.size() == POOL_MAX_SIZE) {
            return;
        }
        pool.add(tank);
    }

}
