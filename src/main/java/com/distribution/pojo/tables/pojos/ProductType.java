/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 积分兑换产品类型表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1267455823;

    private String id;
    private String prodName;
    private String prodRate;
    private String prodRemark;
    private String prodImg;
    private String prodType;
    private Double exchangePercent;
    private String isDelete;

    public ProductType() {}

    public ProductType(ProductType value) {
        this.id = value.id;
        this.prodName = value.prodName;
        this.prodRate = value.prodRate;
        this.prodRemark = value.prodRemark;
        this.prodImg = value.prodImg;
        this.prodType = value.prodType;
        this.exchangePercent = value.exchangePercent;
        this.isDelete = value.isDelete;
    }

    public ProductType(
        String id,
        String prodName,
        String prodRate,
        String prodRemark,
        String prodImg,
        String prodType,
        Double exchangePercent,
        String isDelete
    ) {
        this.id = id;
        this.prodName = prodName;
        this.prodRate = prodRate;
        this.prodRemark = prodRemark;
        this.prodImg = prodImg;
        this.prodType = prodType;
        this.exchangePercent = exchangePercent;
        this.isDelete = isDelete;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdName() {
        return this.prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdRate() {
        return this.prodRate;
    }

    public void setProdRate(String prodRate) {
        this.prodRate = prodRate;
    }

    public String getProdRemark() {
        return this.prodRemark;
    }

    public void setProdRemark(String prodRemark) {
        this.prodRemark = prodRemark;
    }

    public String getProdImg() {
        return this.prodImg;
    }

    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }

    public String getProdType() {
        return this.prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public Double getExchangePercent() {
        return this.exchangePercent;
    }

    public void setExchangePercent(Double exchangePercent) {
        this.exchangePercent = exchangePercent;
    }

    public String getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProductType (");

        sb.append(id);
        sb.append(", ").append(prodName);
        sb.append(", ").append(prodRate);
        sb.append(", ").append(prodRemark);
        sb.append(", ").append(prodImg);
        sb.append(", ").append(prodType);
        sb.append(", ").append(exchangePercent);
        sb.append(", ").append(isDelete);

        sb.append(")");
        return sb.toString();
    }
}
