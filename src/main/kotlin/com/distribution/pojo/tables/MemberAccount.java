/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.DistributionDev;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.MemberAccountRecord;

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
 * 会员账户表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberAccount extends TableImpl<MemberAccountRecord> {

    private static final long serialVersionUID = 1463087402;

    /**
     * The reference instance of <code>distribution_dev.member_account</code>
     */
    public static final MemberAccount MEMBER_ACCOUNT = new MemberAccount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MemberAccountRecord> getRecordType() {
        return MemberAccountRecord.class;
    }

    /**
     * The column <code>distribution_dev.member_account.account_id</code>.
     */
    public final TableField<MemberAccountRecord, String> ACCOUNT_ID = createField("account_id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>distribution_dev.member_account.member_id</code>. 会员管理ID
     */
    public final TableField<MemberAccountRecord, String> MEMBER_ID = createField("member_id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "会员管理ID");

    /**
     * The column <code>distribution_dev.member_account.member_type</code>. 账户类型
     */
    public final TableField<MemberAccountRecord, String> MEMBER_TYPE = createField("member_type", org.jooq.impl.SQLDataType.VARCHAR(10), this, "账户类型");

    /**
     * The column <code>distribution_dev.member_account.member_amount</code>.
     */
    public final TableField<MemberAccountRecord, BigDecimal> MEMBER_AMOUNT = createField("member_amount", org.jooq.impl.SQLDataType.DECIMAL(12, 2), this, "");

    /**
     * The column <code>distribution_dev.member_account.alipay_account</code>. 用户支付宝账号
     */
    public final TableField<MemberAccountRecord, String> ALIPAY_ACCOUNT = createField("alipay_account", org.jooq.impl.SQLDataType.VARCHAR(25), this, "用户支付宝账号");

    /**
     * The column <code>distribution_dev.member_account.is_delete</code>.
     */
    public final TableField<MemberAccountRecord, String> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.VARCHAR(1), this, "");

    /**
     * The column <code>distribution_dev.member_account.add_time</code>.
     */
    public final TableField<MemberAccountRecord, String> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>distribution_dev.member_account.update_time</code>.
     */
    public final TableField<MemberAccountRecord, String> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * Create a <code>distribution_dev.member_account</code> table reference
     */
    public MemberAccount() {
        this(DSL.name("member_account"), null);
    }

    /**
     * Create an aliased <code>distribution_dev.member_account</code> table reference
     */
    public MemberAccount(String alias) {
        this(DSL.name(alias), MEMBER_ACCOUNT);
    }

    /**
     * Create an aliased <code>distribution_dev.member_account</code> table reference
     */
    public MemberAccount(Name alias) {
        this(alias, MEMBER_ACCOUNT);
    }

    private MemberAccount(Name alias, Table<MemberAccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private MemberAccount(Name alias, Table<MemberAccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "会员账户表");
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
        return Arrays.<Index>asList(Indexes.MEMBER_ACCOUNT_MEMBER_ACCOUNT__INDEX_1, Indexes.MEMBER_ACCOUNT_MEMBER_ACCOUNT__INDEX_2, Indexes.MEMBER_ACCOUNT_MEMBER_AMOUNT_ID_UINDEX, Indexes.MEMBER_ACCOUNT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MemberAccountRecord> getPrimaryKey() {
        return Keys.KEY_MEMBER_ACCOUNT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MemberAccountRecord>> getKeys() {
        return Arrays.<UniqueKey<MemberAccountRecord>>asList(Keys.KEY_MEMBER_ACCOUNT_PRIMARY, Keys.KEY_MEMBER_ACCOUNT_MEMBER_AMOUNT_ID_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberAccount as(String alias) {
        return new MemberAccount(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberAccount as(Name alias) {
        return new MemberAccount(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MemberAccount rename(String name) {
        return new MemberAccount(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MemberAccount rename(Name name) {
        return new MemberAccount(name, null);
    }
}
