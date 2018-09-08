package com.distribution.weixin.service;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 微信公众号,支付服务类
 *
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.weixin.service
 * @Description TODO(描述)
 * @create 2018/5/27-16:07
 */
public interface WeiXinService {
    /**
     * 发送模板消息
     *
     * @param wxMpTemplateMessage 模板消息
     * @return
     */
    String sendTemplateMsg(WxMpTemplateMessage wxMpTemplateMessage);
}
