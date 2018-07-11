/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.ProductType;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 积分兑换产品类型表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductTypeRecord extends UpdatableRecordImpl<ProductTypeRecord> implements Record7<String, String, String, String, String, String, Double> {

    private static final long serialVersionUID = 477154641;

    /**
     * Setter for <code>distribution.product_type.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.product_type.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>distribution.product_type.prod_name</code>. 产品类型名称
     */
    public void setProdName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.product_type.prod_name</code>. 产品类型名称
     */
    public String getProdName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.product_type.prod_rate</code>. 结算周期
     */
    public void setProdRate(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.product_type.prod_rate</code>. 结算周期
     */
    public String getProdRate() {
        return (String) get(2);
    }

    /**
     * Setter for <code>distribution.product_type.prod_remark</code>. 产品描述
     */
    public void setProdRemark(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.product_type.prod_remark</code>. 产品描述
     */
    public String getProdRemark() {
        return (String) get(3);
    }

    /**
     * Setter for <code>distribution.product_type.prod_img</code>. 图标
     */
    public void setProdImg(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.product_type.prod_img</code>. 图标
     */
    public String getProdImg() {
        return (String) get(4);
    }

    /**
     * Setter for <code>distribution.product_type.prod_type</code>. 提单类型 0:短信,1:图片
     */
    public void setProdType(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution.product_type.prod_type</code>. 提单类型 0:短信,1:图片
     */
    public String getProdType() {
        return (String) get(5);
    }

    /**
     * Setter for <code>distribution.product_type.exchange_percent</code>. 积分兑换比率
     */
    public void setExchangePercent(Double value) {
        set(6, value);
    }

    /**
     * Getter for <code>distribution.product_type.exchange_percent</code>. 积分兑换比率
     */
    public Double getExchangePercent() {
        return (Double) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, String, Double> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, String, Double> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return ProductType.PRODUCT_TYPE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ProductType.PRODUCT_TYPE.PROD_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ProductType.PRODUCT_TYPE.PROD_RATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ProductType.PRODUCT_TYPE.PROD_REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ProductType.PRODUCT_TYPE.PROD_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ProductType.PRODUCT_TYPE.PROD_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field7() {
        return ProductType.PRODUCT_TYPE.EXCHANGE_PERCENT;
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
        return getProdName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getProdRate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getProdRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getProdImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getProdType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component7() {
        return getExchangePercent();
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
        return getProdName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getProdRate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getProdRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getProdImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getProdType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value7() {
        return getExchangePercent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeRecord value2(String value) {
        setProdName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeRecord value3(String value) {
        setProdRate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeRecord value4(String value) {
        setProdRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeRecord value5(String value) {
        setProdImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeRecord value6(String value) {
        setProdType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeRecord value7(Double value) {
        setExchangePercent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeRecord values(String value1, String value2, String value3, String value4, String value5, String value6, Double value7) {
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
     * Create a detached ProductTypeRecord
     */
    public ProductTypeRecord() {
        super(ProductType.PRODUCT_TYPE);
    }

    /**
     * Create a detached, initialised ProductTypeRecord
     */
    public ProductTypeRecord(String id, String prodName, String prodRate, String prodRemark, String prodImg, String prodType, Double exchangePercent) {
        super(ProductType.PRODUCT_TYPE);

        set(0, id);
        set(1, prodName);
        set(2, prodRate);
        set(3, prodRemark);
        set(4, prodImg);
        set(5, prodType);
        set(6, exchangePercent);
    }
}
