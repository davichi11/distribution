/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * ??
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysRole implements Serializable {

    private static final long serialVersionUID = 415065751;

    private Long      roleId;
    private String    roleName;
    private String    remark;
    private Long      createUserId;
    private Timestamp createTime;

    public SysRole() {}

    public SysRole(SysRole value) {
        this.roleId = value.roleId;
        this.roleName = value.roleName;
        this.remark = value.remark;
        this.createUserId = value.createUserId;
        this.createTime = value.createTime;
    }

    public SysRole(
        Long      roleId,
        String    roleName,
        String    remark,
        Long      createUserId,
        Timestamp createTime
    ) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.remark = remark;
        this.createUserId = createUserId;
        this.createTime = createTime;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SysRole (");

        sb.append(roleId);
        sb.append(", ").append(roleName);
        sb.append(", ").append(remark);
        sb.append(", ").append(createUserId);
        sb.append(", ").append(createTime);

        sb.append(")");
        return sb.toString();
    }
}
