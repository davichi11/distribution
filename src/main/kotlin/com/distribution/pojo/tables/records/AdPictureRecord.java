/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.AdPicture;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 轮播图
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AdPictureRecord extends UpdatableRecordImpl<AdPictureRecord> implements Record6<Integer, String, Integer, Integer, String, String> {

    private static final long serialVersionUID = 1593134907;

    /**
     * Setter for <code>distribution.ad_picture.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.ad_picture.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>distribution.ad_picture.img_url</code>.
     */
    public void setImgUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.ad_picture.img_url</code>.
     */
    public String getImgUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.ad_picture.article_id</code>. 对应的文章编号
     */
    public void setArticleId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.ad_picture.article_id</code>. 对应的文章编号
     */
    public Integer getArticleId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>distribution.ad_picture.order_num</code>. 排序
     */
    public void setOrderNum(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.ad_picture.order_num</code>. 排序
     */
    public Integer getOrderNum() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>distribution.ad_picture.add_time</code>.
     */
    public void setAddTime(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.ad_picture.add_time</code>.
     */
    public String getAddTime() {
        return (String) get(4);
    }

    /**
     * Setter for <code>distribution.ad_picture.is_delete</code>. 是否启用 0否 1是
     */
    public void setIsDelete(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution.ad_picture.is_delete</code>. 是否启用 0否 1是
     */
    public String getIsDelete() {
        return (String) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, Integer, Integer, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, Integer, Integer, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return AdPicture.AD_PICTURE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return AdPicture.AD_PICTURE.IMG_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return AdPicture.AD_PICTURE.ARTICLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return AdPicture.AD_PICTURE.ORDER_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return AdPicture.AD_PICTURE.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return AdPicture.AD_PICTURE.IS_DELETE;
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
        return getImgUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getArticleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getOrderNum();
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
        return getImgUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getArticleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getOrderNum();
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
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdPictureRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdPictureRecord value2(String value) {
        setImgUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdPictureRecord value3(Integer value) {
        setArticleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdPictureRecord value4(Integer value) {
        setOrderNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdPictureRecord value5(String value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdPictureRecord value6(String value) {
        setIsDelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdPictureRecord values(Integer value1, String value2, Integer value3, Integer value4, String value5, String value6) {
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
     * Create a detached AdPictureRecord
     */
    public AdPictureRecord() {
        super(AdPicture.AD_PICTURE);
    }

    /**
     * Create a detached, initialised AdPictureRecord
     */
    public AdPictureRecord(Integer id, String imgUrl, Integer articleId, Integer orderNum, String addTime, String isDelete) {
        super(AdPicture.AD_PICTURE);

        set(0, id);
        set(1, imgUrl);
        set(2, articleId);
        set(3, orderNum);
        set(4, addTime);
        set(5, isDelete);
    }
}