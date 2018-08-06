/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.ProductDetailParams;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class ProductDetailParamsRecord extends UpdatableRecordImpl<ProductDetailParamsRecord> implements Record4<Integer, String, Integer, Double> {

    private static final long serialVersionUID = 532112753;

    /**
     * Setter for <code>distribution.product_detail_params.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.product_detail_params.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>distribution.product_detail_params.detail_id</code>.
     */
    public void setDetailId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.product_detail_params.detail_id</code>.
     */
    public String getDetailId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.product_detail_params.level</code>.
     */
    public void setLevel(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.product_detail_params.level</code>.
     */
    public Integer getLevel() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>distribution.product_detail_params.buy_back_price</code>.
     */
    public void setBuyBackPrice(Double value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.product_detail_params.buy_back_price</code>.
     */
    public Double getBuyBackPrice() {
        return (Double) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Integer, Double> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Integer, Double> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ProductDetailParams.PRODUCT_DETAIL_PARAMS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ProductDetailParams.PRODUCT_DETAIL_PARAMS.DETAIL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ProductDetailParams.PRODUCT_DETAIL_PARAMS.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field4() {
        return ProductDetailParams.PRODUCT_DETAIL_PARAMS.BUY_BACK_PRICE;
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
        return getDetailId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component4() {
        return getBuyBackPrice();
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
        return getDetailId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value4() {
        return getBuyBackPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailParamsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailParamsRecord value2(String value) {
        setDetailId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailParamsRecord value3(Integer value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailParamsRecord value4(Double value) {
        setBuyBackPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailParamsRecord values(Integer value1, String value2, Integer value3, Double value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProductDetailParamsRecord
     */
    public ProductDetailParamsRecord() {
        super(ProductDetailParams.PRODUCT_DETAIL_PARAMS);
    }

    /**
     * Create a detached, initialised ProductDetailParamsRecord
     */
    public ProductDetailParamsRecord(Integer id, String detailId, Integer level, Double buyBackPrice) {
        super(ProductDetailParams.PRODUCT_DETAIL_PARAMS);

        set(0, id);
        set(1, detailId);
        set(2, level);
        set(3, buyBackPrice);
    }
}