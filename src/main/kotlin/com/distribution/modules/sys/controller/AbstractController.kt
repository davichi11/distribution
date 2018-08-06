package com.distribution.modules.sys.controller

import com.distribution.modules.sys.entity.SysUserEntity
import org.apache.shiro.SecurityUtils
import org.slf4j.LoggerFactory

/**
 * Controller公共组件
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
abstract class AbstractController {
    protected var logger = LoggerFactory.getLogger(javaClass)

    protected val user: SysUserEntity
        get() = SecurityUtils.getSubject().principal as SysUserEntity

    protected val userId: Long?
        get() = user.userId
}
