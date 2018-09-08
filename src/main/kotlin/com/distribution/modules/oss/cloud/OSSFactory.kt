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

    init {
        OSSFactory.sysConfigService = ApplicationContextHolder.getBean("sysConfigService") as SysConfigService
    }

    fun build(): AbstractCloudStorageService {
        //获取云存储配置信息
        val config = sysConfigService!!.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig::class.java)

        if (config.type == Constant.CloudService.QINIU.value) {
            return QiniuAbstractCloudStorageService(config)
        } else if (config.type == Constant.CloudService.ALIYUN.value) {
            return AliyunAbstractCloudStorageService(config)
        } else if (config.type == Constant.CloudService.QCLOUD.value) {
            return QcloudAbstractCloudStorageService(config)
        }

        return AliyunAbstractCloudStorageService(config)
    }

}
