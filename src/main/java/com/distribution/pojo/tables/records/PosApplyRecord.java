/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.PosApply;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * POS机申请表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PosApplyRecord extends UpdatableRecordImpl<PosApplyRecord> implements Record6<String, String, Long, String, String, Boolean> {

    private static final long serialVersionUID = -1139415575;

    /**
     * Setter for <code>distribution.pos_apply.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.pos_apply.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>distribution.pos_apply.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.pos_apply.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.pos_apply.mobile</code>.
     */
    public void setMobile(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.pos_apply.mobile</code>.
     */
    public Long getMobile() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>distribution.pos_apply.merchants</code>. 商户名称
     */
    public void setMerchants(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.pos_apply.merchants</code>. 商户名称
     */
    public String getMerchants() {
        return (String) get(3);
    }

    /**
     * Setter for <code>distribution.pos_apply.area_code</code>. 区域编码
     */
    public void setAreaCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.pos_apply.area_code</code>. 区域编码
     */
    public String getAreaCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>distribution.pos_apply.attribute</code>. 连锁属性 0:非连锁,1:连锁
     */
    public void setAttribute(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution.pos_apply.attribute</code>. 连锁属性 0:非连锁,1:连锁
     */
    public Boolean getAttribute() {
        return (Boolean) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, Long, String, String, Boolean> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, Long, String, String, Boolean> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return PosApply.POS_APPLY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return PosApply.POS_APPLY.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return PosApply.POS_APPLY.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return PosApply.POS_APPLY.MERCHANTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return PosApply.POS_APPLY.AREA_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return PosApply.POS_APPLY.ATTRIBUTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component3() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getMerchants();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getAreaCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component6() {
        return getAttribute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMerchants();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getAreaCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getAttribute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosApplyRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosApplyRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosApplyRecord value3(Long value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosApplyRecord value4(String value) {
        setMerchants(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosApplyRecord value5(String value) {
        setAreaCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosApplyRecord value6(Boolean value) {
        setAttribute(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PosApplyRecord values(String value1, String value2, Long value3, String value4, String value5, Boolean value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PosApplyRecord
     */
    public PosApplyRecord() {
        super(PosApply.POS_APPLY);
    }

    /**
     * Create a detached, initialised PosApplyRecord
     */
    public PosApplyRecord(String id, String name, Long mobile, String merchants, String areaCode, Boolean attribute) {
        super(PosApply.POS_APPLY);

        set(0, id);
        set(1, name);
        set(2, mobile);
        set(3, merchants);
        set(4, areaCode);
        set(5, attribute);
    }
}
