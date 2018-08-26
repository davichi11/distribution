/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo;


import com.distribution.pojo.tables.AdPicture;
import com.distribution.pojo.tables.ArticleInfo;
import com.distribution.pojo.tables.CardInfo;
import com.distribution.pojo.tables.CardOrderInfo;
import com.distribution.pojo.tables.DisDictionary;
import com.distribution.pojo.tables.DisFans;
import com.distribution.pojo.tables.DisMemberInfo;
import com.distribution.pojo.tables.DisProfiParam;
import com.distribution.pojo.tables.DisProfitRecord;
import com.distribution.pojo.tables.IntegralOrder;
import com.distribution.pojo.tables.IntegralTutorial;
import com.distribution.pojo.tables.LoanInfo;
import com.distribution.pojo.tables.LoanOrderInfo;
import com.distribution.pojo.tables.MemberAccount;
import com.distribution.pojo.tables.MemberAccountHistory;
import com.distribution.pojo.tables.MemeberInfo;
import com.distribution.pojo.tables.MobileMessage;
import com.distribution.pojo.tables.PosApply;
import com.distribution.pojo.tables.PosOrderInfo;
import com.distribution.pojo.tables.ProductDetail;
import com.distribution.pojo.tables.ProductDetailParams;
import com.distribution.pojo.tables.ProductType;
import com.distribution.pojo.tables.ProductTypeParams;
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
import com.distribution.pojo.tables.records.AdPictureRecord;
import com.distribution.pojo.tables.records.ArticleInfoRecord;
import com.distribution.pojo.tables.records.CardInfoRecord;
import com.distribution.pojo.tables.records.CardOrderInfoRecord;
import com.distribution.pojo.tables.records.DisDictionaryRecord;
import com.distribution.pojo.tables.records.DisFansRecord;
import com.distribution.pojo.tables.records.DisMemberInfoRecord;
import com.distribution.pojo.tables.records.DisProfiParamRecord;
import com.distribution.pojo.tables.records.DisProfitRecordRecord;
import com.distribution.pojo.tables.records.IntegralOrderRecord;
import com.distribution.pojo.tables.records.IntegralTutorialRecord;
import com.distribution.pojo.tables.records.LoanInfoRecord;
import com.distribution.pojo.tables.records.LoanOrderInfoRecord;
import com.distribution.pojo.tables.records.MemberAccountHistoryRecord;
import com.distribution.pojo.tables.records.MemberAccountRecord;
import com.distribution.pojo.tables.records.MemeberInfoRecord;
import com.distribution.pojo.tables.records.MobileMessageRecord;
import com.distribution.pojo.tables.records.PosApplyRecord;
import com.distribution.pojo.tables.records.PosOrderInfoRecord;
import com.distribution.pojo.tables.records.ProductDetailParamsRecord;
import com.distribution.pojo.tables.records.ProductDetailRecord;
import com.distribution.pojo.tables.records.ProductTypeParamsRecord;
import com.distribution.pojo.tables.records.ProductTypeRecord;
import com.distribution.pojo.tables.records.ScheduleJobLogRecord;
import com.distribution.pojo.tables.records.ScheduleJobRecord;
import com.distribution.pojo.tables.records.SysConfigRecord;
import com.distribution.pojo.tables.records.SysLogRecord;
import com.distribution.pojo.tables.records.SysMenuRecord;
import com.distribution.pojo.tables.records.SysOssRecord;
import com.distribution.pojo.tables.records.SysRoleMenuRecord;
import com.distribution.pojo.tables.records.SysRoleRecord;
import com.distribution.pojo.tables.records.SysUserRecord;
import com.distribution.pojo.tables.records.SysUserRoleRecord;
import com.distribution.pojo.tables.records.SysUserTokenRecord;
import com.distribution.pojo.tables.records.TbTokenRecord;
import com.distribution.pojo.tables.records.TbUserRecord;
import com.distribution.pojo.tables.records.WithdrawalInfoRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>distribution_dev</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<AdPictureRecord, Integer> IDENTITY_AD_PICTURE = Identities0.IDENTITY_AD_PICTURE;
    public static final Identity<ArticleInfoRecord, Integer> IDENTITY_ARTICLE_INFO = Identities0.IDENTITY_ARTICLE_INFO;
    public static final Identity<DisDictionaryRecord, Integer> IDENTITY_DIS_DICTIONARY = Identities0.IDENTITY_DIS_DICTIONARY;
    public static final Identity<IntegralTutorialRecord, Integer> IDENTITY_INTEGRAL_TUTORIAL = Identities0.IDENTITY_INTEGRAL_TUTORIAL;
    public static final Identity<ProductDetailParamsRecord, Integer> IDENTITY_PRODUCT_DETAIL_PARAMS = Identities0.IDENTITY_PRODUCT_DETAIL_PARAMS;
    public static final Identity<ProductTypeParamsRecord, Integer> IDENTITY_PRODUCT_TYPE_PARAMS = Identities0.IDENTITY_PRODUCT_TYPE_PARAMS;
    public static final Identity<ScheduleJobRecord, Long> IDENTITY_SCHEDULE_JOB = Identities0.IDENTITY_SCHEDULE_JOB;
    public static final Identity<ScheduleJobLogRecord, Long> IDENTITY_SCHEDULE_JOB_LOG = Identities0.IDENTITY_SCHEDULE_JOB_LOG;
    public static final Identity<SysConfigRecord, Long> IDENTITY_SYS_CONFIG = Identities0.IDENTITY_SYS_CONFIG;
    public static final Identity<SysLogRecord, Long> IDENTITY_SYS_LOG = Identities0.IDENTITY_SYS_LOG;
    public static final Identity<SysMenuRecord, Long> IDENTITY_SYS_MENU = Identities0.IDENTITY_SYS_MENU;
    public static final Identity<SysOssRecord, Long> IDENTITY_SYS_OSS = Identities0.IDENTITY_SYS_OSS;
    public static final Identity<SysRoleRecord, Long> IDENTITY_SYS_ROLE = Identities0.IDENTITY_SYS_ROLE;
    public static final Identity<SysRoleMenuRecord, Long> IDENTITY_SYS_ROLE_MENU = Identities0.IDENTITY_SYS_ROLE_MENU;
    public static final Identity<SysUserRecord, Long> IDENTITY_SYS_USER = Identities0.IDENTITY_SYS_USER;
    public static final Identity<SysUserRoleRecord, Long> IDENTITY_SYS_USER_ROLE = Identities0.IDENTITY_SYS_USER_ROLE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AdPictureRecord> KEY_AD_PICTURE_PRIMARY = UniqueKeys0.KEY_AD_PICTURE_PRIMARY;
    public static final UniqueKey<ArticleInfoRecord> KEY_ARTICLE_INFO_PRIMARY = UniqueKeys0.KEY_ARTICLE_INFO_PRIMARY;
    public static final UniqueKey<CardInfoRecord> KEY_CARD_INFO_PRIMARY = UniqueKeys0.KEY_CARD_INFO_PRIMARY;
    public static final UniqueKey<CardInfoRecord> KEY_CARD_INFO_CARD_INFO_ID_UINDEX = UniqueKeys0.KEY_CARD_INFO_CARD_INFO_ID_UINDEX;
    public static final UniqueKey<CardOrderInfoRecord> KEY_CARD_ORDER_INFO_PRIMARY = UniqueKeys0.KEY_CARD_ORDER_INFO_PRIMARY;
    public static final UniqueKey<CardOrderInfoRecord> KEY_CARD_ORDER_INFO_CARD_ORDER_INFO_ID_UINDEX = UniqueKeys0.KEY_CARD_ORDER_INFO_CARD_ORDER_INFO_ID_UINDEX;
    public static final UniqueKey<DisDictionaryRecord> KEY_DIS_DICTIONARY_PRIMARY = UniqueKeys0.KEY_DIS_DICTIONARY_PRIMARY;
    public static final UniqueKey<DisFansRecord> KEY_DIS_FANS_PRIMARY = UniqueKeys0.KEY_DIS_FANS_PRIMARY;
    public static final UniqueKey<DisFansRecord> KEY_DIS_FANS_DIS_FANS_ID_UINDEX = UniqueKeys0.KEY_DIS_FANS_DIS_FANS_ID_UINDEX;
    public static final UniqueKey<DisMemberInfoRecord> KEY_DIS_MEMBER_INFO_PRIMARY = UniqueKeys0.KEY_DIS_MEMBER_INFO_PRIMARY;
    public static final UniqueKey<DisMemberInfoRecord> KEY_DIS_MEMBER_INFO_DIS_MEMBER_INFO_ID_UINDEX = UniqueKeys0.KEY_DIS_MEMBER_INFO_DIS_MEMBER_INFO_ID_UINDEX;
    public static final UniqueKey<DisProfitRecordRecord> KEY_DIS_PROFIT_RECORD_PRIMARY = UniqueKeys0.KEY_DIS_PROFIT_RECORD_PRIMARY;
    public static final UniqueKey<DisProfitRecordRecord> KEY_DIS_PROFIT_RECORD_DIS_PROFIT_RECORD_ID_UINDEX = UniqueKeys0.KEY_DIS_PROFIT_RECORD_DIS_PROFIT_RECORD_ID_UINDEX;
    public static final UniqueKey<DisProfiParamRecord> KEY_DIS_PROFI_PARAM_PRIMARY = UniqueKeys0.KEY_DIS_PROFI_PARAM_PRIMARY;
    public static final UniqueKey<DisProfiParamRecord> KEY_DIS_PROFI_PARAM_DIS_PROFI_PARAM_ID_UINDEX = UniqueKeys0.KEY_DIS_PROFI_PARAM_DIS_PROFI_PARAM_ID_UINDEX;
    public static final UniqueKey<IntegralOrderRecord> KEY_INTEGRAL_ORDER_PRIMARY = UniqueKeys0.KEY_INTEGRAL_ORDER_PRIMARY;
    public static final UniqueKey<IntegralOrderRecord> KEY_INTEGRAL_ORDER_INTEGRAL_ORDER_ID_UINDEX = UniqueKeys0.KEY_INTEGRAL_ORDER_INTEGRAL_ORDER_ID_UINDEX;
    public static final UniqueKey<IntegralTutorialRecord> KEY_INTEGRAL_TUTORIAL_PRIMARY = UniqueKeys0.KEY_INTEGRAL_TUTORIAL_PRIMARY;
    public static final UniqueKey<LoanInfoRecord> KEY_LOAN_INFO_PRIMARY = UniqueKeys0.KEY_LOAN_INFO_PRIMARY;
    public static final UniqueKey<LoanInfoRecord> KEY_LOAN_INFO_LOAN_INFO_ID_UINDEX = UniqueKeys0.KEY_LOAN_INFO_LOAN_INFO_ID_UINDEX;
    public static final UniqueKey<LoanOrderInfoRecord> KEY_LOAN_ORDER_INFO_PRIMARY = UniqueKeys0.KEY_LOAN_ORDER_INFO_PRIMARY;
    public static final UniqueKey<LoanOrderInfoRecord> KEY_LOAN_ORDER_INFO_LOAN_ORDER_INFO_ID_UINDEX = UniqueKeys0.KEY_LOAN_ORDER_INFO_LOAN_ORDER_INFO_ID_UINDEX;
    public static final UniqueKey<MemberAccountRecord> KEY_MEMBER_ACCOUNT_PRIMARY = UniqueKeys0.KEY_MEMBER_ACCOUNT_PRIMARY;
    public static final UniqueKey<MemberAccountRecord> KEY_MEMBER_ACCOUNT_MEMBER_AMOUNT_ID_UINDEX = UniqueKeys0.KEY_MEMBER_ACCOUNT_MEMBER_AMOUNT_ID_UINDEX;
    public static final UniqueKey<MemberAccountHistoryRecord> KEY_MEMBER_ACCOUNT_HISTORY_PRIMARY = UniqueKeys0.KEY_MEMBER_ACCOUNT_HISTORY_PRIMARY;
    public static final UniqueKey<MemberAccountHistoryRecord> KEY_MEMBER_ACCOUNT_HISTORY_MEMBER_AMOUNT_HISTROY_ID_UINDEX = UniqueKeys0.KEY_MEMBER_ACCOUNT_HISTORY_MEMBER_AMOUNT_HISTROY_ID_UINDEX;
    public static final UniqueKey<MemeberInfoRecord> KEY_MEMEBER_INFO_PRIMARY = UniqueKeys0.KEY_MEMEBER_INFO_PRIMARY;
    public static final UniqueKey<MemeberInfoRecord> KEY_MEMEBER_INFO_MEMEBER_INFO_ID_UINDEX = UniqueKeys0.KEY_MEMEBER_INFO_MEMEBER_INFO_ID_UINDEX;
    public static final UniqueKey<MobileMessageRecord> KEY_MOBILE_MESSAGE_PRIMARY = UniqueKeys0.KEY_MOBILE_MESSAGE_PRIMARY;
    public static final UniqueKey<MobileMessageRecord> KEY_MOBILE_MESSAGE_MOBILE_MESSAGE_ID_UINDEX = UniqueKeys0.KEY_MOBILE_MESSAGE_MOBILE_MESSAGE_ID_UINDEX;
    public static final UniqueKey<PosApplyRecord> KEY_POS_APPLY_PRIMARY = UniqueKeys0.KEY_POS_APPLY_PRIMARY;
    public static final UniqueKey<PosOrderInfoRecord> KEY_POS_ORDER_INFO_PRIMARY = UniqueKeys0.KEY_POS_ORDER_INFO_PRIMARY;
    public static final UniqueKey<ProductDetailRecord> KEY_PRODUCT_DETAIL_PRIMARY = UniqueKeys0.KEY_PRODUCT_DETAIL_PRIMARY;
    public static final UniqueKey<ProductDetailRecord> KEY_PRODUCT_DETAIL_PRODUCT_DETAIL_ID_UINDEX = UniqueKeys0.KEY_PRODUCT_DETAIL_PRODUCT_DETAIL_ID_UINDEX;
    public static final UniqueKey<ProductDetailParamsRecord> KEY_PRODUCT_DETAIL_PARAMS_PRIMARY = UniqueKeys0.KEY_PRODUCT_DETAIL_PARAMS_PRIMARY;
    public static final UniqueKey<ProductTypeRecord> KEY_PRODUCT_TYPE_PRIMARY = UniqueKeys0.KEY_PRODUCT_TYPE_PRIMARY;
    public static final UniqueKey<ProductTypeRecord> KEY_PRODUCT_TYPE_PRODUCT_TYPE_ID_UINDEX = UniqueKeys0.KEY_PRODUCT_TYPE_PRODUCT_TYPE_ID_UINDEX;
    public static final UniqueKey<ProductTypeParamsRecord> KEY_PRODUCT_TYPE_PARAMS_PRIMARY = UniqueKeys0.KEY_PRODUCT_TYPE_PARAMS_PRIMARY;
    public static final UniqueKey<ScheduleJobRecord> KEY_SCHEDULE_JOB_PRIMARY = UniqueKeys0.KEY_SCHEDULE_JOB_PRIMARY;
    public static final UniqueKey<ScheduleJobLogRecord> KEY_SCHEDULE_JOB_LOG_PRIMARY = UniqueKeys0.KEY_SCHEDULE_JOB_LOG_PRIMARY;
    public static final UniqueKey<SysConfigRecord> KEY_SYS_CONFIG_PRIMARY = UniqueKeys0.KEY_SYS_CONFIG_PRIMARY;
    public static final UniqueKey<SysConfigRecord> KEY_SYS_CONFIG_KEY = UniqueKeys0.KEY_SYS_CONFIG_KEY;
    public static final UniqueKey<SysLogRecord> KEY_SYS_LOG_PRIMARY = UniqueKeys0.KEY_SYS_LOG_PRIMARY;
    public static final UniqueKey<SysMenuRecord> KEY_SYS_MENU_PRIMARY = UniqueKeys0.KEY_SYS_MENU_PRIMARY;
    public static final UniqueKey<SysOssRecord> KEY_SYS_OSS_PRIMARY = UniqueKeys0.KEY_SYS_OSS_PRIMARY;
    public static final UniqueKey<SysRoleRecord> KEY_SYS_ROLE_PRIMARY = UniqueKeys0.KEY_SYS_ROLE_PRIMARY;
    public static final UniqueKey<SysRoleMenuRecord> KEY_SYS_ROLE_MENU_PRIMARY = UniqueKeys0.KEY_SYS_ROLE_MENU_PRIMARY;
    public static final UniqueKey<SysUserRecord> KEY_SYS_USER_PRIMARY = UniqueKeys0.KEY_SYS_USER_PRIMARY;
    public static final UniqueKey<SysUserRecord> KEY_SYS_USER_USERNAME = UniqueKeys0.KEY_SYS_USER_USERNAME;
    public static final UniqueKey<SysUserRoleRecord> KEY_SYS_USER_ROLE_PRIMARY = UniqueKeys0.KEY_SYS_USER_ROLE_PRIMARY;
    public static final UniqueKey<SysUserTokenRecord> KEY_SYS_USER_TOKEN_PRIMARY = UniqueKeys0.KEY_SYS_USER_TOKEN_PRIMARY;
    public static final UniqueKey<SysUserTokenRecord> KEY_SYS_USER_TOKEN_TOKEN = UniqueKeys0.KEY_SYS_USER_TOKEN_TOKEN;
    public static final UniqueKey<TbTokenRecord> KEY_TB_TOKEN_PRIMARY = UniqueKeys0.KEY_TB_TOKEN_PRIMARY;
    public static final UniqueKey<TbTokenRecord> KEY_TB_TOKEN_TOKEN = UniqueKeys0.KEY_TB_TOKEN_TOKEN;
    public static final UniqueKey<TbUserRecord> KEY_TB_USER_PRIMARY = UniqueKeys0.KEY_TB_USER_PRIMARY;
    public static final UniqueKey<WithdrawalInfoRecord> KEY_WITHDRAWAL_INFO_PRIMARY = UniqueKeys0.KEY_WITHDRAWAL_INFO_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<AdPictureRecord, Integer> IDENTITY_AD_PICTURE = Internal.createIdentity(AdPicture.AD_PICTURE, AdPicture.AD_PICTURE.ID);
        public static Identity<ArticleInfoRecord, Integer> IDENTITY_ARTICLE_INFO = Internal.createIdentity(ArticleInfo.ARTICLE_INFO, ArticleInfo.ARTICLE_INFO.ID);
        public static Identity<DisDictionaryRecord, Integer> IDENTITY_DIS_DICTIONARY = Internal.createIdentity(DisDictionary.DIS_DICTIONARY, DisDictionary.DIS_DICTIONARY.ID);
        public static Identity<IntegralTutorialRecord, Integer> IDENTITY_INTEGRAL_TUTORIAL = Internal.createIdentity(IntegralTutorial.INTEGRAL_TUTORIAL, IntegralTutorial.INTEGRAL_TUTORIAL.ID);
        public static Identity<ProductDetailParamsRecord, Integer> IDENTITY_PRODUCT_DETAIL_PARAMS = Internal.createIdentity(ProductDetailParams.PRODUCT_DETAIL_PARAMS, ProductDetailParams.PRODUCT_DETAIL_PARAMS.ID);
        public static Identity<ProductTypeParamsRecord, Integer> IDENTITY_PRODUCT_TYPE_PARAMS = Internal.createIdentity(ProductTypeParams.PRODUCT_TYPE_PARAMS, ProductTypeParams.PRODUCT_TYPE_PARAMS.ID);
        public static Identity<ScheduleJobRecord, Long> IDENTITY_SCHEDULE_JOB = Internal.createIdentity(ScheduleJob.SCHEDULE_JOB, ScheduleJob.SCHEDULE_JOB.JOB_ID);
        public static Identity<ScheduleJobLogRecord, Long> IDENTITY_SCHEDULE_JOB_LOG = Internal.createIdentity(ScheduleJobLog.SCHEDULE_JOB_LOG, ScheduleJobLog.SCHEDULE_JOB_LOG.LOG_ID);
        public static Identity<SysConfigRecord, Long> IDENTITY_SYS_CONFIG = Internal.createIdentity(SysConfig.SYS_CONFIG, SysConfig.SYS_CONFIG.ID);
        public static Identity<SysLogRecord, Long> IDENTITY_SYS_LOG = Internal.createIdentity(SysLog.SYS_LOG, SysLog.SYS_LOG.ID);
        public static Identity<SysMenuRecord, Long> IDENTITY_SYS_MENU = Internal.createIdentity(SysMenu.SYS_MENU, SysMenu.SYS_MENU.MENU_ID);
        public static Identity<SysOssRecord, Long> IDENTITY_SYS_OSS = Internal.createIdentity(SysOss.SYS_OSS, SysOss.SYS_OSS.ID);
        public static Identity<SysRoleRecord, Long> IDENTITY_SYS_ROLE = Internal.createIdentity(SysRole.SYS_ROLE, SysRole.SYS_ROLE.ROLE_ID);
        public static Identity<SysRoleMenuRecord, Long> IDENTITY_SYS_ROLE_MENU = Internal.createIdentity(SysRoleMenu.SYS_ROLE_MENU, SysRoleMenu.SYS_ROLE_MENU.ID);
        public static Identity<SysUserRecord, Long> IDENTITY_SYS_USER = Internal.createIdentity(SysUser.SYS_USER, SysUser.SYS_USER.USER_ID);
        public static Identity<SysUserRoleRecord, Long> IDENTITY_SYS_USER_ROLE = Internal.createIdentity(SysUserRole.SYS_USER_ROLE, SysUserRole.SYS_USER_ROLE.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AdPictureRecord> KEY_AD_PICTURE_PRIMARY = Internal.createUniqueKey(AdPicture.AD_PICTURE, "KEY_ad_picture_PRIMARY", AdPicture.AD_PICTURE.ID);
        public static final UniqueKey<ArticleInfoRecord> KEY_ARTICLE_INFO_PRIMARY = Internal.createUniqueKey(ArticleInfo.ARTICLE_INFO, "KEY_article_info_PRIMARY", ArticleInfo.ARTICLE_INFO.ID);
        public static final UniqueKey<CardInfoRecord> KEY_CARD_INFO_PRIMARY = Internal.createUniqueKey(CardInfo.CARD_INFO, "KEY_card_info_PRIMARY", CardInfo.CARD_INFO.ID);
        public static final UniqueKey<CardInfoRecord> KEY_CARD_INFO_CARD_INFO_ID_UINDEX = Internal.createUniqueKey(CardInfo.CARD_INFO, "KEY_card_info_card_info_id_uindex", CardInfo.CARD_INFO.ID);
        public static final UniqueKey<CardOrderInfoRecord> KEY_CARD_ORDER_INFO_PRIMARY = Internal.createUniqueKey(CardOrderInfo.CARD_ORDER_INFO, "KEY_card_order_info_PRIMARY", CardOrderInfo.CARD_ORDER_INFO.ID);
        public static final UniqueKey<CardOrderInfoRecord> KEY_CARD_ORDER_INFO_CARD_ORDER_INFO_ID_UINDEX = Internal.createUniqueKey(CardOrderInfo.CARD_ORDER_INFO, "KEY_card_order_info_card_order_info_id_uindex", CardOrderInfo.CARD_ORDER_INFO.ID);
        public static final UniqueKey<DisDictionaryRecord> KEY_DIS_DICTIONARY_PRIMARY = Internal.createUniqueKey(DisDictionary.DIS_DICTIONARY, "KEY_dis_dictionary_PRIMARY", DisDictionary.DIS_DICTIONARY.ID);
        public static final UniqueKey<DisFansRecord> KEY_DIS_FANS_PRIMARY = Internal.createUniqueKey(DisFans.DIS_FANS, "KEY_dis_fans_PRIMARY", DisFans.DIS_FANS.ID);
        public static final UniqueKey<DisFansRecord> KEY_DIS_FANS_DIS_FANS_ID_UINDEX = Internal.createUniqueKey(DisFans.DIS_FANS, "KEY_dis_fans_dis_fans_id_uindex", DisFans.DIS_FANS.ID);
        public static final UniqueKey<DisMemberInfoRecord> KEY_DIS_MEMBER_INFO_PRIMARY = Internal.createUniqueKey(DisMemberInfo.DIS_MEMBER_INFO, "KEY_dis_member_info_PRIMARY", DisMemberInfo.DIS_MEMBER_INFO.ID);
        public static final UniqueKey<DisMemberInfoRecord> KEY_DIS_MEMBER_INFO_DIS_MEMBER_INFO_ID_UINDEX = Internal.createUniqueKey(DisMemberInfo.DIS_MEMBER_INFO, "KEY_dis_member_info_dis_member_info_id_uindex", DisMemberInfo.DIS_MEMBER_INFO.ID);
        public static final UniqueKey<DisProfitRecordRecord> KEY_DIS_PROFIT_RECORD_PRIMARY = Internal.createUniqueKey(DisProfitRecord.DIS_PROFIT_RECORD, "KEY_dis_profit_record_PRIMARY", DisProfitRecord.DIS_PROFIT_RECORD.ID);
        public static final UniqueKey<DisProfitRecordRecord> KEY_DIS_PROFIT_RECORD_DIS_PROFIT_RECORD_ID_UINDEX = Internal.createUniqueKey(DisProfitRecord.DIS_PROFIT_RECORD, "KEY_dis_profit_record_dis_profit_record_id_uindex", DisProfitRecord.DIS_PROFIT_RECORD.ID);
        public static final UniqueKey<DisProfiParamRecord> KEY_DIS_PROFI_PARAM_PRIMARY = Internal.createUniqueKey(DisProfiParam.DIS_PROFI_PARAM, "KEY_dis_profi_param_PRIMARY", DisProfiParam.DIS_PROFI_PARAM.ID);
        public static final UniqueKey<DisProfiParamRecord> KEY_DIS_PROFI_PARAM_DIS_PROFI_PARAM_ID_UINDEX = Internal.createUniqueKey(DisProfiParam.DIS_PROFI_PARAM, "KEY_dis_profi_param_dis_profi_param_id_uindex", DisProfiParam.DIS_PROFI_PARAM.ID);
        public static final UniqueKey<IntegralOrderRecord> KEY_INTEGRAL_ORDER_PRIMARY = Internal.createUniqueKey(IntegralOrder.INTEGRAL_ORDER, "KEY_integral_order_PRIMARY", IntegralOrder.INTEGRAL_ORDER.ID);
        public static final UniqueKey<IntegralOrderRecord> KEY_INTEGRAL_ORDER_INTEGRAL_ORDER_ID_UINDEX = Internal.createUniqueKey(IntegralOrder.INTEGRAL_ORDER, "KEY_integral_order_integral_order_id_uindex", IntegralOrder.INTEGRAL_ORDER.ID);
        public static final UniqueKey<IntegralTutorialRecord> KEY_INTEGRAL_TUTORIAL_PRIMARY = Internal.createUniqueKey(IntegralTutorial.INTEGRAL_TUTORIAL, "KEY_integral_tutorial_PRIMARY", IntegralTutorial.INTEGRAL_TUTORIAL.ID);
        public static final UniqueKey<LoanInfoRecord> KEY_LOAN_INFO_PRIMARY = Internal.createUniqueKey(LoanInfo.LOAN_INFO, "KEY_loan_info_PRIMARY", LoanInfo.LOAN_INFO.ID);
        public static final UniqueKey<LoanInfoRecord> KEY_LOAN_INFO_LOAN_INFO_ID_UINDEX = Internal.createUniqueKey(LoanInfo.LOAN_INFO, "KEY_loan_info_loan_info_id_uindex", LoanInfo.LOAN_INFO.ID);
        public static final UniqueKey<LoanOrderInfoRecord> KEY_LOAN_ORDER_INFO_PRIMARY = Internal.createUniqueKey(LoanOrderInfo.LOAN_ORDER_INFO, "KEY_loan_order_info_PRIMARY", LoanOrderInfo.LOAN_ORDER_INFO.ID);
        public static final UniqueKey<LoanOrderInfoRecord> KEY_LOAN_ORDER_INFO_LOAN_ORDER_INFO_ID_UINDEX = Internal.createUniqueKey(LoanOrderInfo.LOAN_ORDER_INFO, "KEY_loan_order_info_loan_order_info_id_uindex", LoanOrderInfo.LOAN_ORDER_INFO.ID);
        public static final UniqueKey<MemberAccountRecord> KEY_MEMBER_ACCOUNT_PRIMARY = Internal.createUniqueKey(MemberAccount.MEMBER_ACCOUNT, "KEY_member_account_PRIMARY", MemberAccount.MEMBER_ACCOUNT.ACCOUNT_ID);
        public static final UniqueKey<MemberAccountRecord> KEY_MEMBER_ACCOUNT_MEMBER_AMOUNT_ID_UINDEX = Internal.createUniqueKey(MemberAccount.MEMBER_ACCOUNT, "KEY_member_account_member_amount_id_uindex", MemberAccount.MEMBER_ACCOUNT.ACCOUNT_ID);
        public static final UniqueKey<MemberAccountHistoryRecord> KEY_MEMBER_ACCOUNT_HISTORY_PRIMARY = Internal.createUniqueKey(MemberAccountHistory.MEMBER_ACCOUNT_HISTORY, "KEY_member_account_history_PRIMARY", MemberAccountHistory.MEMBER_ACCOUNT_HISTORY.ID);
        public static final UniqueKey<MemberAccountHistoryRecord> KEY_MEMBER_ACCOUNT_HISTORY_MEMBER_AMOUNT_HISTROY_ID_UINDEX = Internal.createUniqueKey(MemberAccountHistory.MEMBER_ACCOUNT_HISTORY, "KEY_member_account_history_member_amount_histroy_id_uindex", MemberAccountHistory.MEMBER_ACCOUNT_HISTORY.ID);
        public static final UniqueKey<MemeberInfoRecord> KEY_MEMEBER_INFO_PRIMARY = Internal.createUniqueKey(MemeberInfo.MEMEBER_INFO, "KEY_memeber_info_PRIMARY", MemeberInfo.MEMEBER_INFO.ID);
        public static final UniqueKey<MemeberInfoRecord> KEY_MEMEBER_INFO_MEMEBER_INFO_ID_UINDEX = Internal.createUniqueKey(MemeberInfo.MEMEBER_INFO, "KEY_memeber_info_memeber_info_id_uindex", MemeberInfo.MEMEBER_INFO.ID);
        public static final UniqueKey<MobileMessageRecord> KEY_MOBILE_MESSAGE_PRIMARY = Internal.createUniqueKey(MobileMessage.MOBILE_MESSAGE, "KEY_mobile_message_PRIMARY", MobileMessage.MOBILE_MESSAGE.ID);
        public static final UniqueKey<MobileMessageRecord> KEY_MOBILE_MESSAGE_MOBILE_MESSAGE_ID_UINDEX = Internal.createUniqueKey(MobileMessage.MOBILE_MESSAGE, "KEY_mobile_message_mobile_message_id_uindex", MobileMessage.MOBILE_MESSAGE.ID);
        public static final UniqueKey<PosApplyRecord> KEY_POS_APPLY_PRIMARY = Internal.createUniqueKey(PosApply.POS_APPLY, "KEY_pos_apply_PRIMARY", PosApply.POS_APPLY.ID);
        public static final UniqueKey<PosOrderInfoRecord> KEY_POS_ORDER_INFO_PRIMARY = Internal.createUniqueKey(PosOrderInfo.POS_ORDER_INFO, "KEY_pos_order_info_PRIMARY", PosOrderInfo.POS_ORDER_INFO.ID);
        public static final UniqueKey<ProductDetailRecord> KEY_PRODUCT_DETAIL_PRIMARY = Internal.createUniqueKey(ProductDetail.PRODUCT_DETAIL, "KEY_product_detail_PRIMARY", ProductDetail.PRODUCT_DETAIL.ID);
        public static final UniqueKey<ProductDetailRecord> KEY_PRODUCT_DETAIL_PRODUCT_DETAIL_ID_UINDEX = Internal.createUniqueKey(ProductDetail.PRODUCT_DETAIL, "KEY_product_detail_product_detail_id_uindex", ProductDetail.PRODUCT_DETAIL.ID);
        public static final UniqueKey<ProductDetailParamsRecord> KEY_PRODUCT_DETAIL_PARAMS_PRIMARY = Internal.createUniqueKey(ProductDetailParams.PRODUCT_DETAIL_PARAMS, "KEY_product_detail_params_PRIMARY", ProductDetailParams.PRODUCT_DETAIL_PARAMS.ID);
        public static final UniqueKey<ProductTypeRecord> KEY_PRODUCT_TYPE_PRIMARY = Internal.createUniqueKey(ProductType.PRODUCT_TYPE, "KEY_product_type_PRIMARY", ProductType.PRODUCT_TYPE.ID);
        public static final UniqueKey<ProductTypeRecord> KEY_PRODUCT_TYPE_PRODUCT_TYPE_ID_UINDEX = Internal.createUniqueKey(ProductType.PRODUCT_TYPE, "KEY_product_type_product_type_id_uindex", ProductType.PRODUCT_TYPE.ID);
        public static final UniqueKey<ProductTypeParamsRecord> KEY_PRODUCT_TYPE_PARAMS_PRIMARY = Internal.createUniqueKey(ProductTypeParams.PRODUCT_TYPE_PARAMS, "KEY_product_type_params_PRIMARY", ProductTypeParams.PRODUCT_TYPE_PARAMS.ID);
        public static final UniqueKey<ScheduleJobRecord> KEY_SCHEDULE_JOB_PRIMARY = Internal.createUniqueKey(ScheduleJob.SCHEDULE_JOB, "KEY_schedule_job_PRIMARY", ScheduleJob.SCHEDULE_JOB.JOB_ID);
        public static final UniqueKey<ScheduleJobLogRecord> KEY_SCHEDULE_JOB_LOG_PRIMARY = Internal.createUniqueKey(ScheduleJobLog.SCHEDULE_JOB_LOG, "KEY_schedule_job_log_PRIMARY", ScheduleJobLog.SCHEDULE_JOB_LOG.LOG_ID);
        public static final UniqueKey<SysConfigRecord> KEY_SYS_CONFIG_PRIMARY = Internal.createUniqueKey(SysConfig.SYS_CONFIG, "KEY_sys_config_PRIMARY", SysConfig.SYS_CONFIG.ID);
        public static final UniqueKey<SysConfigRecord> KEY_SYS_CONFIG_KEY = Internal.createUniqueKey(SysConfig.SYS_CONFIG, "KEY_sys_config_key", SysConfig.SYS_CONFIG.KEY);
        public static final UniqueKey<SysLogRecord> KEY_SYS_LOG_PRIMARY = Internal.createUniqueKey(SysLog.SYS_LOG, "KEY_sys_log_PRIMARY", SysLog.SYS_LOG.ID);
        public static final UniqueKey<SysMenuRecord> KEY_SYS_MENU_PRIMARY = Internal.createUniqueKey(SysMenu.SYS_MENU, "KEY_sys_menu_PRIMARY", SysMenu.SYS_MENU.MENU_ID);
        public static final UniqueKey<SysOssRecord> KEY_SYS_OSS_PRIMARY = Internal.createUniqueKey(SysOss.SYS_OSS, "KEY_sys_oss_PRIMARY", SysOss.SYS_OSS.ID);
        public static final UniqueKey<SysRoleRecord> KEY_SYS_ROLE_PRIMARY = Internal.createUniqueKey(SysRole.SYS_ROLE, "KEY_sys_role_PRIMARY", SysRole.SYS_ROLE.ROLE_ID);
        public static final UniqueKey<SysRoleMenuRecord> KEY_SYS_ROLE_MENU_PRIMARY = Internal.createUniqueKey(SysRoleMenu.SYS_ROLE_MENU, "KEY_sys_role_menu_PRIMARY", SysRoleMenu.SYS_ROLE_MENU.ID);
        public static final UniqueKey<SysUserRecord> KEY_SYS_USER_PRIMARY = Internal.createUniqueKey(SysUser.SYS_USER, "KEY_sys_user_PRIMARY", SysUser.SYS_USER.USER_ID);
        public static final UniqueKey<SysUserRecord> KEY_SYS_USER_USERNAME = Internal.createUniqueKey(SysUser.SYS_USER, "KEY_sys_user_username", SysUser.SYS_USER.USERNAME);
        public static final UniqueKey<SysUserRoleRecord> KEY_SYS_USER_ROLE_PRIMARY = Internal.createUniqueKey(SysUserRole.SYS_USER_ROLE, "KEY_sys_user_role_PRIMARY", SysUserRole.SYS_USER_ROLE.ID);
        public static final UniqueKey<SysUserTokenRecord> KEY_SYS_USER_TOKEN_PRIMARY = Internal.createUniqueKey(SysUserToken.SYS_USER_TOKEN, "KEY_sys_user_token_PRIMARY", SysUserToken.SYS_USER_TOKEN.USER_ID);
        public static final UniqueKey<SysUserTokenRecord> KEY_SYS_USER_TOKEN_TOKEN = Internal.createUniqueKey(SysUserToken.SYS_USER_TOKEN, "KEY_sys_user_token_token", SysUserToken.SYS_USER_TOKEN.TOKEN);
        public static final UniqueKey<TbTokenRecord> KEY_TB_TOKEN_PRIMARY = Internal.createUniqueKey(TbToken.TB_TOKEN, "KEY_tb_token_PRIMARY", TbToken.TB_TOKEN.USER_ID);
        public static final UniqueKey<TbTokenRecord> KEY_TB_TOKEN_TOKEN = Internal.createUniqueKey(TbToken.TB_TOKEN, "KEY_tb_token_token", TbToken.TB_TOKEN.TOKEN);
        public static final UniqueKey<TbUserRecord> KEY_TB_USER_PRIMARY = Internal.createUniqueKey(TbUser.TB_USER, "KEY_tb_user_PRIMARY", TbUser.TB_USER.USER_ID);
        public static final UniqueKey<WithdrawalInfoRecord> KEY_WITHDRAWAL_INFO_PRIMARY = Internal.createUniqueKey(WithdrawalInfo.WITHDRAWAL_INFO, "KEY_withdrawal_info_PRIMARY", WithdrawalInfo.WITHDRAWAL_INFO.ID);
    }
}
