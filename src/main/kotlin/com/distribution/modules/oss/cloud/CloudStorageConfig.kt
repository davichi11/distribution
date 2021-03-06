package com.distribution.modules.oss.cloud

import com.distribution.common.validator.group.AliyunGroup
import com.distribution.common.validator.group.QcloudGroup
import com.distribution.common.validator.group.QiniuGroup
import org.hibernate.validator.constraints.URL
import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * 云存储配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 16:12
 */

class CloudStorageConfig(
        private val serialVersionUID: Long = 1L,
        /**
         * //类型 1：七牛  2：阿里云  3：腾讯云
         */
        var type: Int? = null,

        /**
         * 七牛绑定的域名
         */
        @NotBlank(message = "七牛绑定的域名不能为空", groups = [(QiniuGroup::class)])
        @URL(message = "七牛绑定的域名格式不正确", groups = [(QiniuGroup::class)])
        var qiniuDomain: String? = null,


        /**
         * 七牛路径前缀
         */
        var qiniuPrefix: String? = null,

        /**
         * 七牛ACCESS_KEY
         */
        @NotBlank(message = "七牛AccessKey不能为空", groups = [(QiniuGroup::class)])
        var qiniuAccessKey: String? = null,

        /**
         * 七牛SECRET_KEY
         */
        @NotBlank(message = "七牛SecretKey不能为空", groups = [(QiniuGroup::class)])
        var qiniuSecretKey: String? = null,

        /**
         * 七牛存储空间名
         */
        @NotBlank(message = "七牛空间名不能为空", groups = [(QiniuGroup::class)])
        var qiniuBucketName: String? = null,


        /**
         * 阿里云绑定的域名
         */
        @NotBlank(message = "阿里云绑定的域名不能为空", groups = [(AliyunGroup::class)])
        @URL(message = "阿里云绑定的域名格式不正确", groups = [(AliyunGroup::class)])
        var aliyunDomain: String? = null,

        /**
         * 阿里云路径前缀
         */
        var aliyunPrefix: String? = null,

        /**
         * 阿里云EndPoint
         */
        @NotBlank(message = "阿里云EndPoint不能为空", groups = [(AliyunGroup::class)])
        var aliyunEndPoint: String? = null,

        /**
         * 阿里云AccessKeyId
         */
        @NotBlank(message = "阿里云AccessKeyId不能为空", groups = [(AliyunGroup::class)])
        var aliyunAccessKeyId: String? = null,

        /**
         * 阿里云AccessKeySecret
         */
        @NotBlank(message = "阿里云AccessKeySecret不能为空", groups = [(AliyunGroup::class)])
        var aliyunAccessKeySecret: String? = null,

        /**
         * 阿里云BucketName
         */
        @NotBlank(message = "阿里云BucketName不能为空", groups = [(AliyunGroup::class)])
        var aliyunBucketName: String? = null,


        /**
         * 腾讯云绑定的域名
         */
        @NotBlank(message = "腾讯云绑定的域名不能为空", groups = [(QcloudGroup::class)])
        @URL(message = "腾讯云绑定的域名格式不正确", groups = [(QcloudGroup::class)])
        var qcloudDomain: String? = null,

        /**
         * 腾讯云路径前缀
         */
        var qcloudPrefix: String? = null,

        /**
         * 腾讯云AppId
         */
        @NotNull(message = "腾讯云AppId不能为空", groups = [(QcloudGroup::class)])
        var qcloudAppId: Int? = null,

        /**
         * 腾讯云SecretId
         */
        @NotBlank(message = "腾讯云SecretId不能为空", groups = [(QcloudGroup::class)])
        var qcloudSecretId: String? = null,

        /**
         * 腾讯云SecretKey
         */
        @NotBlank(message = "腾讯云SecretKey不能为空", groups = [(QcloudGroup::class)])
        var qcloudSecretKey: String? = null,

        /**
         * 腾讯云BucketName
         */
        @NotBlank(message = "腾讯云BucketName不能为空", groups = [(QcloudGroup::class)])
        var qcloudBucketName: String? = null,

        /**
         * 腾讯云COS所属地区
         */
        @NotBlank(message = "所属地区不能为空", groups = [(QcloudGroup::class)])
        var qcloudRegion: String? = null
) : Serializable