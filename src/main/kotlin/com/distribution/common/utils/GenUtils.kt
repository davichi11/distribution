package com.distribution.common.utils

import com.distribution.common.exception.RRException
import com.distribution.modules.sys.entity.ColumnEntity
import com.distribution.modules.sys.entity.TableEntity
import org.apache.commons.configuration.Configuration
import org.apache.commons.configuration.ConfigurationException
import org.apache.commons.configuration.PropertiesConfiguration
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.WordUtils
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity

import java.io.File
import java.io.IOException
import java.io.StringWriter
import java.time.LocalDate
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * 代码生成器   工具类
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
object GenUtils {

    private val templates: List<String>
        get() {
            return listOf("template/Entity.java.vm", "template/Dao.java.vm", "template/Dao.xml.vm",
                    "template/Service.java.vm", "template/ServiceImpl.java.vm", "template/Controller.java.vm",
                    "template/list.html.vm", "template/list.js.vm", "template/menu.sql.vm")
        }

    /**
     * 获取配置信息
     */
    val config: Configuration
        get() {
            try {
                return PropertiesConfiguration("generator.properties")
            } catch (e: ConfigurationException) {
                throw RRException("获取配置文件失败，", e)
            }

        }

//    /**
//     * 获取classpath1
//     *
//     * @return
//     */
//    val classpath: String
//        get() {
//            var path = Thread.currentThread().contextClassLoader.getResource("").toString().replace("file:/".toRegex(), "").replace("%20".toRegex(), " ").trim { it <= ' ' }
//            if (path.indexOf(":") != 1) {
//                path = File.separator + path
//            }
//            val s = File.separator
//            return Paths.get(path).parent.parent + s + "src" + s + "main" + s + "resources" + s
//        }

    /**
     * 生成代码
     */
    fun generatorCode(table: Map<String, String>,
                      columns: List<Map<String, String>>, zip: ZipOutputStream) {
        //配置信息
        val config = config

        //表信息
        val tableEntity = TableEntity()
        tableEntity.tableName = table.getValue("tableName")
        tableEntity.comments = table.getValue("tableComment")
        //表名转换成Java类名
        val className = tableToJava(tableEntity.tableName, config.getString("tablePrefix"))
        tableEntity.upClassName = className
        tableEntity.lowClassName = StringUtils.uncapitalize(className)

        //列信息
        val columsList = columns.map { column ->
            val columnEntity = ColumnEntity()
            columnEntity.columnName = column.getValue("columnName")
            columnEntity.dataType = column.getValue("dataType")
            columnEntity.comments = column.getValue("columnComment")
            columnEntity.extra = column.getValue("extra")
            //列名转换成Java属性名
            val attrName = columnToJava(columnEntity.columnName)
            columnEntity.upAttrName = attrName
            columnEntity.lowAttrName = StringUtils.uncapitalize(attrName)
            //列的数据类型，转换成Java类型
            val attrType = config.getString(columnEntity.dataType, "unknowType")
            columnEntity.attrType = attrType
            //是否主键
            if ("PRI".equals(column["columnKey"], ignoreCase = true) && tableEntity.pk == null) {
                tableEntity.pk = columnEntity
            }
            columnEntity
        }.toList()
        tableEntity.columns = columsList

        //没主键，则第一个字段为主键
        if (tableEntity.pk == null) {
            tableEntity.pk = tableEntity.columns[0]
        }

        //设置velocity资源加载器
        val prop = Properties()
        prop["file.resource.loader.class"] = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"
        Velocity.init(prop)

        //封装模板数据
        val map = HashMap<String, Any>(16)
        map["tableName"] = tableEntity.tableName
        map["comments"] = tableEntity.comments
        map["pk"] = tableEntity.pk!!
        map["className"] = tableEntity.upClassName
        map["lowClassName"] = tableEntity.lowClassName
        map["pathName"] = tableEntity.lowClassName.toLowerCase()
        map["columns"] = tableEntity.columns
        map["package"] = config.getString("package")
        map["author"] = config.getString("author")
        map["email"] = config.getString("email")
        map["datetime"] = DateUtils.format(LocalDate.now())
        val context = VelocityContext(map)

        //获取模板列表
        val templates = templates
        templates.forEach { template ->
            //渲染模板
            val sw = StringWriter()
            val tpl = Velocity.getTemplate(template, "UTF-8")
            tpl.merge(context, sw)
            try {
                //添加到zip
                zip.putNextEntry(ZipEntry(getFileName(template, tableEntity.upClassName, config.getString("package"))))
                IOUtils.write(sw.toString(), zip, "UTF-8")
                IOUtils.closeQuietly(sw)
                zip.closeEntry()
            } catch (e: IOException) {
                throw RRException("渲染模板失败，表名：" + tableEntity.tableName, e)
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    private fun columnToJava(columnName: String): String {
        return WordUtils.capitalizeFully(columnName, charArrayOf('_')).replace("_", "")
    }

    /**
     * 表名转换成Java类名
     */
    private fun tableToJava(tableName: String?, tablePrefix: String): String {
        var tableName1 = tableName
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName1 = tableName1!!.replace(tablePrefix, "")
        }
        return columnToJava(tableName1!!)
    }

    /**
     * 获取文件名
     */
    private fun getFileName(template: String, className: String?, packageName: String): String {
        var packagePath = "main" + File.separator + "java" + File.separator
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java"
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java"
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java"
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java"
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java"
        }

        if (template.contains("Dao.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + "generator" + File.separator + className + "Dao.xml"
        }

        if (template.contains("list.html.vm")) {
            return ("main" + File.separator + "resources" + File.separator + "views" + File.separator
                    + "modules" + File.separator + "generator" + File.separator + className!!.toLowerCase() + ".html")
        }

        if (template.contains("list.js.vm")) {
            return ("main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "modules" + File.separator + "generator" + File.separator + className!!.toLowerCase() + ".js")
        }

        return if (template.contains("menu.sql.vm")) {
            className!!.toLowerCase() + "_menu.sql"
        } else packagePath

    }


}
