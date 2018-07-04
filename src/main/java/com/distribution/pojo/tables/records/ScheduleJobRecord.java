/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.records;


import com.distribution.pojo.tables.ScheduleJob;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


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
public class ScheduleJobRecord extends UpdatableRecordImpl<ScheduleJobRecord> implements Record8<Long, String, String, String, String, Boolean, String, Timestamp> {

    private static final long serialVersionUID = -823619806;

    /**
     * Setter for <code>distribution.schedule_job.job_id</code>. 任务id
     */
    public void setJobId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>distribution.schedule_job.job_id</code>. 任务id
     */
    public Long getJobId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>distribution.schedule_job.bean_name</code>. spring bean名称
     */
    public void setBeanName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>distribution.schedule_job.bean_name</code>. spring bean名称
     */
    public String getBeanName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>distribution.schedule_job.method_name</code>. 方法名
     */
    public void setMethodName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>distribution.schedule_job.method_name</code>. 方法名
     */
    public String getMethodName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>distribution.schedule_job.params</code>. 参数
     */
    public void setParams(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>distribution.schedule_job.params</code>. 参数
     */
    public String getParams() {
        return (String) get(3);
    }

    /**
     * Setter for <code>distribution.schedule_job.cron_expression</code>. cron ???
     */
    public void setCronExpression(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>distribution.schedule_job.cron_expression</code>. cron ???
     */
    public String getCronExpression() {
        return (String) get(4);
    }

    /**
     * Setter for <code>distribution.schedule_job.status</code>. ????  0???  1???
     */
    public void setStatus(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>distribution.schedule_job.status</code>. ????  0???  1???
     */
    public Boolean getStatus() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>distribution.schedule_job.remark</code>. 备注
     */
    public void setRemark(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>distribution.schedule_job.remark</code>. 备注
     */
    public String getRemark() {
        return (String) get(6);
    }

    /**
     * Setter for <code>distribution.schedule_job.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>distribution.schedule_job.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, String, String, String, String, Boolean, String, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, String, String, String, String, Boolean, String, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return ScheduleJob.SCHEDULE_JOB.JOB_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ScheduleJob.SCHEDULE_JOB.BEAN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ScheduleJob.SCHEDULE_JOB.METHOD_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ScheduleJob.SCHEDULE_JOB.PARAMS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ScheduleJob.SCHEDULE_JOB.CRON_EXPRESSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return ScheduleJob.SCHEDULE_JOB.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return ScheduleJob.SCHEDULE_JOB.REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return ScheduleJob.SCHEDULE_JOB.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getJobId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getBeanName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getMethodName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getParams();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getCronExpression();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component6() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getJobId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getBeanName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getMethodName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getParams();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCronExpression();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord value1(Long value) {
        setJobId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord value2(String value) {
        setBeanName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord value3(String value) {
        setMethodName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord value4(String value) {
        setParams(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord value5(String value) {
        setCronExpression(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord value6(Boolean value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord value7(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleJobRecord values(Long value1, String value2, String value3, String value4, String value5, Boolean value6, String value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ScheduleJobRecord
     */
    public ScheduleJobRecord() {
        super(ScheduleJob.SCHEDULE_JOB);
    }

    /**
     * Create a detached, initialised ScheduleJobRecord
     */
    public ScheduleJobRecord(Long jobId, String beanName, String methodName, String params, String cronExpression, Boolean status, String remark, Timestamp createTime) {
        super(ScheduleJob.SCHEDULE_JOB);

        set(0, jobId);
        set(1, beanName);
        set(2, methodName);
        set(3, params);
        set(4, cronExpression);
        set(5, status);
        set(6, remark);
        set(7, createTime);
    }
}