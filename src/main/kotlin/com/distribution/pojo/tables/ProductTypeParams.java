/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.Distribution;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.ProductTypeParamsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class ProductTypeParams extends TableImpl<ProductTypeParamsRecord> {

    private static final long serialVersionUID = -600947089;

    /**
     * The reference instance of <code>distribution.product_type_params</code>
     */
    public static final ProductTypeParams PRODUCT_TYPE_PARAMS = new ProductTypeParams();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductTypeParamsRecord> getRecordType() {
        return ProductTypeParamsRecord.class;
    }

    /**
     * The column <code>distribution.product_type_params.id</code>.
     */
    public final TableField<ProductTypeParamsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>distribution.product_type_params.level</code>. 会员等级
     */
    public final TableField<ProductTypeParamsRecord, Integer> LEVEL = createField("level", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "会员等级");

    /**
     * The column <code>distribution.product_type_params.exchange_percent</code>. 积分兑换百分比
     */
    public final TableField<ProductTypeParamsRecord, Double> EXCHANGE_PERCENT = createField("exchange_percent", org.jooq.impl.SQLDataType.DOUBLE, this, "积分兑换百分比");

    /**
     * The column <code>distribution.product_type_params.type_id</code>.
     */
    public final TableField<ProductTypeParamsRecord, String> TYPE_ID = createField("type_id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * Create a <code>distribution.product_type_params</code> table reference
     */
    public ProductTypeParams() {
        this(DSL.name("product_type_params"), null);
    }

    /**
     * Create an aliased <code>distribution.product_type_params</code> table reference
     */
    public ProductTypeParams(String alias) {
        this(DSL.name(alias), PRODUCT_TYPE_PARAMS);
    }

    /**
     * Create an aliased <code>distribution.product_type_params</code> table reference
     */
    public ProductTypeParams(Name alias) {
        this(alias, PRODUCT_TYPE_PARAMS);
    }

    private ProductTypeParams(Name alias, Table<ProductTypeParamsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProductTypeParams(Name alias, Table<ProductTypeParamsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Distribution.DISTRIBUTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRODUCT_TYPE_PARAMS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProductTypeParamsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PRODUCT_TYPE_PARAMS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProductTypeParamsRecord> getPrimaryKey() {
        return Keys.KEY_PRODUCT_TYPE_PARAMS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProductTypeParamsRecord>> getKeys() {
        return Arrays.<UniqueKey<ProductTypeParamsRecord>>asList(Keys.KEY_PRODUCT_TYPE_PARAMS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeParams as(String alias) {
        return new ProductTypeParams(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTypeParams as(Name alias) {
        return new ProductTypeParams(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductTypeParams rename(String name) {
        return new ProductTypeParams(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductTypeParams rename(Name name) {
        return new ProductTypeParams(name, null);
    }
}