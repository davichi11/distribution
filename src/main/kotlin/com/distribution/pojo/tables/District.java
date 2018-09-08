/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.DistributionDev;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.tables.records.DistrictRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
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
public class District extends TableImpl<DistrictRecord> {

    private static final long serialVersionUID = 1144949412;

    /**
     * The reference instance of <code>distribution_dev.district</code>
     */
    public static final District DISTRICT = new District();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DistrictRecord> getRecordType() {
        return DistrictRecord.class;
    }

    /**
     * The column <code>distribution_dev.district.id</code>.
     */
    public final TableField<DistrictRecord, Short> ID = createField("id", org.jooq.impl.SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>distribution_dev.district.name</code>.
     */
    public final TableField<DistrictRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(270), this, "");

    /**
     * The column <code>distribution_dev.district.parent_id</code>.
     */
    public final TableField<DistrictRecord, Short> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>distribution_dev.district.code</code>.
     */
    public final TableField<DistrictRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CHAR(30), this, "");

    /**
     * The column <code>distribution_dev.district.order</code>.
     */
    public final TableField<DistrictRecord, Integer> ORDER = createField("order", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>distribution_dev.district</code> table reference
     */
    public District() {
        this(DSL.name("district"), null);
    }

    /**
     * Create an aliased <code>distribution_dev.district</code> table reference
     */
    public District(String alias) {
        this(DSL.name(alias), DISTRICT);
    }

    /**
     * Create an aliased <code>distribution_dev.district</code> table reference
     */
    public District(Name alias) {
        this(alias, DISTRICT);
    }

    private District(Name alias, Table<DistrictRecord> aliased) {
        this(alias, aliased, null);
    }

    private District(Name alias, Table<DistrictRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DistributionDev.DISTRIBUTION_DEV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DISTRICT_DISTRICT_ID_INDEX, Indexes.DISTRICT_DISTRICT_PARENT_ID_INDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public District as(String alias) {
        return new District(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public District as(Name alias) {
        return new District(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public District rename(String name) {
        return new District(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public District rename(Name name) {
        return new District(name, null);
    }
}
