/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.ArticleInfo;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 文章表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ArticleInfoRecord extends UpdatableRecordImpl<ArticleInfoRecord> implements Record7<Integer, String, String, String, Integer, String, String> {

    private static final long serialVersionUID = -1577958357;

    /**
     * Setter for <code>distribution.article_info.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.article_info.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>distribution.article_info.title</code>. 文章标题
     */
    public void setTitle(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.article_info.title</code>. 文章标题
     */
    public String getTitle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.article_info.detail</code>. 文章内容
     */
    public void setDetail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.article_info.detail</code>. 文章内容
     */
    public String getDetail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>distribution.article_info.type</code>. 文章类别 0:客户推广 1:热门活动
     */
    public void setType(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.article_info.type</code>. 文章类别 0:客户推广 1:热门活动
     */
    public String getType() {
        return (String) get(3);
    }

    /**
     * Setter for <code>distribution.article_info.order_num</code>.
     */
    public void setOrderNum(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.article_info.order_num</code>.
     */
    public Integer getOrderNum() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>distribution.article_info.add_time</code>.
     */
    public void setAddTime(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution.article_info.add_time</code>.
     */
    public String getAddTime() {
        return (String) get(5);
    }

    /**
     * Setter for <code>distribution.article_info.is_delete</code>.
     */
    public void setIsDelete(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>distribution.article_info.is_delete</code>.
     */
    public String getIsDelete() {
        return (String) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, String, String, Integer, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, String, String, Integer, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ArticleInfo.ARTICLE_INFO.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ArticleInfo.ARTICLE_INFO.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ArticleInfo.ARTICLE_INFO.DETAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ArticleInfo.ARTICLE_INFO.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return ArticleInfo.ARTICLE_INFO.ORDER_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ArticleInfo.ARTICLE_INFO.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return ArticleInfo.ARTICLE_INFO.IS_DELETE;
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
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDetail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getOrderNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getAddTime();
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
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDetail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getOrderNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getAddTime();
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
    public ArticleInfoRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfoRecord value2(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfoRecord value3(String value) {
        setDetail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfoRecord value4(String value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfoRecord value5(Integer value) {
        setOrderNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfoRecord value6(String value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfoRecord value7(String value) {
        setIsDelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfoRecord values(Integer value1, String value2, String value3, String value4, Integer value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ArticleInfoRecord
     */
    public ArticleInfoRecord() {
        super(ArticleInfo.ARTICLE_INFO);
    }

    /**
     * Create a detached, initialised ArticleInfoRecord
     */
    public ArticleInfoRecord(Integer id, String title, String detail, String type, Integer orderNum, String addTime, String isDelete) {
        super(ArticleInfo.ARTICLE_INFO);

        set(0, id);
        set(1, title);
        set(2, detail);
        set(3, type);
        set(4, orderNum);
        set(5, addTime);
        set(6, isDelete);
    }
}
