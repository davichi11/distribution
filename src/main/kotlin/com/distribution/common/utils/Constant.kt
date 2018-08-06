package com.distribution.common.utils

/**
 * 常量
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
object Constant {
    /**
     * 超级管理员ID
     */
    val SUPER_ADMIN = 1

    /**
     * 菜单类型
     *
     * @author ChunLiang Hu
     * @email davichi2009@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    enum class MenuType private constructor(val value: Int) {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2)
    }

    /**
     * 定时任务状态
     *
     * @author ChunLiang Hu
     * @email davichi2009@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    enum class ScheduleStatus private constructor(val value: Int) {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1)
    }

    /**
     * 云服务商
     */
    enum class CloudService private constructor(val value: Int) {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3)
    }

    /**
     * 业务类型
     */
    enum class PayType private constructor(val value: String) {
        /**
         * 提现
         */
        WITHDRAWAL("0"),
        /**
         * 购买
         */
        BUY("1"),
        /**
         * 申请信用卡
         */
        applyCard("2"),
        /**
         * 申请借款
         */
        applyLoan("3")
    }

}
