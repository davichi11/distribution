/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo;


import com.distribution.pojo.tables.CardInfo;
import com.distribution.pojo.tables.CardOrderInfo;
import com.distribution.pojo.tables.DisDictionary;
import com.distribution.pojo.tables.DisFans;
import com.distribution.pojo.tables.DisMemberInfo;
import com.distribution.pojo.tables.DisProfiParam;
import com.distribution.pojo.tables.DisProfitRecord;
import com.distribution.pojo.tables.District;
import com.distribution.pojo.tables.IntegralOrder;
import com.distribution.pojo.tables.MemberAccount;
import com.distribution.pojo.tables.MemberAccountHistory;
import com.distribution.pojo.tables.MemeberInfo;
import com.distribution.pojo.tables.MobileMessage;
import com.distribution.pojo.tables.OrderHistory;
import com.distribution.pojo.tables.PosApply;
import com.distribution.pojo.tables.PosOrderInfo;
import com.distribution.pojo.tables.ProductDetail;
import com.distribution.pojo.tables.ProductType;
import com.distribution.pojo.tables.ScheduleJob;
import com.distribution.pojo.tables.ScheduleJobLog;
import com.distribution.pojo.tables.SysConfig;
import com.distribution.pojo.tables.SysLog;
import com.distribution.pojo.tables.SysMenu;
import com.distribution.pojo.tables.SysOss;
import com.distribution.pojo.tables.SysRole;
import com.distribution.pojo.tables.SysRoleMenu;
import com.distribution.pojo.tables.SysUser;
import com.distribution.pojo.tables.SysUserRole;
import com.distribution.pojo.tables.SysUserToken;
import com.distribution.pojo.tables.TbToken;
import com.distribution.pojo.tables.TbUser;
import com.distribution.pojo.tables.WithdrawalInfo;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in distribution
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * 可办信用卡信息表
     */
    public static final CardInfo CARD_INFO = com.distribution.pojo.tables.CardInfo.CARD_INFO;

    /**
     * 办卡订单信息表
     */
    public static final CardOrderInfo CARD_ORDER_INFO = com.distribution.pojo.tables.CardOrderInfo.CARD_ORDER_INFO;

    /**
     * The table <code>distribution.district</code>.
     */
    public static final District DISTRICT = com.distribution.pojo.tables.District.DISTRICT;

    /**
     * 字典表
     */
    public static final DisDictionary DIS_DICTIONARY = com.distribution.pojo.tables.DisDictionary.DIS_DICTIONARY;

    /**
     * 锁粉表
     */
    public static final DisFans DIS_FANS = com.distribution.pojo.tables.DisFans.DIS_FANS;

    /**
     * 用户表
     */
    public static final DisMemberInfo DIS_MEMBER_INFO = com.distribution.pojo.tables.DisMemberInfo.DIS_MEMBER_INFO;

    /**
     * 分润记录表
     */
    public static final DisProfitRecord DIS_PROFIT_RECORD = com.distribution.pojo.tables.DisProfitRecord.DIS_PROFIT_RECORD;

    /**
     * 分润参数设置
     */
    public static final DisProfiParam DIS_PROFI_PARAM = com.distribution.pojo.tables.DisProfiParam.DIS_PROFI_PARAM;

    /**
     * 积分兑换申请表
     */
    public static final IntegralOrder INTEGRAL_ORDER = com.distribution.pojo.tables.IntegralOrder.INTEGRAL_ORDER;

    /**
     * 会员账户表
     */
    public static final MemberAccount MEMBER_ACCOUNT = com.distribution.pojo.tables.MemberAccount.MEMBER_ACCOUNT;

    /**
     * 用户金额变化表
     */
    public static final MemberAccountHistory MEMBER_ACCOUNT_HISTORY = com.distribution.pojo.tables.MemberAccountHistory.MEMBER_ACCOUNT_HISTORY;

    /**
     * 会员信息表
     */
    public static final MemeberInfo MEMEBER_INFO = com.distribution.pojo.tables.MemeberInfo.MEMEBER_INFO;

    /**
     * 发送短信信息表
     */
    public static final MobileMessage MOBILE_MESSAGE = com.distribution.pojo.tables.MobileMessage.MOBILE_MESSAGE;

    /**
     * 升级会员 交易记录
     */
    public static final OrderHistory ORDER_HISTORY = com.distribution.pojo.tables.OrderHistory.ORDER_HISTORY;

    /**
     * POS机申请表
     */
    public static final PosApply POS_APPLY = com.distribution.pojo.tables.PosApply.POS_APPLY;

    /**
     * The table <code>distribution.pos_order_info</code>.
     */
    public static final PosOrderInfo POS_ORDER_INFO = com.distribution.pojo.tables.PosOrderInfo.POS_ORDER_INFO;

    /**
     * 积分兑换产品列表
     */
    public static final ProductDetail PRODUCT_DETAIL = com.distribution.pojo.tables.ProductDetail.PRODUCT_DETAIL;

    /**
     * 积分兑换产品类型表
     */
    public static final ProductType PRODUCT_TYPE = com.distribution.pojo.tables.ProductType.PRODUCT_TYPE;

    /**
     * ????
     */
    public static final ScheduleJob SCHEDULE_JOB = com.distribution.pojo.tables.ScheduleJob.SCHEDULE_JOB;

    /**
     * ??????
     */
    public static final ScheduleJobLog SCHEDULE_JOB_LOG = com.distribution.pojo.tables.ScheduleJobLog.SCHEDULE_JOB_LOG;

    /**
     * ???????
     */
    public static final SysConfig SYS_CONFIG = com.distribution.pojo.tables.SysConfig.SYS_CONFIG;

    /**
     * ?????
     */
    public static final SysLog SYS_LOG = com.distribution.pojo.tables.SysLog.SYS_LOG;

    /**
     * ???
     */
    public static final SysMenu SYS_MENU = com.distribution.pojo.tables.SysMenu.SYS_MENU;

    /**
     * ????
     */
    public static final SysOss SYS_OSS = com.distribution.pojo.tables.SysOss.SYS_OSS;

    /**
     * ??
     */
    public static final SysRole SYS_ROLE = com.distribution.pojo.tables.SysRole.SYS_ROLE;

    /**
     * ?????????
     */
    public static final SysRoleMenu SYS_ROLE_MENU = com.distribution.pojo.tables.SysRoleMenu.SYS_ROLE_MENU;

    /**
     * ????
     */
    public static final SysUser SYS_USER = com.distribution.pojo.tables.SysUser.SYS_USER;

    /**
     * ?????????
     */
    public static final SysUserRole SYS_USER_ROLE = com.distribution.pojo.tables.SysUserRole.SYS_USER_ROLE;

    /**
     * ????Token
     */
    public static final SysUserToken SYS_USER_TOKEN = com.distribution.pojo.tables.SysUserToken.SYS_USER_TOKEN;

    /**
     * ??Token
     */
    public static final TbToken TB_TOKEN = com.distribution.pojo.tables.TbToken.TB_TOKEN;

    /**
     * ??
     */
    public static final TbUser TB_USER = com.distribution.pojo.tables.TbUser.TB_USER;

    /**
     * 提现信息表
     */
    public static final WithdrawalInfo WITHDRAWAL_INFO = com.distribution.pojo.tables.WithdrawalInfo.WITHDRAWAL_INFO;
}
