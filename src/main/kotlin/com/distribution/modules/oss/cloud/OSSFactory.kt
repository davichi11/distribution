package com.distribution.modules.oss.cloud

import com.distribution.common.utils.ApplicationContextHolder
import com.distribution.common.utils.ConfigConstant
import com.distribution.common.utils.Constant
import com.distribution.modules.sys.service.SysConfigService

/**
 * 文件上传Factory
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 10:18
 */
object OSSFactory {
    private var sysConfigService: SysConfigService? = null
    private val ossMap: MutableMap<Int, (CloudStorageConfig) -> AbstractCloudStorageService> = mutableMapOf()

    init {
        sysConfigService = ApplicationContextHolder.getBean("sysConfigService") as SysConfigService
        ossMap[Constant.CloudService.QINIU.value] = { config -> QiniuAbstractCloudStorageService(config) }
        ossMap[Constant.CloudService.ALIYUN.value] = { config -> AliyunAbstractCloudStorageService(config) }
        ossMap[Constant.CloudService.QCLOUD.value] = { config -> QcloudAbstractCloudStorageService(config) }
    }

    fun build(): AbstractCloudStorageService {
        //获取云存储配置信息
        val config = sysConfigService!!.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig::class.java)
        return ossMap[config.type]?.let { it(config) } ?: AliyunAbstractCloudStorageService(config)
    }

}
