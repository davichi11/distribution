/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.IntegralOrder;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 积分兑换申请表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IntegralOrderRecord extends UpdatableRecordImpl<IntegralOrderRecord> implements Record6<String, Long, String, String, String, Boolean> {

    private static final long serialVersionUID = 1202244212;

    /**
     * Setter for <code>distribution.integral_order.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.integral_order.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>distribution.integral_order.mobile</code>. 兑换人手机号
     */
    public void setMobile(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.integral_order.mobile</code>. 兑换人手机号
     */
    public Long getMobile() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>distribution.integral_order.detail_id</code>. 兑换产品ID
     */
    public void setDetailId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.integral_order.detail_id</code>. 兑换产品ID
     */
    public String getDetailId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>distribution.integral_order.img</code>. 卷码图片,可以逗号分隔
     */
    public void setImg(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.integral_order.img</code>. 卷码图片,可以逗号分隔
     */
    public String getImg() {
        return (String) get(3);
    }

    /**
     * Setter for <code>distribution.integral_order.add_time</code>. 申请时间
     */
    public void setAddTime(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.integral_order.add_time</code>. 申请时间
     */
    public String getAddTime() {
        return (String) get(4);
    }

    /**
     * Setter for <code>distribution.integral_order.status</code>. 审核状态:0失败,1成功,2审核中
     */
    public void setStatus(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution.integral_order.status</code>. 审核状态:0失败,1成功,2审核中
     */
    public Boolean getStatus() {
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
    public Row6<String, Long, String, String, String, Boolean> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, Long, String, String, String, Boolean> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return IntegralOrder.INTEGRAL_ORDER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return IntegralOrder.INTEGRAL_ORDER.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return IntegralOrder.INTEGRAL_ORDER.DETAIL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return IntegralOrder.INTEGRAL_ORDER.IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return IntegralOrder.INTEGRAL_ORDER.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return IntegralOrder.INTEGRAL_ORDER.STATUS;
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
    public Long component2() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDetailId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component6() {
        return getStatus();
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
    public Long value2() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDetailId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrderRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrderRecord value2(Long value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrderRecord value3(String value) {
        setDetailId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrderRecord value4(String value) {
        setImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrderRecord value5(String value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrderRecord value6(Boolean value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrderRecord values(String value1, Long value2, String value3, String value4, String value5, Boolean value6) {
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
     * Create a detached IntegralOrderRecord
     */
    public IntegralOrderRecord() {
        super(IntegralOrder.INTEGRAL_ORDER);
    }

    /**
     * Create a detached, initialised IntegralOrderRecord
     */
    public IntegralOrderRecord(String id, Long mobile, String detailId, String img, String addTime, Boolean status) {
        super(IntegralOrder.INTEGRAL_ORDER);

        set(0, id);
        set(1, mobile);
        set(2, detailId);
        set(3, img);
        set(4, addTime);
        set(5, status);
    }
}
