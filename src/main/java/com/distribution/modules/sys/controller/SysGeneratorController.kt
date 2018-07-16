package com.distribution.modules.sys.controller

import com.alibaba.fastjson.JSON
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.sys.service.SysGeneratorService
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import lombok.extern.slf4j.Slf4j
import org.apache.commons.collections.MapUtils
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package com.distribution.modules.sys.controller
 * @Description TODO(描述)
 * @create 2017/8/13-21:24
 */

@RestController
@RequestMapping("/sys/generator")
class SysGeneratorController {
    @Autowired
    private lateinit var sysGeneratorService: SysGeneratorService
    private val log = LoggerFactory.getLogger(SysGeneratorController::class.java)

    /**
     * 列表数据
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    fun list(@RequestParam params: Map<String, Any>): Result? {

        val pageInfo: PageInfo<Map<String, Any>>
        try {
            pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0)!!,
                    MapUtils.getInteger(params, "limit", 0)!!).doSelectPageInfo { sysGeneratorService.queryList(params) }
        } catch (e: Exception) {
            log.error("查询异常", e)
            return Result().error(msg = "查询异常")
        }

        return Result().ok().put("page", pageInfo)

    }

    /**
     * 生成代码
     */
    @GetMapping("/code")
    @Throws(IOException::class)
    fun code(request: HttpServletRequest): Result {
        val tables = request.getParameter("tables")
        val tableNames = JSON.parseArray(tables).toArray()

        val data: ByteArray
        try {
            data = sysGeneratorService.generatorCode(tableNames.map { it.toString() })
        } catch (e: Exception) {
            log.error("生成异常", e)
            return Result().error(msg = "生成失败")
        }

        val fileName = "gender" + DateUtils.formatDateTime(LocalDateTime.now(), "yyyyMMddHHMMss") + ".zip"
        //                File file = new File(GenUtils.getClasspath() + "code/" + fileName);
        val file = File("/Users/huchunliang/Downloads/$fileName")
        try {
            FileUtils.writeByteArrayToFile(file, data)
        } catch (e: IOException) {
            log.error("文件写入异常", e)
            return Result().error(msg = "生成失败,文件写入异常")
        }

        return Result().ok()
        //        response.reset();
        //        response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");
        //        response.addHeader("Content-Length", "" + data.length);
        //        response.setContentType("application/octet-stream; charset=UTF-8");
        //
        //        IOUtils.write(data, response.getOutputStream());
    }
}
