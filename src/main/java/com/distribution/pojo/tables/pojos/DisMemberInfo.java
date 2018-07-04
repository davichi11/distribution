/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class DisMemberInfo implements Serializable {

    private static final long serialVersionUID = 1147267453;

    private String  id;
    private Long    disPlatformId;
    private String  disUserId;
    private String  disModelId;
    private String  disFullIndex;
    private String  disUserName;
    private Integer disLevel;
    private String  disUserType;
    private String  disUserIdCode;
    private String  disNote;
    private String  addTime;
    private String  updateTime;
    private String  isDelete;
    private String  openId;

    public DisMemberInfo() {}

    public DisMemberInfo(DisMemberInfo value) {
        this.id = value.id;
        this.disPlatformId = value.disPlatformId;
        this.disUserId = value.disUserId;
        this.disModelId = value.disModelId;
        this.disFullIndex = value.disFullIndex;
        this.disUserName = value.disUserName;
        this.disLevel = value.disLevel;
        this.disUserType = value.disUserType;
        this.disUserIdCode = value.disUserIdCode;
        this.disNote = value.disNote;
        this.addTime = value.addTime;
        this.updateTime = value.updateTime;
        this.isDelete = value.isDelete;
        this.openId = value.openId;
    }

    public DisMemberInfo(
        String  id,
        Long    disPlatformId,
        String  disUserId,
        String  disModelId,
        String  disFullIndex,
        String  disUserName,
        Integer disLevel,
        String  disUserType,
        String  disUserIdCode,
        String  disNote,
        String  addTime,
        String  updateTime,
        String  isDelete,
        String  openId
    ) {
        this.id = id;
        this.disPlatformId = disPlatformId;
        this.disUserId = disUserId;
        this.disModelId = disModelId;
        this.disFullIndex = disFullIndex;
        this.disUserName = disUserName;
        this.disLevel = disLevel;
        this.disUserType = disUserType;
        this.disUserIdCode = disUserIdCode;
        this.disNote = disNote;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.openId = openId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDisPlatformId() {
        return this.disPlatformId;
    }

    public void setDisPlatformId(Long disPlatformId) {
        this.disPlatformId = disPlatformId;
    }

    public String getDisUserId() {
        return this.disUserId;
    }

    public void setDisUserId(String disUserId) {
        this.disUserId = disUserId;
    }

    public String getDisModelId() {
        return this.disModelId;
    }

    public void setDisModelId(String disModelId) {
        this.disModelId = disModelId;
    }

    public String getDisFullIndex() {
        return this.disFullIndex;
    }

    public void setDisFullIndex(String disFullIndex) {
        this.disFullIndex = disFullIndex;
    }

    public String getDisUserName() {
        return this.disUserName;
    }

    public void setDisUserName(String disUserName) {
        this.disUserName = disUserName;
    }

    public Integer getDisLevel() {
        return this.disLevel;
    }

    public void setDisLevel(Integer disLevel) {
        this.disLevel = disLevel;
    }

    public String getDisUserType() {
        return this.disUserType;
    }

    public void setDisUserType(String disUserType) {
        this.disUserType = disUserType;
    }

    public String getDisUserIdCode() {
        return this.disUserIdCode;
    }

    public void setDisUserIdCode(String disUserIdCode) {
        this.disUserIdCode = disUserIdCode;
    }

    public String getDisNote() {
        return this.disNote;
    }

    public void setDisNote(String disNote) {
        this.disNote = disNote;
    }

    public String getAddTime() {
        return this.addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DisMemberInfo (");

        sb.append(id);
        sb.append(", ").append(disPlatformId);
        sb.append(", ").append(disUserId);
        sb.append(", ").append(disModelId);
        sb.append(", ").append(disFullIndex);
        sb.append(", ").append(disUserName);
        sb.append(", ").append(disLevel);
        sb.append(", ").append(disUserType);
        sb.append(", ").append(disUserIdCode);
        sb.append(", ").append(disNote);
        sb.append(", ").append(addTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(isDelete);
        sb.append(", ").append(openId);

        sb.append(")");
        return sb.toString();
    }
}