package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 13:11
 */
@AllArgsConstructor
@Getter
public enum RedisPrefix {

    TOKEN("token_", "登录之后分配的  token"),
    USER("userid_", "登录之后分配的  存 user和 token 的关系"),
    SMS_REDIS_PREFIX("sms_","sms短信信息"),
    SPONSOR("sponsor_id_", "登录后分配的赞助商token前缀"),
    SPONSOR_TOKEN("sponsor_token_", "登录后分配的赞助商token前缀"),
    SPONSOR_EXCHANGE_TOKEN("sponsor_exchange_token_", "登录后分配的赞助商兑换端token前缀"),
    CHECK_MAN_TOKEN("check_man_token_", "登录后分配验票员的token前缀"),
    EXPRESS("express_", "快递信息"),
    RANKING_USER("ranking", "排行榜前三名");


    private String code;
    private String message;


}
