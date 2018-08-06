/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.Distribution;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.SysOssRecord;

import java.sql.Timestamp;
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
 * ????
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysOss extends TableImpl<SysOssRecord> {

    private static final long serialVersionUID = 1692546377;

    /**
     * The reference instance of <code>distribution.sys_oss</code>
     */
    public static final SysOss SYS_OSS = new SysOss();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SysOssRecord> getRecordType() {
        return SysOssRecord.class;
    }

    /**
     * The column <code>distribution.sys_oss.id</code>.
     */
    public final TableField<SysOssRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>distribution.sys_oss.url</code>. URL地址
     */
    public final TableField<SysOssRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR(200), this, "URL地址");

    /**
     * The column <code>distribution.sys_oss.create_date</code>. 创建时间
     */
    public final TableField<SysOssRecord, Timestamp> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "创建时间");

    /**
     * Create a <code>distribution.sys_oss</code> table reference
     */
    public SysOss() {
        this(DSL.name("sys_oss"), null);
    }

    /**
     * Create an aliased <code>distribution.sys_oss</code> table reference
     */
    public SysOss(String alias) {
        this(DSL.name(alias), SYS_OSS);
    }

    /**
     * Create an aliased <code>distribution.sys_oss</code> table reference
     */
    public SysOss(Name alias) {
        this(alias, SYS_OSS);
    }

    private SysOss(Name alias, Table<SysOssRecord> aliased) {
        this(alias, aliased, null);
    }

    private SysOss(Name alias, Table<SysOssRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "????");
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
        return Arrays.<Index>asList(Indexes.SYS_OSS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<SysOssRecord, Long> getIdentity() {
        return Keys.IDENTITY_SYS_OSS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SysOssRecord> getPrimaryKey() {
        return Keys.KEY_SYS_OSS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SysOssRecord>> getKeys() {
        return Arrays.<UniqueKey<SysOssRecord>>asList(Keys.KEY_SYS_OSS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysOss as(String alias) {
        return new SysOss(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysOss as(Name alias) {
        return new SysOss(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SysOss rename(String name) {
        return new SysOss(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SysOss rename(Name name) {
        return new SysOss(name, null);
    }
}