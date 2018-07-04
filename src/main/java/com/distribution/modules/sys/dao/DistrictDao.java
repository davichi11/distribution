package com.distribution.modules.sys.dao;

import com.distribution.modules.sys.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.dao
 * @Description TODO(描述)
 * @create 2018/6/20-21:58
 */
@Mapper
public interface DistrictDao {

    List<District> getByParentId(String parentId);

}