package com.distribution.weixin.utils

import com.distribution.weixin.service.WeiXinService
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage

/**
 * @Company
 * @Project distribution
 * @Package com.distribution.weixin.utils
 * @Description TODO(描述)
 * @author ChunLiang Hu
 * @create 2018/8/6-14:46
 */
object WxUtils {

    /**
     *构造提现提醒模板信息
     *
     * @param openId
     * @param templateId
     * @param templateDataList
     * @return
     */
    fun buildAndSendTemplateMsg(openId: String, templateId: String, templateDataList: List<WxMpTemplateData>, weiXinService: WeiXinService) {
        val wxMpTemplateMessage = WxMpTemplateMessage()
        wxMpTemplateMessage.templateId = templateId
        wxMpTemplateMessage.data = templateDataList
        wxMpTemplateMessage.toUser = openId
        weiXinService.sendTemplateMsg(wxMpTemplateMessage)
    }
}