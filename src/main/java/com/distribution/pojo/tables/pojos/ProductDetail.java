/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 积分兑换产品列表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductDetail implements Serializable {

    private static final long serialVersionUID = 1455354853;

    private String  id;
    private String  prodDetailName;
    private Integer prodDetailBuyBack;
    private String  prodTypeId;
    private Long    prodDetailValue;
    private String  prodDetailCount;
    private String  prodDetailUrl;

    public ProductDetail() {}

    public ProductDetail(ProductDetail value) {
        this.id = value.id;
        this.prodDetailName = value.prodDetailName;
        this.prodDetailBuyBack = value.prodDetailBuyBack;
        this.prodTypeId = value.prodTypeId;
        this.prodDetailValue = value.prodDetailValue;
        this.prodDetailCount = value.prodDetailCount;
        this.prodDetailUrl = value.prodDetailUrl;
    }

    public ProductDetail(
        String  id,
        String  prodDetailName,
        Integer prodDetailBuyBack,
        String  prodTypeId,
        Long    prodDetailValue,
        String  prodDetailCount,
        String  prodDetailUrl
    ) {
        this.id = id;
        this.prodDetailName = prodDetailName;
        this.prodDetailBuyBack = prodDetailBuyBack;
        this.prodTypeId = prodTypeId;
        this.prodDetailValue = prodDetailValue;
        this.prodDetailCount = prodDetailCount;
        this.prodDetailUrl = prodDetailUrl;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdDetailName() {
        return this.prodDetailName;
    }

    public void setProdDetailName(String prodDetailName) {
        this.prodDetailName = prodDetailName;
    }

    public Integer getProdDetailBuyBack() {
        return this.prodDetailBuyBack;
    }

    public void setProdDetailBuyBack(Integer prodDetailBuyBack) {
        this.prodDetailBuyBack = prodDetailBuyBack;
    }

    public String getProdTypeId() {
        return this.prodTypeId;
    }

    public void setProdTypeId(String prodTypeId) {
        this.prodTypeId = prodTypeId;
    }

    public Long getProdDetailValue() {
        return this.prodDetailValue;
    }

    public void setProdDetailValue(Long prodDetailValue) {
        this.prodDetailValue = prodDetailValue;
    }

    public String getProdDetailCount() {
        return this.prodDetailCount;
    }

    public void setProdDetailCount(String prodDetailCount) {
        this.prodDetailCount = prodDetailCount;
    }

    public String getProdDetailUrl() {
        return this.prodDetailUrl;
    }

    public void setProdDetailUrl(String prodDetailUrl) {
        this.prodDetailUrl = prodDetailUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProductDetail (");

        sb.append(id);
        sb.append(", ").append(prodDetailName);
        sb.append(", ").append(prodDetailBuyBack);
        sb.append(", ").append(prodTypeId);
        sb.append(", ").append(prodDetailValue);
        sb.append(", ").append(prodDetailCount);
        sb.append(", ").append(prodDetailUrl);

        sb.append(")");
        return sb.toString();
    }
}
