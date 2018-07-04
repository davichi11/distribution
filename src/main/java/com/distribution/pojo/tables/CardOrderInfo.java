/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.Distribution;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.CardOrderInfoRecord;

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
 * 办卡订单信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CardOrderInfo extends TableImpl<CardOrderInfoRecord> {

    private static final long serialVersionUID = 2023957227;

    /**
     * The reference instance of <code>distribution.card_order_info</code>
     */
    public static final CardOrderInfo CARD_ORDER_INFO = new CardOrderInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CardOrderInfoRecord> getRecordType() {
        return CardOrderInfoRecord.class;
    }

    /**
     * The column <code>distribution.card_order_info.id</code>.
     */
    public final TableField<CardOrderInfoRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>distribution.card_order_info.order_name</code>.
     */
    public final TableField<CardOrderInfoRecord, String> ORDER_NAME = createField("order_name", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>distribution.card_order_info.order_id</code>.
     */
    public final TableField<CardOrderInfoRecord, String> ORDER_ID = createField("order_id", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>distribution.card_order_info.order_mobile</code>.
     */
    public final TableField<CardOrderInfoRecord, String> ORDER_MOBILE = createField("order_mobile", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>distribution.card_order_info.order_idcardno</code>.
     */
    public final TableField<CardOrderInfoRecord, String> ORDER_IDCARDNO = createField("order_idcardno", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>distribution.card_order_info.order_email</code>.
     */
    public final TableField<CardOrderInfoRecord, String> ORDER_EMAIL = createField("order_email", org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>distribution.card_order_info.order_status</code>. 订单状态 0:失败,1:成功,2:申请中
     */
    public final TableField<CardOrderInfoRecord, Integer> ORDER_STATUS = createField("order_status", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("2", org.jooq.impl.SQLDataType.INTEGER)), this, "订单状态 0:失败,1:成功,2:申请中");

    /**
     * The column <code>distribution.card_order_info.member_id</code>. 用户关联ID
     */
    public final TableField<CardOrderInfoRecord, String> MEMBER_ID = createField("member_id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "用户关联ID");

    /**
     * The column <code>distribution.card_order_info.card_id</code>. 关联信用卡ID
     */
    public final TableField<CardOrderInfoRecord, String> CARD_ID = createField("card_id", org.jooq.impl.SQLDataType.VARCHAR(32), this, "关联信用卡ID");

    /**
     * The column <code>distribution.card_order_info.is_delete</code>.
     */
    public final TableField<CardOrderInfoRecord, String> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.VARCHAR(1), this, "");

    /**
     * The column <code>distribution.card_order_info.add_time</code>.
     */
    public final TableField<CardOrderInfoRecord, String> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>distribution.card_order_info.update_time</code>.
     */
    public final TableField<CardOrderInfoRecord, String> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>distribution.card_order_info.order_bank_status</code>. 银行进度查询备用字段 0未查（办卡中）。1己查（申请中）。2己查（通过）
     */
    public final TableField<CardOrderInfoRecord, Integer> ORDER_BANK_STATUS = createField("order_bank_status", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "银行进度查询备用字段 0未查（办卡中）。1己查（申请中）。2己查（通过）");

    /**
     * Create a <code>distribution.card_order_info</code> table reference
     */
    public CardOrderInfo() {
        this(DSL.name("card_order_info"), null);
    }

    /**
     * Create an aliased <code>distribution.card_order_info</code> table reference
     */
    public CardOrderInfo(String alias) {
        this(DSL.name(alias), CARD_ORDER_INFO);
    }

    /**
     * Create an aliased <code>distribution.card_order_info</code> table reference
     */
    public CardOrderInfo(Name alias) {
        this(alias, CARD_ORDER_INFO);
    }

    private CardOrderInfo(Name alias, Table<CardOrderInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private CardOrderInfo(Name alias, Table<CardOrderInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "办卡订单信息表");
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
        return Arrays.<Index>asList(Indexes.CARD_ORDER_INFO_CARD_ORDER_INFO_ID_UINDEX, Indexes.CARD_ORDER_INFO_CARD_ORDER_INFO__INDEX_1, Indexes.CARD_ORDER_INFO_CARD_ORDER_INFO__INDEX_2, Indexes.CARD_ORDER_INFO_CARD_ORDER_INFO__INDEX_3, Indexes.CARD_ORDER_INFO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CardOrderInfoRecord> getPrimaryKey() {
        return Keys.KEY_CARD_ORDER_INFO_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CardOrderInfoRecord>> getKeys() {
        return Arrays.<UniqueKey<CardOrderInfoRecord>>asList(Keys.KEY_CARD_ORDER_INFO_PRIMARY, Keys.KEY_CARD_ORDER_INFO_CARD_ORDER_INFO_ID_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardOrderInfo as(String alias) {
        return new CardOrderInfo(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardOrderInfo as(Name alias) {
        return new CardOrderInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CardOrderInfo rename(String name) {
        return new CardOrderInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CardOrderInfo rename(Name name) {
        return new CardOrderInfo(name, null);
    }
}