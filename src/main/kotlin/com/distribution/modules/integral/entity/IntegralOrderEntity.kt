package com.distribution.modules.integral.entity

import java.io.Serializable


/**
 * 积分兑换申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
data class IntegralOrderEntity(
        var id: String = "",
        /**
         * 兑换人手机号
         */
        var mobile: Long = 0,
        /**
         * 兑换产品ID
         */
        var detailId: String = "",
        var prodName: String = "",
        var prodTypeName: String = "",
        var userName: String = "",
        var idCode: String = "",
        /**
         * 上传短信记录
         */
        var record: String = "",
        var disLevel: Int = 0,
        /**
         * 卷码图片,可以逗号分隔
         */
        var img: String = "",
        /**
         * 申请时间
         */
        var addTime: String = "",
        /**
         * 审核状态
         */
        var status: Int = 0,
        /**
         * 分润金额
         */
        var profiMoney: Double = 0.00

) : Serializable

