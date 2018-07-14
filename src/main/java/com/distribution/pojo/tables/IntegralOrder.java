/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.Distribution;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.IntegralOrderRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * 积分兑换申请表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IntegralOrder extends TableImpl<IntegralOrderRecord> {

    private static final long serialVersionUID = -1420510928;

    /**
     * The reference instance of <code>distribution.integral_order</code>
     */
    public static final IntegralOrder INTEGRAL_ORDER = new IntegralOrder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IntegralOrderRecord> getRecordType() {
        return IntegralOrderRecord.class;
    }

    /**
     * The column <code>distribution.integral_order.id</code>.
     */
    public final TableField<IntegralOrderRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>distribution.integral_order.mobile</code>. 兑换人手机号
     */
    public final TableField<IntegralOrderRecord, Long> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "兑换人手机号");

    /**
     * The column <code>distribution.integral_order.detail_id</code>. 兑换产品ID
     */
    public final TableField<IntegralOrderRecord, String> DETAIL_ID = createField("detail_id", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "兑换产品ID");

    /**
     * The column <code>distribution.integral_order.img</code>. 卷码图片,可以逗号分隔
     */
    public final TableField<IntegralOrderRecord, String> IMG = createField("img", org.jooq.impl.SQLDataType.VARCHAR(2000), this, "卷码图片,可以逗号分隔");

    /**
     * The column <code>distribution.integral_order.add_time</code>. 申请时间
     */
    public final TableField<IntegralOrderRecord, String> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "申请时间");

    /**
     * The column <code>distribution.integral_order.status</code>. 审核状态:0失败,1成功,2审核中
     */
    public final TableField<IntegralOrderRecord, String> STATUS = createField("status", org.jooq.impl.SQLDataType.CHAR(1).nullable(false).defaultValue(org.jooq.impl.DSL.inline("2", org.jooq.impl.SQLDataType.CHAR)), this, "审核状态:0失败,1成功,2审核中");

    /**
     * The column <code>distribution.integral_order.record</code>. 上传短信记录
     */
    public final TableField<IntegralOrderRecord, String> RECORD = createField("record", org.jooq.impl.SQLDataType.VARCHAR(500), this, "上传短信记录");

    /**
     * The column <code>distribution.integral_order.profi_money</code>. 分润金额
     */
    public final TableField<IntegralOrderRecord, Double> PROFI_MONEY = createField("profi_money", org.jooq.impl.SQLDataType.DOUBLE.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.DOUBLE)), this, "分润金额");

    /**
     * Create a <code>distribution.integral_order</code> table reference
     */
    public IntegralOrder() {
        this(DSL.name("integral_order"), null);
    }

    /**
     * Create an aliased <code>distribution.integral_order</code> table reference
     */
    public IntegralOrder(String alias) {
        this(DSL.name(alias), INTEGRAL_ORDER);
    }

    /**
     * Create an aliased <code>distribution.integral_order</code> table reference
     */
    public IntegralOrder(Name alias) {
        this(alias, INTEGRAL_ORDER);
    }

    private IntegralOrder(Name alias, Table<IntegralOrderRecord> aliased) {
        this(alias, aliased, null);
    }

    private IntegralOrder(Name alias, Table<IntegralOrderRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "积分兑换申请表");
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
        return Arrays.<Index>asList(Indexes.INTEGRAL_ORDER_INTEGRAL_ORDER_ID_UINDEX, Indexes.INTEGRAL_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<IntegralOrderRecord> getPrimaryKey() {
        return Keys.KEY_INTEGRAL_ORDER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<IntegralOrderRecord>> getKeys() {
        return Arrays.<UniqueKey<IntegralOrderRecord>>asList(Keys.KEY_INTEGRAL_ORDER_PRIMARY, Keys.KEY_INTEGRAL_ORDER_INTEGRAL_ORDER_ID_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrder as(String alias) {
        return new IntegralOrder(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralOrder as(Name alias) {
        return new IntegralOrder(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IntegralOrder rename(String name) {
        return new IntegralOrder(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IntegralOrder rename(Name name) {
        return new IntegralOrder(name, null);
    }
}
