package com.xiaoyang.util;

import com.xiaoyang.game.Bullet;
import com.xiaoyang.game.Explode;

import java.util.ArrayList;
import java.util.List;

/**
 * 爆炸效果对象池
 *
 * @author: Mr.Yang
 * @create: 2020-05-03 19:01
 **/
public class ExplodesPool {
    //池塘的默认大小
    public static final int DEFAULT_POOL_SIZE = 20;
    public static final int POOL_MAX_SIZE = 30;
    //用于保存所有的爆炸效果对象 (池塘)
    private static List<Explode> pool = new ArrayList<>();

    //在类加载的时候创建20个爆炸效果出来
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Explode());
        }
    }

    /**
     * 从池塘中获取一个爆炸对象
     *
     * @return
     */
    public static Explode get() {
        Explode explode = null;
        //池塘中没有对象了
        if (pool.size() == 0) {
            explode = new Explode();
        } else {//还有对象就从第一个位置拿走
            explode = pool.remove(0);
        }
        return explode;
    }

    //子弹用完之后，归还到池塘中
    public static void theReturn(Explode explode) {
        //达到池塘的上限后不再归还
        if (pool.size() == POOL_MAX_SIZE) {
            return;
        }
        pool.add(explode);
    }

}