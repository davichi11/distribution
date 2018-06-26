package com.distribution.modules.card.entity;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.entity
 * @Description TODO(描述)
 * @create 2018/6/8-20:48
 */
@Data
public class CardApiResults {
    /**
     * maxTime : 最高期限
     */
    private String maxTime;
    /**
     * highestMoney : 最高额度
     */
    private String highestMoney;
    /**
     * 背景图片
     */
    private String backgroundImg;
    /**
     * 最低期限
     */
    private String minTime;
    /**
     * 办卡人数
     */
    private String num;
    /**
     * 名称
     */
    private String name;
    /**
     * 链接
     */
    private String link;
    /**
     * logo图
     */
    private String icon;
    /**
     * 利率
     */
    private String persent;
    /**
     * 产品编号
     */
    private String id;
    private int type;
    /**
     *  内容
     */
    private String content;

    private Integer moneySent;
}
