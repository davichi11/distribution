/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductTypeParams implements Serializable {

    private static final long serialVersionUID = -1327960219;

    private Integer id;
    private Integer level;
    private Double  exchangePercent;
    private String  typeId;

    public ProductTypeParams() {}

    public ProductTypeParams(ProductTypeParams value) {
        this.id = value.id;
        this.level = value.level;
        this.exchangePercent = value.exchangePercent;
        this.typeId = value.typeId;
    }

    public ProductTypeParams(
        Integer id,
        Integer level,
        Double  exchangePercent,
        String  typeId
    ) {
        this.id = id;
        this.level = level;
        this.exchangePercent = exchangePercent;
        this.typeId = typeId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getExchangePercent() {
        return this.exchangePercent;
    }

    public void setExchangePercent(Double exchangePercent) {
        this.exchangePercent = exchangePercent;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProductTypeParams (");

        sb.append(id);
        sb.append(", ").append(level);
        sb.append(", ").append(exchangePercent);
        sb.append(", ").append(typeId);

        sb.append(")");
        return sb.toString();
    }
}