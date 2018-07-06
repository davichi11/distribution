/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class IntegralOrder implements Serializable {

    private static final long serialVersionUID = 336785870;

    private String  id;
    private Long    mobile;
    private String  detailId;
    private String  img;
    private String  addTime;
    private Boolean status;

    public IntegralOrder() {}

    public IntegralOrder(IntegralOrder value) {
        this.id = value.id;
        this.mobile = value.mobile;
        this.detailId = value.detailId;
        this.img = value.img;
        this.addTime = value.addTime;
        this.status = value.status;
    }

    public IntegralOrder(
        String  id,
        Long    mobile,
        String  detailId,
        String  img,
        String  addTime,
        Boolean status
    ) {
        this.id = id;
        this.mobile = mobile;
        this.detailId = detailId;
        this.img = img;
        this.addTime = addTime;
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMobile() {
        return this.mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getDetailId() {
        return this.detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddTime() {
        return this.addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IntegralOrder (");

        sb.append(id);
        sb.append(", ").append(mobile);
        sb.append(", ").append(detailId);
        sb.append(", ").append(img);
        sb.append(", ").append(addTime);
        sb.append(", ").append(status);

        sb.append(")");
        return sb.toString();
    }
}
