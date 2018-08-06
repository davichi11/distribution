package com.distribution.common.excel

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.stream.IntStream
import javax.servlet.http.HttpServletResponse

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.common.excel
 * @create 2018/6/24-18:12
 */
interface ExportExcel<T> {
    /**
     * 将字节数组写出到servlet输出流
     *
     * @param response http回应对象，为excel回应的目的地
     * @param list     要导出到 excel的数据集合
     * @param titles   excel的标题 通常取第一行作为excel的标题
     * @param name
     * @throws IOException
     */
    @Throws(IOException::class)
    fun exportExcel(response: HttpServletResponse, list: List<T>, titles: Array<String>, name: String) {
        val bytes = selectExcel(list, titles)
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        response.setHeader("Content-Disposition", "attachment;filename=" + String(name.toByteArray(), charset("UTF-8")) + ".xlsx")
        response.setContentLength(bytes.size)
        response.outputStream.write(bytes)
        response.outputStream.flush()
        response.outputStream.close()
    }

    /**
     * 选择要导出的文件 导出的excel 属于office 2007格式的文件
     *
     * @param list   excel文件内容
     * @param titles excel 文件的标题
     * @return 已经生成excel文件的字节数组
     * @throws IOException
     */
    @Throws(IOException::class)
    fun selectExcel(list: List<T>, titles: Array<String>): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet()
        generateExcelTitle(titles, sheet)
        eachListAndCreateRow(list, sheet)
        val out = ByteArrayOutputStream()
        workbook.write(out)
        return out.toByteArray()
    }

    /**
     * 遍历集合，并创建单元格行
     *
     * @param list  数据集合
     * @param sheet 工作簿
     * @throws Exception
     */
    fun eachListAndCreateRow(list: List<T>, sheet: Sheet) {
        var i = 0
        var j = 1
        while (i < list.size) {
            val t = list[i]
            val row = sheet.createRow(j)
            generateExcelForAs(t, row)
            i++
            j++
        }
    }

    /**
     * 生成excel文件的标题
     *
     * @param sheet
     * @param titles
     */
    fun generateExcelTitle(titles: Array<String>, sheet: Sheet) {
        val row = sheet.createRow(0)
        IntStream.range(0, titles.size).forEach { i -> row.createCell(i, CellType.STRING).setCellValue(titles[i]) }
    }

    /**
     * 创建excel内容文件
     *
     * @param t   组装excel 文件的内容
     * @param row 当前excel 工作行
     */
    fun generateExcelForAs(t: T, row: Row)

    //    /**
    //     * 当发生错误时如此回应信息
    //     * */
    //    default void errorResponse(HttpServletResponse response){
    //        byte[] message = JSONObject.toJSONString(HttpResultFactory.builderFailtureResult("导出excel文件错误,请重试!")).getBytes();
    //        response.setContentType("text/json;charset=UTF-8");
    //        response.setContentLength(message.length);
    //        try {
    //            response.getOutputStream().write(message);
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //    }
}
