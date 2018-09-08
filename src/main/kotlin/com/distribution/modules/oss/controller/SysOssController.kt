package com.distribution.modules.oss.controller

import com.distribution.common.exception.RRException
import com.distribution.common.utils.ConfigConstant
import com.distribution.common.utils.Constant
import com.distribution.common.utils.Result
import com.distribution.common.validator.ValidatorUtils
import com.distribution.common.validator.group.AliyunGroup
import com.distribution.common.validator.group.QcloudGroup
import com.distribution.common.validator.group.QiniuGroup
import com.distribution.modules.oss.cloud.CloudStorageConfig
import com.distribution.modules.oss.cloud.OSSFactory
import com.distribution.modules.oss.entity.SysOssEntity
import com.distribution.modules.oss.service.SysOssService
import com.distribution.modules.sys.service.SysConfigService
import com.github.pagehelper.PageHelper
import com.google.gson.Gson
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*


/**
 * 文件上传
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 12:13:26
 */

@RestController
@RequestMapping("sys/oss")
class SysOssController {
    @Autowired
    private lateinit var sysOssService: SysOssService
    @Autowired
    private lateinit var sysConfigService: SysConfigService
    private val log = LoggerFactory.getLogger(SysOssController::class.java)
    private val KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<SysOssEntity> { sysOssService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    fun config(): Result {
        val config = sysConfigService.getConfigObject(KEY, CloudStorageConfig::class.java)

        return Result().ok().put("config", config)
    }


    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    fun saveConfig(@RequestBody config: CloudStorageConfig): Result {
        //校验类型
        ValidatorUtils.validateEntity(config)

        when (config.type) {
            Constant.CloudService.QINIU.value -> //校验七牛数据
                ValidatorUtils.validateEntity(config, QiniuGroup::class.java)
            Constant.CloudService.ALIYUN.value -> //校验阿里云数据
                ValidatorUtils.validateEntity(config, AliyunGroup::class.java)
            Constant.CloudService.QCLOUD.value -> //校验腾讯云数据
                ValidatorUtils.validateEntity(config, QcloudGroup::class.java)
        }

        return try {
            sysConfigService.updateValueByKey(KEY, Gson().toJson(config))
            Result().ok()
        } catch (e: Exception) {
            log.error("保存云存储配置信息异常", e)
            return Result().error(msg = "保存云存储配置信息")
        }

    }


    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    @Throws(Exception::class)
    fun upload(@RequestParam("file") file: MultipartFile): Result {
        if (file.isEmpty) {
            throw RRException("上传文件不能为空")
        }

        //上传文件
        val url = OSSFactory.build().upload(file.bytes)

        //保存文件信息
        val ossEntity = SysOssEntity()
        ossEntity.url = url
        ossEntity.createDate = Date()
        sysOssService.save(ossEntity)

        return Result().ok().put("url", url)
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    fun delete(@RequestBody ids: Array<Long>): Result {
        try {
            sysOssService.deleteBatch(ids)
        } catch (e: Exception) {
            log.error("删除文件异常", e)
            return Result().error(msg = "删除文件异常")
        }

        return Result().ok()
    }

}
