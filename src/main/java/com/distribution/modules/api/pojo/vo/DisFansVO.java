package com.distribution.modules.api.pojo.vo;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/5/11-23:12
 */
@Data
public class DisFansVO {
    /**
     * 微信ID
     */
    private String wechatId;

    /**
     * 微信头像
     */
    private String wechatImg;

    /**
     * 微信昵称
     */
    private String wechatNickname;
    /**
     *推荐人ID
     */
    private String memberId;
}
