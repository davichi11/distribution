/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 分润参数设置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DisProfiParam implements Serializable {

    private static final long serialVersionUID = -705221319;

    private String id;
    private String disPlatformId;
    private String disProMode;
    private String disProType;
    private Double disProValue;
    private String disProLevel;
    private String disUserType;
    private String isDelete;
    private String updateTime;
    private String addTime;

    public DisProfiParam() {}

    public DisProfiParam(DisProfiParam value) {
        this.id = value.id;
        this.disPlatformId = value.disPlatformId;
        this.disProMode = value.disProMode;
        this.disProType = value.disProType;
        this.disProValue = value.disProValue;
        this.disProLevel = value.disProLevel;
        this.disUserType = value.disUserType;
        this.isDelete = value.isDelete;
        this.updateTime = value.updateTime;
        this.addTime = value.addTime;
    }

    public DisProfiParam(
        String id,
        String disPlatformId,
        String disProMode,
        String disProType,
        Double disProValue,
        String disProLevel,
        String disUserType,
        String isDelete,
        String updateTime,
        String addTime
    ) {
        this.id = id;
        this.disPlatformId = disPlatformId;
        this.disProMode = disProMode;
        this.disProType = disProType;
        this.disProValue = disProValue;
        this.disProLevel = disProLevel;
        this.disUserType = disUserType;
        this.isDelete = isDelete;
        this.updateTime = updateTime;
        this.addTime = addTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisPlatformId() {
        return this.disPlatformId;
    }

    public void setDisPlatformId(String disPlatformId) {
        this.disPlatformId = disPlatformId;
    }

    public String getDisProMode() {
        return this.disProMode;
    }

    public void setDisProMode(String disProMode) {
        this.disProMode = disProMode;
    }

    public String getDisProType() {
        return this.disProType;
    }

    public void setDisProType(String disProType) {
        this.disProType = disProType;
    }

    public Double getDisProValue() {
        return this.disProValue;
    }

    public void setDisProValue(Double disProValue) {
        this.disProValue = disProValue;
    }

    public String getDisProLevel() {
        return this.disProLevel;
    }

    public void setDisProLevel(String disProLevel) {
        this.disProLevel = disProLevel;
    }

    public String getDisUserType() {
        return this.disUserType;
    }

    public void setDisUserType(String disUserType) {
        this.disUserType = disUserType;
    }

    public String getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddTime() {
        return this.addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DisProfiParam (");

        sb.append(id);
        sb.append(", ").append(disPlatformId);
        sb.append(", ").append(disProMode);
        sb.append(", ").append(disProType);
        sb.append(", ").append(disProValue);
        sb.append(", ").append(disProLevel);
        sb.append(", ").append(disUserType);
        sb.append(", ").append(isDelete);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(addTime);

        sb.append(")");
        return sb.toString();
    }
}
