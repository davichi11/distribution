/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.SysUser;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


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
public class SysUserRecord extends UpdatableRecordImpl<SysUserRecord> implements Record9<Long, String, String, String, String, String, Integer, Long, Timestamp> {

    private static final long serialVersionUID = -1675339751;

    /**
     * Setter for <code>distribution_dev.sys_user.user_id</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>distribution_dev.sys_user.username</code>. 用户名
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.username</code>. 用户名
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution_dev.sys_user.password</code>. 密码
     */
    public void setPassword(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.password</code>. 密码
     */
    public String getPassword() {
        return (String) get(2);
    }

    /**
     * Setter for <code>distribution_dev.sys_user.salt</code>. 盐
     */
    public void setSalt(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.salt</code>. 盐
     */
    public String getSalt() {
        return (String) get(3);
    }

    /**
     * Setter for <code>distribution_dev.sys_user.email</code>. 邮箱
     */
    public void setEmail(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.email</code>. 邮箱
     */
    public String getEmail() {
        return (String) get(4);
    }

    /**
     * Setter for <code>distribution_dev.sys_user.mobile</code>. 手机号
     */
    public void setMobile(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.mobile</code>. 手机号
     */
    public String getMobile() {
        return (String) get(5);
    }

    /**
     * Setter for <code>distribution_dev.sys_user.status</code>. 状态  0：禁用   1：正常
     */
    public void setStatus(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.status</code>. 状态  0：禁用   1：正常
     */
    public Integer getStatus() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>distribution_dev.sys_user.create_user_id</code>. 创建者ID
     */
    public void setCreateUserId(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.create_user_id</code>. 创建者ID
     */
    public Long getCreateUserId() {
        return (Long) get(7);
    }

    /**
     * Setter for <code>distribution_dev.sys_user.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>distribution_dev.sys_user.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Long, String, String, String, String, String, Integer, Long, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Long, String, String, String, String, String, Integer, Long, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return SysUser.SYS_USER.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return SysUser.SYS_USER.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return SysUser.SYS_USER.PASSWORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return SysUser.SYS_USER.SALT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return SysUser.SYS_USER.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return SysUser.SYS_USER.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return SysUser.SYS_USER.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field8() {
        return SysUser.SYS_USER.CREATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return SysUser.SYS_USER.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getSalt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component8() {
        return getCreateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getSalt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value8() {
        return getCreateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value2(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value3(String value) {
        setPassword(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value4(String value) {
        setSalt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value5(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value6(String value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value7(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value8(Long value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord value9(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRecord values(Long value1, String value2, String value3, String value4, String value5, String value6, Integer value7, Long value8, Timestamp value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SysUserRecord
     */
    public SysUserRecord() {
        super(SysUser.SYS_USER);
    }

    /**
     * Create a detached, initialised SysUserRecord
     */
    public SysUserRecord(Long userId, String username, String password, String salt, String email, String mobile, Integer status, Long createUserId, Timestamp createTime) {
        super(SysUser.SYS_USER);

        set(0, userId);
        set(1, username);
        set(2, password);
        set(3, salt);
        set(4, email);
        set(5, mobile);
        set(6, status);
        set(7, createUserId);
        set(8, createTime);
    }
}
