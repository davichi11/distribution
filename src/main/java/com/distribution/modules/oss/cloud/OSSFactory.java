package com.distribution.modules.oss.cloud;

import com.distribution.common.utils.ApplicationContextHolder;
import com.distribution.common.utils.ConfigConstant;
import com.distribution.common.utils.Constant;
import com.distribution.modules.sys.service.SysConfigService;

/**
 * 文件上传Factory
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 10:18
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) ApplicationContextHolder.getBean("sysConfigService");
    }

    public static AbstractCloudStorageService build() {
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return new QiniuAbstractCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return new AliyunAbstractCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            return new QcloudAbstractCloudStorageService(config);
        }

        return new AliyunAbstractCloudStorageService(config);
    }

}
