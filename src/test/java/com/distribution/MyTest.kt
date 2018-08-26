package com.distribution

import com.alibaba.fastjson.JSON
import com.distribution.common.excel.ExcelUtils
import com.distribution.common.utils.OkHttpUtil
import com.distribution.modules.card.entity.CardApiResponse
import com.google.common.collect.Lists
import okhttp3.Request
import org.apache.commons.lang3.StringUtils
import org.junit.Test
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution
 * @Description TODO(描述)
 * @create 2018/5/9-22:23
 */
class MyTest {
    @Test
    fun test1() {
        val f = File("/Volumes/APPLE SD Card Reader Media/工作文档/钱到手app/签到表7月31日.xlsx")
        ExcelUtils.readExcel(f.inputStream(), 0).forEach { it.filter { StringUtils.isNoneBlank(it) }.forEach { println(it) } }
    }


    private suspend fun aa() {
        print("123123")
    }

    @Test
    fun test6() {
        val list = LinkedList<Int>()
        for (i in 1..5) {
            list.add(i)
        }
        val l = LinkedList<Int>()
//        for (i in list) {
//            val pop = list.pop()
//            l.addFirst(pop)
//            list.addLast(pop)
//        }

        while (list.size > 0) {
            val pop = list.pop()
            l.addFirst(pop)
            list.addLast(pop)
        }

        print(list)


    }

    @Test
    fun test5() {
        val between = Duration.between(LocalDateTime.now().with(LocalTime.MIN), LocalDateTime.now())
        println(between.toMinutes())

        val partition = Lists.partition(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 2)

        for (i in 0..38) {
            partition.forEach {
                print(i)
                println(it)
            }
        }

    }

    @Test
    @Throws(UnsupportedEncodingException::class)
    fun test2() {
        //当前时间
        val start = LocalDateTime.now()
        //第二天开始时间
        val end = LocalDateTime.now().plusDays(1).with(LocalTime.MIN)
        //计算时间差
        val between = Duration.between(start, end)
        println(between.toNanos())
    }

    @Test
    @Throws(IOException::class)
    fun test3() {
        //先获取
        val url = OkHttpUtil.attachHttpGetParam("http://www.qichangkeji.vip/qckjgzhManager/DownSingleLoan/selectById.do", "id", "30")
        val request = Request.Builder().url(url).get().build()
        val response = OkHttpUtil.execute(request)
        if (response.isSuccessful) {
            val cardApiResponse = JSON.parseObject(response.body()!!.string(), CardApiResponse::class.java)
            println(cardApiResponse)
        }
    }

    @Test
    fun testMap() {
        print(hashMapOf("a" to 1, "b" to 2))
    }

    @Test
    @Throws(IOException::class)
    fun test4() {
        val params = HashMap<String, String>()
        params["name"] = "刘辉"
        params["phone"] = "18635136850"
        params["type"] = "1"
        params["goodsId"] = "18"
        params["idCard"] = "14052219860503301X"
        params["fatherId"] = "5710"
        params["otherUserId"] = "10904"
        val url = OkHttpUtil.attachHttpGetParams("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do", params)
        //        RequestBody formBody = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), JSON.toJSONString(params));
        //        Request request = new Request.Builder().url("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do")
        //                .post(formBody).build();
        val request = Request.Builder().url(url).get().build()
        OkHttpUtil.execute(request).use { response -> println(response.isSuccessful) }
    }
}
