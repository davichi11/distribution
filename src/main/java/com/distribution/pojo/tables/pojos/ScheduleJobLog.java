/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * ??????
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ScheduleJobLog implements Serializable {

    private static final long serialVersionUID = -852880497;

    private Long      logId;
    private Long      jobId;
    private String    beanName;
    private String    methodName;
    private String    params;
    private Boolean   status;
    private String    error;
    private Integer   times;
    private Timestamp createTime;

    public ScheduleJobLog() {}

    public ScheduleJobLog(ScheduleJobLog value) {
        this.logId = value.logId;
        this.jobId = value.jobId;
        this.beanName = value.beanName;
        this.methodName = value.methodName;
        this.params = value.params;
        this.status = value.status;
        this.error = value.error;
        this.times = value.times;
        this.createTime = value.createTime;
    }

    public ScheduleJobLog(
        Long      logId,
        Long      jobId,
        String    beanName,
        String    methodName,
        String    params,
        Boolean   status,
        String    error,
        Integer   times,
        Timestamp createTime
    ) {
        this.logId = logId;
        this.jobId = jobId;
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
        this.status = status;
        this.error = error;
        this.times = times;
        this.createTime = createTime;
    }

    public Long getLogId() {
        return this.logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getTimes() {
        return this.times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ScheduleJobLog (");

        sb.append(logId);
        sb.append(", ").append(jobId);
        sb.append(", ").append(beanName);
        sb.append(", ").append(methodName);
        sb.append(", ").append(params);
        sb.append(", ").append(status);
        sb.append(", ").append(error);
        sb.append(", ").append(times);
        sb.append(", ").append(createTime);

        sb.append(")");
        return sb.toString();
    }
}