package com.distribution.common.excel

import org.apache.commons.lang3.StringUtils
import org.apache.poi.hssf.usermodel.HSSFDateUtil
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.LoggerFactory
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.common.excel
 
 * @create 2018/7/31-22:51
 */
object ExcelUtils {
    private val log = LoggerFactory.getLogger(ExcelUtils::class.java)

    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    @Throws(IOException::class)
    fun readExcel(file: InputStream, ignoreRows: Int): List<Array<String?>> {
        val result = LinkedList<Array<String?>>()
        var rowSize = 0
        BufferedInputStream(file).use { inputStream ->
            // 打开XSSFWorkbook
            val wb = XSSFWorkbook(inputStream)
            var cell: XSSFCell?

            for (sheetIndex in 0 until wb.numberOfSheets) {
                val st = wb.getSheetAt(sheetIndex)
                // 第一行为标题，不取
                for (rowIndex in ignoreRows..st.lastRowNum) {
                    val row = st.getRow(rowIndex) ?: continue
                    val tempRowSize = row.lastCellNum + 1
                    if (tempRowSize > rowSize) {
                        rowSize = tempRowSize
                    }
                    val values = arrayOfNulls<String>(rowSize)
                    Arrays.fill(values, "")
                    var hasValue = false
                    for (columnIndex in 0..row.lastCellNum) {
                        var value = ""
                        cell = row.getCell(columnIndex)
                        if (cell != null) {
                            when (cell.cellTypeEnum) {
                                CellType.STRING -> value = cell.stringCellValue
                                CellType.NUMERIC -> value = if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                                    sdf.format(HSSFDateUtil.getJavaDate(cell.numericCellValue))
                                } else {
                                    DecimalFormat("0").format(cell.numericCellValue)
                                }
                                CellType.FORMULA -> value = cell.cellFormula
                                CellType.BLANK -> {
                                }
                                CellType.ERROR -> value = ""
                                CellType.BOOLEAN -> value = (if (cell.booleanCellValue) "Y" else "N")
                                else -> value = ""
                            }
                        }
                        if (columnIndex == 0 && value.trim { it <= ' ' } == "") {
                            break
                        }
                        values[columnIndex] = StringUtils.trim(value)
                        hasValue = true
                    }

                    if (hasValue) {
                        result.add(values)
                    }
                }
            }
        }

        return result
    }


}
