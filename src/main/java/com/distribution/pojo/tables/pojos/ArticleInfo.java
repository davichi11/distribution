/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 文章表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ArticleInfo implements Serializable {

    private static final long serialVersionUID = -1019097656;

    private Integer id;
    private String  title;
    private String  detail;
    private String  type;
    private Integer orderNum;
    private String  addTime;
    private String  isDelete;

    public ArticleInfo() {}

    public ArticleInfo(ArticleInfo value) {
        this.id = value.id;
        this.title = value.title;
        this.detail = value.detail;
        this.type = value.type;
        this.orderNum = value.orderNum;
        this.addTime = value.addTime;
        this.isDelete = value.isDelete;
    }

    public ArticleInfo(
        Integer id,
        String  title,
        String  detail,
        String  type,
        Integer orderNum,
        String  addTime,
        String  isDelete
    ) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.type = type;
        this.orderNum = orderNum;
        this.addTime = addTime;
        this.isDelete = isDelete;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ArticleInfo (");

        sb.append(id);
        sb.append(", ").append(title);
        sb.append(", ").append(detail);
        sb.append(", ").append(type);
        sb.append(", ").append(orderNum);
        sb.append(", ").append(addTime);
        sb.append(", ").append(isDelete);

        sb.append(")");
        return sb.toString();
    }
}
