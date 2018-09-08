/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.Distribution;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.DisMemberInfoRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 用户表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DisMemberInfo extends TableImpl<DisMemberInfoRecord> {

    private static final long serialVersionUID = -1530827719;

    /**
     * The reference instance of <code>distribution.dis_member_info</code>
     */
    public static final DisMemberInfo DIS_MEMBER_INFO = new DisMemberInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DisMemberInfoRecord> getRecordType() {
        return DisMemberInfoRecord.class;
    }

    /**
     * The column <code>distribution.dis_member_info.id</code>. 主键
     */
    public final TableField<DisMemberInfoRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "主键");

    /**
     * The column <code>distribution.dis_member_info.dis_platform_id</code>. 工号
     */
    public final TableField<DisMemberInfoRecord, Long> DIS_PLATFORM_ID = createField("dis_platform_id", org.jooq.impl.SQLDataType.BIGINT, this, "工号");

    /**
     * The column <code>distribution.dis_member_info.dis_user_id</code>. 用户id
     */
    public final TableField<DisMemberInfoRecord, String> DIS_USER_ID = createField("dis_user_id", org.jooq.impl.SQLDataType.VARCHAR(100), this, "用户id");

    /**
     * The column <code>distribution.dis_member_info.dis_model_id</code>. 上级id
     */
    public final TableField<DisMemberInfoRecord, String> DIS_MODEL_ID = createField("dis_model_id", org.jooq.impl.SQLDataType.VARCHAR(100), this, "上级id");

    /**
     * The column <code>distribution.dis_member_info.dis_full_index</code>. 全路径
     */
    public final TableField<DisMemberInfoRecord, String> DIS_FULL_INDEX = createField("dis_full_index", org.jooq.impl.SQLDataType.VARCHAR(100), this, "全路径");

    /**
     * The column <code>distribution.dis_member_info.dis_user_name</code>.
     */
    public final TableField<DisMemberInfoRecord, String> DIS_USER_NAME = createField("dis_user_name", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>distribution.dis_member_info.dis_level</code>. 级别
     */
    public final TableField<DisMemberInfoRecord, Integer> DIS_LEVEL = createField("dis_level", org.jooq.impl.SQLDataType.INTEGER, this, "级别");

    /**
     * The column <code>distribution.dis_member_info.dis_user_type</code>. 身份类型(0 非会员 1会员)
     */
    public final TableField<DisMemberInfoRecord, String> DIS_USER_TYPE = createField("dis_user_type", org.jooq.impl.SQLDataType.VARCHAR(10).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "身份类型(0 非会员 1会员)");

    /**
     * The column <code>distribution.dis_member_info.dis_user_id_code</code>. 身份证号
     */
    public final TableField<DisMemberInfoRecord, String> DIS_USER_ID_CODE = createField("dis_user_id_code", org.jooq.impl.SQLDataType.VARCHAR(18).nullable(false), this, "身份证号");

    /**
     * The column <code>distribution.dis_member_info.dis_note</code>. 备注
     */
    public final TableField<DisMemberInfoRecord, String> DIS_NOTE = createField("dis_note", org.jooq.impl.SQLDataType.VARCHAR(100), this, "备注");

    /**
     * The column <code>distribution.dis_member_info.add_time</code>. 添加时间
     */
    public final TableField<DisMemberInfoRecord, String> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.VARCHAR(20), this, "添加时间");

    /**
     * The column <code>distribution.dis_member_info.update_time</code>. 更新时间
     */
    public final TableField<DisMemberInfoRecord, String> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.VARCHAR(20), this, "更新时间");

    /**
     * The column <code>distribution.dis_member_info.is_delete</code>. 删除状态
     */
    public final TableField<DisMemberInfoRecord, String> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.VARCHAR(1).defaultValue(org.jooq.impl.DSL.inline("N", org.jooq.impl.SQLDataType.VARCHAR)), this, "删除状态");

    /**
     * The column <code>distribution.dis_member_info.open_id</code>. 微信openID
     */
    public final TableField<DisMemberInfoRecord, String> OPEN_ID = createField("open_id", org.jooq.impl.SQLDataType.VARCHAR(32), this, "微信openID");

    /**
     * Create a <code>distribution.dis_member_info</code> table reference
     */
    public DisMemberInfo() {
        this(DSL.name("dis_member_info"), null);
    }

    /**
     * Create an aliased <code>distribution.dis_member_info</code> table reference
     */
    public DisMemberInfo(String alias) {
        this(DSL.name(alias), DIS_MEMBER_INFO);
    }

    /**
     * Create an aliased <code>distribution.dis_member_info</code> table reference
     */
    public DisMemberInfo(Name alias) {
        this(alias, DIS_MEMBER_INFO);
    }

    private DisMemberInfo(Name alias, Table<DisMemberInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private DisMemberInfo(Name alias, Table<DisMemberInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "用户表");
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
        return Arrays.<Index>asList(Indexes.DIS_MEMBER_INFO_DIS_MEMBER_INFO_ID_UINDEX, Indexes.DIS_MEMBER_INFO_DIS_MEMBER_INFO__INDEX_1, Indexes.DIS_MEMBER_INFO_DIS_MEMBER_INFO__INDEX_2, Indexes.DIS_MEMBER_INFO_DIS_MEMBER_INFO__INDEX_3, Indexes.DIS_MEMBER_INFO_DIS_MEMBER_INFO__INDEX_4, Indexes.DIS_MEMBER_INFO_DIS_MEMBER_INFO__INDEX_5, Indexes.DIS_MEMBER_INFO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DisMemberInfoRecord> getPrimaryKey() {
        return Keys.KEY_DIS_MEMBER_INFO_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DisMemberInfoRecord>> getKeys() {
        return Arrays.<UniqueKey<DisMemberInfoRecord>>asList(Keys.KEY_DIS_MEMBER_INFO_PRIMARY, Keys.KEY_DIS_MEMBER_INFO_DIS_MEMBER_INFO_ID_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisMemberInfo as(String alias) {
        return new DisMemberInfo(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisMemberInfo as(Name alias) {
        return new DisMemberInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DisMemberInfo rename(String name) {
        return new DisMemberInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DisMemberInfo rename(Name name) {
        return new DisMemberInfo(name, null);
    }
}
