/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.LoanInfo;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LoanInfoRecord extends UpdatableRecordImpl<LoanInfoRecord> implements Record8<String, String, String, String, String, String, Integer, String> {

    private static final long serialVersionUID = -768415627;

    /**
     * Setter for <code>distribution.loan_info.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.loan_info.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>distribution.loan_info.loan_name</code>. 贷款产品名称
     */
    public void setLoanName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.loan_info.loan_name</code>. 贷款产品名称
     */
    public String getLoanName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.loan_info.loan_url</code>. 产品链接
     */
    public void setLoanUrl(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.loan_info.loan_url</code>. 产品链接
     */
    public String getLoanUrl() {
        return (String) get(2);
    }

    /**
     * Setter for <code>distribution.loan_info.loan_img</code>.
     */
    public void setLoanImg(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.loan_info.loan_img</code>.
     */
    public String getLoanImg() {
        return (String) get(3);
    }

    /**
     * Setter for <code>distribution.loan_info.add_time</code>.
     */
    public void setAddTime(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.loan_info.add_time</code>.
     */
    public String getAddTime() {
        return (String) get(4);
    }

    /**
     * Setter for <code>distribution.loan_info.update_time</code>.
     */
    public void setUpdateTime(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution.loan_info.update_time</code>.
     */
    public String getUpdateTime() {
        return (String) get(5);
    }

    /**
     * Setter for <code>distribution.loan_info.is_delete</code>. 是否启用 0否 1是
     */
    public void setIsDelete(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>distribution.loan_info.is_delete</code>. 是否启用 0否 1是
     */
    public Integer getIsDelete() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>distribution.loan_info.remark</code>.
     */
    public void setRemark(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>distribution.loan_info.remark</code>.
     */
    public String getRemark() {
        return (String) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, String, String, String, String, Integer, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, String, String, String, String, Integer, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return LoanInfo.LOAN_INFO.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return LoanInfo.LOAN_INFO.LOAN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return LoanInfo.LOAN_INFO.LOAN_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return LoanInfo.LOAN_INFO.LOAN_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return LoanInfo.LOAN_INFO.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return LoanInfo.LOAN_INFO.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return LoanInfo.LOAN_INFO.IS_DELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return LoanInfo.LOAN_INFO.REMARK;
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
        return getLoanName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getLoanUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getLoanImg();
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
    public String component6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getRemark();
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
        return getLoanName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getLoanUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getLoanImg();
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
    public String value6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord value2(String value) {
        setLoanName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord value3(String value) {
        setLoanUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord value4(String value) {
        setLoanImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord value5(String value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord value6(String value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord value7(Integer value) {
        setIsDelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord value8(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanInfoRecord values(String value1, String value2, String value3, String value4, String value5, String value6, Integer value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LoanInfoRecord
     */
    public LoanInfoRecord() {
        super(LoanInfo.LOAN_INFO);
    }

    /**
     * Create a detached, initialised LoanInfoRecord
     */
    public LoanInfoRecord(String id, String loanName, String loanUrl, String loanImg, String addTime, String updateTime, Integer isDelete, String remark) {
        super(LoanInfo.LOAN_INFO);

        set(0, id);
        set(1, loanName);
        set(2, loanUrl);
        set(3, loanImg);
        set(4, addTime);
        set(5, updateTime);
        set(6, isDelete);
        set(7, remark);
    }
}