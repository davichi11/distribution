package com.distribution.modules.api.pojo.vo;

import com.distribution.pojo.tables.pojos.ProductTypeParams;
import lombok.Data;

import java.util.List;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/7/8-15:17
 */
@Data
public class ProductTypeVO {
    private String prodName;
    private String prodRate;
    private String prodRemark;
    private String prodImg;
    private List<ProductTypeParams> params;
}
