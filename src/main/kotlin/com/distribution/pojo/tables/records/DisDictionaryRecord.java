/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.DisDictionary;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 字典表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DisDictionaryRecord extends UpdatableRecordImpl<DisDictionaryRecord> implements Record9<Integer, String, String, String, Integer, String, String, String, String> {

    private static final long serialVersionUID = 1170749759;

    /**
     * Setter for <code>distribution.dis_dictionary.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>distribution.dis_dictionary.dis_code</code>. 字典编码
     */
    public void setDisCode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.dis_code</code>. 字典编码
     */
    public String getDisCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.dis_dictionary.dis_type</code>. 字典隐藏字段
     */
    public void setDisType(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.dis_type</code>. 字典隐藏字段
     */
    public String getDisType() {
        return (String) get(2);
    }

    /**
     * Setter for <code>distribution.dis_dictionary.dis_value</code>. 字典值，为前台显示用
     */
    public void setDisValue(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.dis_value</code>. 字典值，为前台显示用
     */
    public String getDisValue() {
        return (String) get(3);
    }

    /**
     * Setter for <code>distribution.dis_dictionary.dis_sort</code>. 排序
     */
    public void setDisSort(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.dis_sort</code>. 排序
     */
    public Integer getDisSort() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>distribution.dis_dictionary.dis_sys_id</code>.
     */
    public void setDisSysId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.dis_sys_id</code>.
     */
    public String getDisSysId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>distribution.dis_dictionary.is_delete</code>.
     */
    public void setIsDelete(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.is_delete</code>.
     */
    public String getIsDelete() {
        return (String) get(6);
    }

    /**
     * Setter for <code>distribution.dis_dictionary.add_time</code>.
     */
    public void setAddTime(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.add_time</code>.
     */
    public String getAddTime() {
        return (String) get(7);
    }

    /**
     * Setter for <code>distribution.dis_dictionary.update_time</code>.
     */
    public void setUpdateTime(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>distribution.dis_dictionary.update_time</code>.
     */
    public String getUpdateTime() {
        return (String) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, String, Integer, String, String, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, String, Integer, String, String, String, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DisDictionary.DIS_DICTIONARY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return DisDictionary.DIS_DICTIONARY.DIS_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DisDictionary.DIS_DICTIONARY.DIS_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DisDictionary.DIS_DICTIONARY.DIS_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return DisDictionary.DIS_DICTIONARY.DIS_SORT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return DisDictionary.DIS_DICTIONARY.DIS_SYS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DisDictionary.DIS_DICTIONARY.IS_DELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return DisDictionary.DIS_DICTIONARY.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return DisDictionary.DIS_DICTIONARY.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getDisCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDisType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getDisValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getDisSort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getDisSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getDisCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDisType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDisValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getDisSort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getDisSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value2(String value) {
        setDisCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value3(String value) {
        setDisType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value4(String value) {
        setDisValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value5(Integer value) {
        setDisSort(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value6(String value) {
        setDisSysId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value7(String value) {
        setIsDelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value8(String value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord value9(String value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisDictionaryRecord values(Integer value1, String value2, String value3, String value4, Integer value5, String value6, String value7, String value8, String value9) {
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
     * Create a detached DisDictionaryRecord
     */
    public DisDictionaryRecord() {
        super(DisDictionary.DIS_DICTIONARY);
    }

    /**
     * Create a detached, initialised DisDictionaryRecord
     */
    public DisDictionaryRecord(Integer id, String disCode, String disType, String disValue, Integer disSort, String disSysId, String isDelete, String addTime, String updateTime) {
        super(DisDictionary.DIS_DICTIONARY);

        set(0, id);
        set(1, disCode);
        set(2, disType);
        set(3, disValue);
        set(4, disSort);
        set(5, disSysId);
        set(6, isDelete);
        set(7, addTime);
        set(8, updateTime);
    }
}