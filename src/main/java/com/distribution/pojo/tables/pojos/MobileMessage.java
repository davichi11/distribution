/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 发送短信信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MobileMessage implements Serializable {

    private static final long serialVersionUID = -308644271;

    private String  id;
    private String  mobile;
    private String  code;
    private String  sendTime;
    private Integer count;
    private String  addTime;
    private String  isDelete;
    private String  updateTime;

    public MobileMessage() {}

    public MobileMessage(MobileMessage value) {
        this.id = value.id;
        this.mobile = value.mobile;
        this.code = value.code;
        this.sendTime = value.sendTime;
        this.count = value.count;
        this.addTime = value.addTime;
        this.isDelete = value.isDelete;
        this.updateTime = value.updateTime;
    }

    public MobileMessage(
        String  id,
        String  mobile,
        String  code,
        String  sendTime,
        Integer count,
        String  addTime,
        String  isDelete,
        String  updateTime
    ) {
        this.id = id;
        this.mobile = mobile;
        this.code = code;
        this.sendTime = sendTime;
        this.count = count;
        this.addTime = addTime;
        this.isDelete = isDelete;
        this.updateTime = updateTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getAddTime() {
        return this.addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MobileMessage (");

        sb.append(id);
        sb.append(", ").append(mobile);
        sb.append(", ").append(code);
        sb.append(", ").append(sendTime);
        sb.append(", ").append(count);
        sb.append(", ").append(addTime);
        sb.append(", ").append(isDelete);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}