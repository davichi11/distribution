package com.distribution.modules.oss.dao

import com.distribution.modules.oss.entity.SysOssEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 * 文件上传
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 12:13:26
 */
@Mapper
interface SysOssDao : BaseDao<SysOssEntity>
