package com.xiaoyang.util;

import com.xiaoyang.game.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * 子弹对象池
 *
 * @author: Mr.Yang
 * @create: 2020-05-03 03:14
 **/
public class BulletsPool {
    //池塘的默认大小
    public static final int DEFAULT_POOL_SIZE = 200;
    public static final int POOL_MAX_SIZE = 300;
    //用于保存所有的子弹对象 (池塘)
    private static List<Bullet> pool = new ArrayList<>();

    //在类加载的时候创建200个子弹出来
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Bullet());
        }
    }

    /**
     * 从池塘中获取一个子弹对象
     *
     * @return
     */
    public static Bullet get() {
        Bullet bullet = null;
        //池塘中没有对象了
        if (pool.size() == 0) {
            bullet = new Bullet();
        } else {//还有对象就从第一个位置拿走
            bullet = pool.remove(0);
        }
        return bullet;
    }

    //子弹用完之后，归还到池塘中
    public static void theReturn(Bullet bullet) {
        //达到池塘的上限后不再归还
        if (pool.size() == POOL_MAX_SIZE){
            return;
        }
        pool.add(bullet);
    }

}