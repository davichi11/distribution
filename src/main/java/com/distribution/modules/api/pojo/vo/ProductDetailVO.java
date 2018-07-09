package com.distribution.modules.api.pojo.vo;

import com.distribution.pojo.tables.pojos.ProductDetailParams;
import lombok.Data;

import java.util.List;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/7/8-00:15
 */
@Data
public class ProductDetailVO {
    private String id;
    private String prodDetailName;
    private Integer prodDetailBuyBack;
    private String prodTypeId;
    private Long prodDetailValue;
    private Integer prodDetailCount;
    List<ProductDetailParams> params;
}
