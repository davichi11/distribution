package com.distribution.modules.api.controller


import com.distribution.common.utils.Result
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.annotation.LoginUser
import com.distribution.modules.api.entity.UserEntity
import com.distribution.modules.oss.cloud.OSSFactory
import com.distribution.modules.oss.entity.SysOssEntity
import com.distribution.modules.oss.service.SysOssService
import com.distribution.modules.sys.service.DistrictService
import com.distribution.modules.sys.service.SysConfigService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

/**
 * API测试接口
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:47
 */
@RestController
@RequestMapping("/api")

@Api("通用接口")
@CrossOrigin
class ApiCommonController {

    @Autowired
    private lateinit var districtService: DistrictService

    @Autowired
    private lateinit var sysOssService: SysOssService
    @Autowired
    private lateinit var sysConfigService: SysConfigService
    private val log = LoggerFactory.getLogger(ApiCommonController::class.java)
    /**
     * 获取用户信息
     */
    @AuthIgnore
    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true)
    fun userInfo(@LoginUser user: UserEntity): Result {
        return Result().ok().put("user", user)
    }

    /**
     * 忽略Token验证测试
     */
    @AuthIgnore
    @GetMapping("notToken")
    @ApiOperation(value = "忽略Token验证测试")
    fun notToken(): Result {
        return Result().ok().put("msg", "无需token也能访问。。。")
    }

    @AuthIgnore
    @ApiOperation("根据上级code获取区域信息")
    @GetMapping("/area/{code}")
    fun getAreaInfo(@PathVariable("code") code: String): Result {
        return Result().ok().put("area", districtService.getByParentId(code))
    }

    @AuthIgnore
    @GetMapping("/ios")
    fun ios(): Map<String, String> {
        //苹果上架前为0；上架中为0； 上架审核通过后为1；  建议此值从数据库获取，或者从配置文件中获取，方便审核通过后进行修改；
        val ios = sysConfigService.getValue("ios", "0")
        return mapOf("ios" to ios)
    }


    /**
     * 文件上传接口
     *
     * @param file
     * @return
     */
    @AuthIgnore
    @ApiOperation("文件上传接口")
    @PostMapping("/upload")
    fun upload(@ApiParam("文件") file: MultipartFile?): Result {
        if (file == null) {
            return Result().error(msg = "上传文件为空")
        }
        try {
            //            //获取跟目录
            //            String fileName = Instant.now().toEpochMilli() + ".jpg";
            //            Files.copy(file.getInputStream(), Paths.get(filePath, fileName));
            //            String url = (StringUtils.isNoneBlank(fileServer) ? fileServer : "") + "/" + fileName;
            //上传文件
            val url = OSSFactory.build().upload(file.bytes)

            //保存文件信息
            val ossEntity = SysOssEntity()
            ossEntity.url = url
            ossEntity.createDate = Date()
            try {
                sysOssService.save(ossEntity)
            } catch (e: Exception) {
                log.error("保存文件异常", e)
            }

            return Result().ok().put("url", url)
        } catch (e: IOException) {
            log.error("上传文件异常", e)
            return Result().error(msg = "上传文件异常")
        }

    }
}
