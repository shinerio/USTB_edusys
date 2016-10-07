package com.ustb.superui.ustbedu.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jstxzhangrui on 2016/10/4.
 */
public class Lucky_message {
    private static Map<Integer,String> LUCK_MESSAGE = new HashMap<>();
    static {
        LUCK_MESSAGE.put(0,"魔镜,魔镜告诉我谁最帅\n当然是.........\nshine啦");
        LUCK_MESSAGE.put(1,"考试秘诀：\n三长一短选一短\n三短一长选一长");
        LUCK_MESSAGE.put(2,"听说用USTB掌上教务管理系统\n的都是帅哥靓女呢！");
        LUCK_MESSAGE.put(3,"听说用USTB掌上系统掷骰子蒙答案\n正确率更高呢\n一般人，我不告诉他");
        LUCK_MESSAGE.put(4,"书山有路勤为径\n学海无涯苦作舟");
        LUCK_MESSAGE.put(5,"胸怀文墨虚若谷\n腹有诗书气自华");
        LUCK_MESSAGE.put(6,"心，若没有归宿\n走到哪都是流浪");
        LUCK_MESSAGE.put(7,"北科校训：\n求实鼎新");
        LUCK_MESSAGE.put(8,"领略大师风华\n传承北科大精神");
        LUCK_MESSAGE.put(9,"大学可以毕业\n梦想不可以毕业");
        LUCK_MESSAGE.put(10,"宏博，万秀\n美味没有停歇");
        LUCK_MESSAGE.put(11,"求实鼎新\n熔基锻梁");
        LUCK_MESSAGE.put(12,"听说北科大前身是\n北京钢铁学院哦");
        LUCK_MESSAGE.put(13,"莫非阁下就是\n玉树临风美少年\n揽镜自顾不能眠");
        LUCK_MESSAGE.put(14,"志当存高远\n勤勉书春秋");
        LUCK_MESSAGE.put(15,"魔镜,魔镜告诉我谁最帅\n当然是.........\nshine啦");
    }
    public static String getLuckMessage(int num){
        return LUCK_MESSAGE.get(new Integer(num));
    }
}
