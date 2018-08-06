package com.distribution

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * @Company
 * @Project distribution
 * @Package com.distribution
 * @Description TODO(描述)
 * @author ChunLiang Hu
 * @create 2018/7/30-22:47
 */
fun main(args: Array<String>) {
    print("222")
    launch { // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        aa()
    }
}

private suspend fun aa() {
    print("123123")
}