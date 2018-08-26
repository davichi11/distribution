/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.DistributionDev;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.WithdrawalInfoRecord;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 提现信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WithdrawalInfo extends TableImpl<WithdrawalInfoRecord> {

    private static final long serialVersionUID = 1235802197;

    /**
     * The reference instance of <code>distribution_dev.withdrawal_info</code>
     */
    public static final WithdrawalInfo WITHDRAWAL_INFO = new WithdrawalInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WithdrawalInfoRecord> getRecordType() {
        return WithdrawalInfoRecord.class;
    }

    /**
     * The column <code>distribution_dev.withdrawal_info.id</code>.
     */
    public final TableField<WithdrawalInfoRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>distribution_dev.withdrawal_info.withdraw_num</code>. 提现订单号
     */
    public final TableField<WithdrawalInfoRecord, String> WITHDRAW_NUM = createField("withdraw_num", org.jooq.impl.SQLDataType.VARCHAR(100), this, "提现订单号");

    /**
     * The column <code>distribution_dev.withdrawal_info.withdraw_type</code>. 提现状态 0:失败,1:成功
     */
    public final TableField<WithdrawalInfoRecord, String> WITHDRAW_TYPE = createField("withdraw_type", org.jooq.impl.SQLDataType.VARCHAR(20), this, "提现状态 0:失败,1:成功");

    /**
     * The column <code>distribution_dev.withdrawal_info.withdraw_name</code>. 提现用户名
     */
    public final TableField<WithdrawalInfoRecord, String> WITHDRAW_NAME = createField("withdraw_name", org.jooq.impl.SQLDataType.VARCHAR(100), this, "提现用户名");

    /**
     * The column <code>distribution_dev.withdrawal_info.withdraw_mobile</code>. 提现用户手机号
     */
    public final TableField<WithdrawalInfoRecord, String> WITHDRAW_MOBILE = createField("withdraw_mobile", org.jooq.impl.SQLDataType.VARCHAR(20), this, "提现用户手机号");

    /**
     * The column <code>distribution_dev.withdrawal_info.withdraw_card</code>.
     */
    public final TableField<WithdrawalInfoRecord, String> WITHDRAW_CARD = createField("withdraw_card", org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>distribution_dev.withdrawal_info.withdraw_amount</code>. 提现金额
     */
    public final TableField<WithdrawalInfoRecord, BigDecimal> WITHDRAW_AMOUNT = createField("withdraw_amount", org.jooq.impl.SQLDataType.DECIMAL(12, 2), this, "提现金额");

    /**
     * The column <code>distribution_dev.withdrawal_info.withdraw_poundage</code>. 提现手续费 默认0
     */
    public final TableField<WithdrawalInfoRecord, BigDecimal> WITHDRAW_POUNDAGE = createField("withdraw_poundage", org.jooq.impl.SQLDataType.DECIMAL(12, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "提现手续费 默认0");

    /**
     * The column <code>distribution_dev.withdrawal_info.real_amount</code>. 实际到账金额
     */
    public final TableField<WithdrawalInfoRecord, BigDecimal> REAL_AMOUNT = createField("real_amount", org.jooq.impl.SQLDataType.DECIMAL(12, 2), this, "实际到账金额");

    /**
     * The column <code>distribution_dev.withdrawal_info.alipay_account</code>. 支付宝账号
     */
    public final TableField<WithdrawalInfoRecord, String> ALIPAY_ACCOUNT = createField("alipay_account", org.jooq.impl.SQLDataType.VARCHAR(25), this, "支付宝账号");

    /**
     * The column <code>distribution_dev.withdrawal_info.is_delete</code>.
     */
    public final TableField<WithdrawalInfoRecord, String> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.VARCHAR(1), this, "");

    /**
     * The column <code>distribution_dev.withdrawal_info.add_time</code>.
     */
    public final TableField<WithdrawalInfoRecord, String> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>distribution_dev.withdrawal_info.update_time</code>.
     */
    public final TableField<WithdrawalInfoRecord, String> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * Create a <code>distribution_dev.withdrawal_info</code> table reference
     */
    public WithdrawalInfo() {
        this(DSL.name("withdrawal_info"), null);
    }

    /**
     * Create an aliased <code>distribution_dev.withdrawal_info</code> table reference
     */
    public WithdrawalInfo(String alias) {
        this(DSL.name(alias), WITHDRAWAL_INFO);
    }

    /**
     * Create an aliased <code>distribution_dev.withdrawal_info</code> table reference
     */
    public WithdrawalInfo(Name alias) {
        this(alias, WITHDRAWAL_INFO);
    }

    private WithdrawalInfo(Name alias, Table<WithdrawalInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private WithdrawalInfo(Name alias, Table<WithdrawalInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "提现信息表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DistributionDev.DISTRIBUTION_DEV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.WITHDRAWAL_INFO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WithdrawalInfoRecord> getPrimaryKey() {
        return Keys.KEY_WITHDRAWAL_INFO_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WithdrawalInfoRecord>> getKeys() {
        return Arrays.<UniqueKey<WithdrawalInfoRecord>>asList(Keys.KEY_WITHDRAWAL_INFO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WithdrawalInfo as(String alias) {
        return new WithdrawalInfo(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WithdrawalInfo as(Name alias) {
        return new WithdrawalInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WithdrawalInfo rename(String name) {
        return new WithdrawalInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WithdrawalInfo rename(Name name) {
        return new WithdrawalInfo(name, null);
    }
}
