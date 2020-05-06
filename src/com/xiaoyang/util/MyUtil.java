package com.xiaoyang.util;

import java.awt.*;

/**
 * 工具类
 *
 * @author: Mr.Yang
 * @create: 2020-05-02 17:32
 **/
public class MyUtil {

    private MyUtil() {
    }

    /**
     * 得到指定区间的随机数
     *
     * @param min 区间最小值，包含
     * @param max 区间最大值，不包含
     * @return
     */
    public static final int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * 得到随机的颜色
     *
     * @return
     */
    public static final Color getRandomColor() {
        int rec = getRandomNumber(0, 256);
        int blue = getRandomNumber(0, 256);
        int green = getRandomNumber(0, 256);
        return new Color(rec, blue, green);
    }

    /**
     * 获得一张图片
     *
     * @param URL 图片的路径
     * @return Image对象
     */
    public static Image getImage(String URL) {
        return Toolkit.getDefaultToolkit().createImage(URL);
    }

    /**
     * 判断一个点是否在一个正方形内部
     *
     * @param rectX  正方形的中心点的X坐标
     * @param rectY  正方形的中心点的Y坐标
     * @param radius 正方形边长的一半
     * @param pointX 点的X坐标
     * @param pointY 点的Y坐标
     * @return 如果在内部，返回true，否则返回false
     */
    public static final boolean isCollide(int rectX, int rectY, int radius, int pointX, int pointY) {
        //正方形中心点 和 点的 X,Y 轴的距离
        int disX = Math.abs(rectX - pointX);
        int disY = Math.abs(rectY - pointY);
        if (disX < radius && disY < radius)
            return true;
        return false;
    }

    /***********************关于坦克名字**************************/
    private static final String[] ADJECTIVE = {
            "用心", "飞快", "雪白", "着急", "乐观", "主要", "鲜艳", "冰冷", "细心", "奇妙", "水平", "动人",
            "大量", "无知", "礼貌", "暖和", "深情", "正常", "平淡", "光亮", "落后", "大方", "老大", "笔直",
            "刻苦", "晴朗", "专业", "永久", "大气", "知己", "刚好", "相对", "平和", "友好", "广大", "相同",
            "秀丽", "日常", "高级", "勤劳", "负责", "幸运", "难受", "绝对", "及时", "附近", "华丽", "焦急",
            "不幸", "良好", "相互", "惊奇", "自觉", "好久", "卫生", "温和", "亲密", "冲动", "饱满", "好事",
            "正式", "喜庆", "灵活", "开朗", "清洁", "泥泞", "忠诚", "活跃", "生死", "运气", "响亮", "干脆",
            "点滴", "夸张", "丰满", "隐约", "贫困",
    };
    private static final String[] NOUN = {
            "青鱼", "草鱼", "鲢鱼", "鳙鱼", "鲤鱼", "鲫鱼", "海马", "海龙", "黄鳝", "鳕鱼", "鲨鱼", "麝牛",
            "豹子", "琴鸟","猴子", "树懒", "斑马", "小狗", "狐狸", "狗熊", "黑熊", "大象", "老虎 ", "老鼠 ",
            "杜鹃", "啄木鸟", "戴胜", "蜂虎", "犀鸟", "翠鸟", "斑鸠", "原鸽", "雉鸡", "八哥", "松鸦", "麝牛",
            "山龟", "山鳖", "石蛙", "鲵鱼", "蟾蜍", "鳄鱼", "蜥蜴", "地龟", "玳瑁", "大鲵"
    };

    /**
     * 获取一个随机的名字
     * @return 名字
     */
    public static String getName(){
        return ADJECTIVE[getRandomNumber(0,ADJECTIVE.length)] + "de" +NOUN[getRandomNumber(0,NOUN.length)];
    }

}