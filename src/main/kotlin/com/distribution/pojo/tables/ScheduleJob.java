/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.DistributionDev;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.ScheduleJobRecord;

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
public class ScheduleJob extends TableImpl<ScheduleJobRecord> {

    private static final long serialVersionUID = -1375920989;

    /**
     * The reference instance of <code>distribution_dev.schedule_job</code>
     */
    public static final ScheduleJob SCHEDULE_JOB = new ScheduleJob();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ScheduleJobRecord> getRecordType() {
        return ScheduleJobRecord.class;
    }

    /**
     * The column <code>distribution_dev.schedule_job.job_id</code>. 任务id
     */
    public final TableField<ScheduleJobRecord, Long> JOB_ID = createField("job_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "任务id");

    /**
     * The column <code>distribution_dev.schedule_job.bean_name</code>. spring bean名称
     */
    public final TableField<ScheduleJobRecord, String> BEAN_NAME = createField("bean_name", org.jooq.impl.SQLDataType.VARCHAR(200), this, "spring bean名称");

    /**
     * The column <code>distribution_dev.schedule_job.method_name</code>. 方法名
     */
    public final TableField<ScheduleJobRecord, String> METHOD_NAME = createField("method_name", org.jooq.impl.SQLDataType.VARCHAR(100), this, "方法名");

    /**
     * The column <code>distribution_dev.schedule_job.params</code>. 参数
     */
    public final TableField<ScheduleJobRecord, String> PARAMS = createField("params", org.jooq.impl.SQLDataType.VARCHAR(2000), this, "参数");

    /**
     * The column <code>distribution_dev.schedule_job.cron_expression</code>. cron ???
     */
    public final TableField<ScheduleJobRecord, String> CRON_EXPRESSION = createField("cron_expression", org.jooq.impl.SQLDataType.VARCHAR(100), this, "cron ???");

    /**
     * The column <code>distribution_dev.schedule_job.status</code>. ????  0???  1???
     */
    public final TableField<ScheduleJobRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER, this, "????  0???  1???");

    /**
     * The column <code>distribution_dev.schedule_job.remark</code>. 备注
     */
    public final TableField<ScheduleJobRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR(255), this, "备注");

    /**
     * The column <code>distribution_dev.schedule_job.create_time</code>. 创建时间
     */
    public final TableField<ScheduleJobRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "创建时间");

    /**
     * Create a <code>distribution_dev.schedule_job</code> table reference
     */
    public ScheduleJob() {
        this(DSL.name("schedule_job"), null);
    }

    /**
     * Create an aliased <code>distribution_dev.schedule_job</code> table reference
     */
    public ScheduleJob(String alias) {
        this(DSL.name(alias), SCHEDULE_JOB);
    }

    /**
     * Create an aliased <code>distribution_dev.schedule_job</code> table reference
     */
    public ScheduleJob(Name alias) {
        this(alias, SCHEDULE_JOB);
    }

    private ScheduleJob(Name alias, Table<ScheduleJobRecord> aliased) {
        this(alias, aliased, null);
    }

    private ScheduleJob(Name alias, Table<ScheduleJobRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "????");
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
        return Arrays.<Index>asList(Indexes.SCHEDULE_JOB_PRIMARY, Indexes.SCHEDULE_JOB_SCHEDULE_JOB_BEAN_NAME_INDEX, Indexes.SCHEDULE_JOB_SCHEDULE_JOB_JOB_ID_INDEX, Indexes.SCHEDULE_JOB_SCHEDULE_JOB_METHOD_NAME_INDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ScheduleJobRecord, Long> getIdentity() {
        return Keys.IDENTITY_SCHEDULE_JOB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ScheduleJobRecord> getPrimaryKey() {
        return Keys.KEY_SCHEDULE_JOB_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ScheduleJobRecord>> getKeys() {
        return Arrays.<UniqueKey<ScheduleJobRecord>>asList(Keys.KEY_SCHEDULE_JOB_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJob as(String alias) {
        return new ScheduleJob(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJob as(Name alias) {
        return new ScheduleJob(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ScheduleJob rename(String name) {
        return new ScheduleJob(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ScheduleJob rename(Name name) {
        return new ScheduleJob(name, null);
    }
}
