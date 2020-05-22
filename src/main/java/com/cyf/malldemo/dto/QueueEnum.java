package com.cyf.malldemo.dto;

import lombok.Getter;

/**
 * 消息队列枚举配置
 *
 * @author by cyf
 * @date 2020/5/22.
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("mall.order.direct","mall.order.cancel","mall.order.cancel"),
    /**
     * 消息通知ttl
     */
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl","mall.order.cancel.ttl","mall.order.cancel.ttl");
    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名字
     */
    private String name;
    /**
     * 路由键
     */
    private String routekey;

    QueueEnum(String exchange, String name, String routekey) {
        this.exchange = exchange;
        this.name = name;
        this.routekey = routekey;
    }
}
