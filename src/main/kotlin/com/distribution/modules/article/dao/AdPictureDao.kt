package com.distribution.modules.article.dao

import com.distribution.modules.article.entity.AdPictureEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 * 轮播图
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-29
 */
@Mapper
interface AdPictureDao : BaseDao<AdPictureEntity>
