/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.Distribution;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.SysUserRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * ????
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysUser extends TableImpl<SysUserRecord> {

    private static final long serialVersionUID = 2092748468;

    /**
     * The reference instance of <code>distribution.sys_user</code>
     */
    public static final SysUser SYS_USER = new SysUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SysUserRecord> getRecordType() {
        return SysUserRecord.class;
    }

    /**
     * The column <code>distribution.sys_user.user_id</code>.
     */
    public final TableField<SysUserRecord, Long> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>distribution.sys_user.username</code>. 用户名
     */
    public final TableField<SysUserRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "用户名");

    /**
     * The column <code>distribution.sys_user.password</code>. 密码
     */
    public final TableField<SysUserRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR(100), this, "密码");

    /**
     * The column <code>distribution.sys_user.salt</code>. 盐
     */
    public final TableField<SysUserRecord, String> SALT = createField("salt", org.jooq.impl.SQLDataType.VARCHAR(20), this, "盐");

    /**
     * The column <code>distribution.sys_user.email</code>. 邮箱
     */
    public final TableField<SysUserRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(100), this, "邮箱");

    /**
     * The column <code>distribution.sys_user.mobile</code>. 手机号
     */
    public final TableField<SysUserRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR(100), this, "手机号");

    /**
     * The column <code>distribution.sys_user.status</code>. 状态  0：禁用   1：正常
     */
    public final TableField<SysUserRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER, this, "状态  0：禁用   1：正常");

    /**
     * The column <code>distribution.sys_user.create_user_id</code>. 创建者ID
     */
    public final TableField<SysUserRecord, Long> CREATE_USER_ID = createField("create_user_id", org.jooq.impl.SQLDataType.BIGINT, this, "创建者ID");

    /**
     * The column <code>distribution.sys_user.create_time</code>. 创建时间
     */
    public final TableField<SysUserRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "创建时间");

    /**
     * Create a <code>distribution.sys_user</code> table reference
     */
    public SysUser() {
        this(DSL.name("sys_user"), null);
    }

    /**
     * Create an aliased <code>distribution.sys_user</code> table reference
     */
    public SysUser(String alias) {
        this(DSL.name(alias), SYS_USER);
    }

    /**
     * Create an aliased <code>distribution.sys_user</code> table reference
     */
    public SysUser(Name alias) {
        this(alias, SYS_USER);
    }

    private SysUser(Name alias, Table<SysUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private SysUser(Name alias, Table<SysUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Distribution.DISTRIBUTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SYS_USER_PRIMARY, Indexes.SYS_USER_USERNAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<SysUserRecord, Long> getIdentity() {
        return Keys.IDENTITY_SYS_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SysUserRecord> getPrimaryKey() {
        return Keys.KEY_SYS_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SysUserRecord>> getKeys() {
        return Arrays.<UniqueKey<SysUserRecord>>asList(Keys.KEY_SYS_USER_PRIMARY, Keys.KEY_SYS_USER_USERNAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUser as(String alias) {
        return new SysUser(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUser as(Name alias) {
        return new SysUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SysUser rename(String name) {
        return new SysUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SysUser rename(Name name) {
        return new SysUser(name, null);
    }
}