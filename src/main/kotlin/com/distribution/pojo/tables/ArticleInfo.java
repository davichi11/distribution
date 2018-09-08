/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.DistributionDev;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.ArticleInfoRecord;

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
public class ArticleInfo extends TableImpl<ArticleInfoRecord> {

    private static final long serialVersionUID = 705217514;

    /**
     * The reference instance of <code>distribution_dev.article_info</code>
     */
    public static final ArticleInfo ARTICLE_INFO = new ArticleInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ArticleInfoRecord> getRecordType() {
        return ArticleInfoRecord.class;
    }

    /**
     * The column <code>distribution_dev.article_info.id</code>.
     */
    public final TableField<ArticleInfoRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>distribution_dev.article_info.title</code>. 文章标题
     */
    public final TableField<ArticleInfoRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "文章标题");

    /**
     * The column <code>distribution_dev.article_info.detail</code>. 文章内容
     */
    public final TableField<ArticleInfoRecord, String> DETAIL = createField("detail", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "文章内容");

    /**
     * The column <code>distribution_dev.article_info.type</code>. 文章类别 0:客户推广 1:热门活动
     */
    public final TableField<ArticleInfoRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.CHAR(1).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.CHAR)), this, "文章类别 0:客户推广 1:热门活动");

    /**
     * The column <code>distribution_dev.article_info.order_num</code>.
     */
    public final TableField<ArticleInfoRecord, Integer> ORDER_NUM = createField("order_num", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>distribution_dev.article_info.add_time</code>.
     */
    public final TableField<ArticleInfoRecord, String> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>distribution_dev.article_info.is_delete</code>.
     */
    public final TableField<ArticleInfoRecord, String> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.CHAR(1).nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.CHAR)), this, "");

    /**
     * Create a <code>distribution_dev.article_info</code> table reference
     */
    public ArticleInfo() {
        this(DSL.name("article_info"), null);
    }

    /**
     * Create an aliased <code>distribution_dev.article_info</code> table reference
     */
    public ArticleInfo(String alias) {
        this(DSL.name(alias), ARTICLE_INFO);
    }

    /**
     * Create an aliased <code>distribution_dev.article_info</code> table reference
     */
    public ArticleInfo(Name alias) {
        this(alias, ARTICLE_INFO);
    }

    private ArticleInfo(Name alias, Table<ArticleInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private ArticleInfo(Name alias, Table<ArticleInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "文章表");
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
        return Arrays.<Index>asList(Indexes.ARTICLE_INFO_ARTICLE_INFO_ID_INDEX, Indexes.ARTICLE_INFO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ArticleInfoRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ARTICLE_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ArticleInfoRecord> getPrimaryKey() {
        return Keys.KEY_ARTICLE_INFO_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ArticleInfoRecord>> getKeys() {
        return Arrays.<UniqueKey<ArticleInfoRecord>>asList(Keys.KEY_ARTICLE_INFO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfo as(String alias) {
        return new ArticleInfo(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleInfo as(Name alias) {
        return new ArticleInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ArticleInfo rename(String name) {
        return new ArticleInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ArticleInfo rename(Name name) {
        return new ArticleInfo(name, null);
    }
}
