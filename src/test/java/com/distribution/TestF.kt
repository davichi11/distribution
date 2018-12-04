package com.distribution

/**
 * @Company
 * @Project distribution
 * @Package com.distribution
 * @Description TODO(描述)
 * @author ChunLiang Hu
 * @create 2018/7/30-22:47
 */
fun main(args: Array<String>) {

    val keys = 'a'..'f'
    val m = keys.associateWith { it.toString().repeat(5).capitalize() }
    println(m)
    val map = mapOf("1" to "2", "3" to "4", "5" to "")
    print(map.filterValues { it.isEmpty() })

}
