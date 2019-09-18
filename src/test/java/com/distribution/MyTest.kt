package com.distribution

import com.alibaba.fastjson.JSON
import com.distribution.common.excel.ExcelUtils
import com.distribution.common.utils.OkHttpUtil
import com.distribution.common.utils.Result
import com.distribution.modules.card.entity.CardApiResponse
import com.google.common.collect.Lists
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import org.apache.commons.lang3.StringUtils
import org.junit.Test
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution

 * @create 2018/5/9-22:23
 */
class MyTest {
    @Test
    fun test1() {
        val f = File("/Volumes/APPLE SD Card Reader Media/工作文档/钱到手app/签到表7月31日.xlsx")
        ExcelUtils.readExcel(f.inputStream(), 0).forEach { it.filter { StringUtils.isNoneBlank(it) }.forEach { println(it) } }
    }

    @Test
    fun t3() {
        val s: String? = ""

        val split = s?.split(",")
        print(split)
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
        } else {
            print(response.message())
        }
    }

    @Test
    fun testKSmall() {
        val nums1 = intArrayOf(1, 3, 5, 8, 9, 3, 12, 33)
        val nums2 = intArrayOf(4, 22, 44, 66, 77, 1, 8)
        print(kSmallestPairs(nums1, nums2, 3))
    }

    fun kSmallestPairs(nums1: IntArray, nums2: IntArray, k: Int): List<IntArray> {
        var arr = k
        val result = ArrayList<IntArray>()
        if (nums1.isEmpty() || nums2.isEmpty() || arr == 0) return result
        val heap = PriorityQueue(Comparator<IntArray> { o1, o2 -> o1[0] + o1[1] - o2[0] - o2[1] })

        for (i in nums1.indices) {
            heap.offer(intArrayOf(nums1[i], nums2[0], 0))
        }
        while (arr-- != 0 && !heap.isEmpty()) {
            val min = heap.poll()
            result.add(intArrayOf(min[0], min[1]))
            if (min[2] == nums2.size) continue
            heap.offer(intArrayOf(min[0], nums2[min[2] + 1], min[2] + 1))
        }
        return result

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

    @Test
    fun test8() {
        val sd = "20190101"
        val dateTime = LocalDate.parse(sd, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        print(dateTime)
//        print(sd.length)
    }

    @Test
    fun testFuncMap() {
        val map = mutableMapOf<String, (Int, Int) -> Int>()

        map["1"] = { i, i1 -> i + i1 }
        map["2"] = { i, i1 -> i * i1 }
        val f = map["1"]
        when {
            f != null -> print("type 1 = ${f(1, 2)}")
            else -> print("123")
        }

    }


    @Test
    fun testCoroutine() = runBlocking {
        GlobalScope.launch {
            // 在后台启动一个新的协程并继续
            delay(1000L)
            println("World!")
        }
        println("Hello,") // 主线程中的代码会立即执行
        // 但是这个表达式阻塞了主线程
        delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
    }

    @Test
    fun testCoroutine2() = runBlocking {
        val job = GlobalScope.launch {
            // 启动一个新协程并保持对这个作业的引用
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // 等待直到子协程执行结束
    }

    @Test
    fun testCoroutine3() = runBlocking {
        launch {
            delay(1000L)
            println("World!")
        }
        print("Hello,")
    }

    @Test
    fun testDate() {
        val str = "2019-01-01"
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        print(format.parse(str))
    }


    @Test
    fun testFibonacci() {
        for (i in 0..20) {
            println(fibonacci(i))
        }

    }

    private fun fibonacci(num: Int): Int {
        if (num < 2) {
            return 1
        }
        return fibonacci(num - 1) + fibonacci(num - 2)
    }

    @Test
    fun testFibonacci2() {

        val function = fibonacci2()
        for (i in 0..20) {
            println(function())
        }
    }

    private fun fibonacci2(): () -> Int {
        var a = 0
        var b = 1
        return fun(): Int {
            a = b
            b += a
            return a
        }
    }

    @Test
    fun t2() {
        val loc = listOf("北京", "上海", "广州", "深圳", "杭州")
        val flatMap = loc.flatMap {
            listOf(it, it + 1)
        }
        println("转换后的结果:$flatMap")
    }

    @Test
    fun tt() {
        val r = Result().ok("123").put("test", true)
        println(JSON.toJSONString(r))
    }
}
