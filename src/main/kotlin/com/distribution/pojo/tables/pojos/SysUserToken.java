/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * ????Token
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysUserToken implements Serializable {

    private static final long serialVersionUID = -392097290;

    private Long      userId;
    private String    token;
    private Timestamp expireTime;
    private Timestamp updateTime;

    public SysUserToken() {}

    public SysUserToken(SysUserToken value) {
        this.userId = value.userId;
        this.token = value.token;
        this.expireTime = value.expireTime;
        this.updateTime = value.updateTime;
    }

    public SysUserToken(
        Long      userId,
        String    token,
        Timestamp expireTime,
        Timestamp updateTime
    ) {
        this.userId = userId;
        this.token = token;
        this.expireTime = expireTime;
        this.updateTime = updateTime;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SysUserToken (");

        sb.append(userId);
        sb.append(", ").append(token);
        sb.append(", ").append(expireTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}