package com.distribution.modules.api.controller;


import com.distribution.common.utils.ConfigConstant;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.annotation.LoginUser;
import com.distribution.modules.api.entity.UserEntity;
import com.distribution.modules.oss.cloud.OSSFactory;
import com.distribution.modules.oss.entity.SysOssEntity;
import com.distribution.modules.oss.service.SysOssService;
import com.distribution.modules.sys.service.DistrictService;
import com.distribution.modules.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * API测试接口
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:47
 */
@RestController
@RequestMapping("/api")
@Slf4j
@Api("通用接口")
@CrossOrigin
public class ApiCommonController {

    @Autowired
    private DistrictService districtService;

    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;


    @Value("${risk.file-server}")
    private String fileServer;
    @Value("${risk.file-path}")
    private String filePath;

    /**
     * 获取用户信息
     */
    @AuthIgnore
    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true)
    public Result userInfo(@LoginUser UserEntity user) {
        return Result.ok().put("user", user);
    }

    /**
     * 忽略Token验证测试
     */
    @AuthIgnore
    @GetMapping("notToken")
    @ApiOperation(value = "忽略Token验证测试")
    public Result notToken() {
        return Result.ok().put("msg", "无需token也能访问。。。");
    }

    @AuthIgnore
    @ApiOperation("根据上级code获取区域信息")
    @GetMapping("/area/{code}")
    public Result getAreaInfo(@PathVariable("code") String code) {
        return Result.ok().put("area", districtService.getByParentId(code));
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
    public Result upload(@ApiParam("文件") MultipartFile file) {
        if (file == null) {
            return Result.error("上传文件为空");
        }
        try {
//            //获取跟目录
//            String fileName = Instant.now().toEpochMilli() + ".jpg";
//            Files.copy(file.getInputStream(), Paths.get(filePath, fileName));
//            String url = (StringUtils.isNoneBlank(fileServer) ? fileServer : "") + "/" + fileName;
            //上传文件
            String url = OSSFactory.build().upload(file.getBytes());

            //保存文件信息
            SysOssEntity ossEntity = new SysOssEntity();
            ossEntity.setUrl(url);
            ossEntity.setCreateDate(new Date());
            try {
                sysOssService.save(ossEntity);
            } catch (Exception e) {
                log.error("保存文件异常", e);
            }
            return Result.ok().put("url", url);
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return Result.error("上传文件异常");
        }
    }
}
