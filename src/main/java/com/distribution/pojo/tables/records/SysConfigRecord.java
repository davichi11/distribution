/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.SysConfig;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * ???????
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysConfigRecord extends UpdatableRecordImpl<SysConfigRecord> implements Record5<Long, String, String, Boolean, String> {

    private static final long serialVersionUID = 1294765019;

    /**
     * Setter for <code>distribution.sys_config.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.sys_config.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>distribution.sys_config.key</code>. key
     */
    public void setKey(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.sys_config.key</code>. key
     */
    public String getKey() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.sys_config.value</code>. value
     */
    public void setValue(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.sys_config.value</code>. value
     */
    public String getValue() {
        return (String) get(2);
    }

    /**
     * Setter for <code>distribution.sys_config.status</code>. ??   0???   1???
     */
    public void setStatus(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.sys_config.status</code>. ??   0???   1???
     */
    public Boolean getStatus() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>distribution.sys_config.remark</code>. ??
     */
    public void setRemark(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.sys_config.remark</code>. ??
     */
    public String getRemark() {
        return (String) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, String, Boolean, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, String, Boolean, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return SysConfig.SYS_CONFIG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return SysConfig.SYS_CONFIG.KEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return SysConfig.SYS_CONFIG.VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field4() {
        return SysConfig.SYS_CONFIG.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return SysConfig.SYS_CONFIG.REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component4() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value4() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysConfigRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysConfigRecord value2(String value) {
        setKey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysConfigRecord value3(String value) {
        setValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysConfigRecord value4(Boolean value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysConfigRecord value5(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysConfigRecord values(Long value1, String value2, String value3, Boolean value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SysConfigRecord
     */
    public SysConfigRecord() {
        super(SysConfig.SYS_CONFIG);
    }

    /**
     * Create a detached, initialised SysConfigRecord
     */
    public SysConfigRecord(Long id, String key, String value, Boolean status, String remark) {
        super(SysConfig.SYS_CONFIG);

        set(0, id);
        set(1, key);
        set(2, value);
        set(3, status);
        set(4, remark);
    }
}