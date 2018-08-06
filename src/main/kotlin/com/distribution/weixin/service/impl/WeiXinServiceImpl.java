package com.distribution.weixin.service.impl;

import com.distribution.weixin.service.WeiXinService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.weixin.service.impl
 * @Description TODO(描述)
 * @create 2018/5/27-16:10
 */
@Slf4j
@Service
public class WeiXinServiceImpl implements WeiXinService {
    @Autowired
    private WxMpService wxMpService;

    /**
     * 发送模板消息
     *
     * @param wxMpTemplateMessage 模板消息
     * @return
     */
    @Override
    public String sendTemplateMsg(WxMpTemplateMessage wxMpTemplateMessage) {
        if (StringUtils.isBlank(wxMpTemplateMessage.getToUser())) {
            return "用户openID不能为空";
        }
        try {
            String templateMsg = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
            log.info("消息发送成功,返回{}", templateMsg);
            return templateMsg;
        } catch (WxErrorException e) {
            log.error("消息发送异常", e);
            return "消息发送异常";
        }
    }
}
