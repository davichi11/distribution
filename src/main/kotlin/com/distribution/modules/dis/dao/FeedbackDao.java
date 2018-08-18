package com.distribution.modules.dis.dao;

import com.distribution.modules.dis.entity.FeedbackEntity;
import com.distribution.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 意见反馈表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-08-18
 */
@Mapper
public interface FeedbackDao extends BaseDao<FeedbackEntity> {

}
