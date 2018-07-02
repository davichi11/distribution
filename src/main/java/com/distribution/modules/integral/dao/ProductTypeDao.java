package com.distribution.modules.integral.dao;


import com.distribution.modules.integral.entity.ProductTypeEntity;
import com.distribution.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分兑换产品类型表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@Mapper
public interface ProductTypeDao extends BaseDao<ProductTypeEntity> {

}
