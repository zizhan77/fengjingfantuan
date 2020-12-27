package com.mem.vo.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 骰子对应的任务
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-02 21:28
 */
public class Dice {
    public static final Map<Integer, String> diceMap = new HashMap<Integer, String>(){{
        put(1,"看广告");
        put(2,"知识问答");
        put(3,"拼图游戏");
        put(4,"看广告");
        put(5,"知识问答");
        put(6,"拼图游戏");
    }
    };
}
