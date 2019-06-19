package io.leetcode

import java.util.*



/**
 * @Company
 * @Project renren-fast
 * @Package io.renren
 * @Description TODO(描述)
 * @author ChunLiang Hu
 * @create 2018/4/3-21:39
 */
fun main() {
    println("是否是有效括号(])=${isValid("(])")}")
//    println("是否是有效括号(([]){})=${isValid("(([]){})")}")
}



/**
 * 判断是否是回文数
 */
fun isPalindrome(x: Int): Boolean {
    var a = x
    var b = 0
    if (x < 0) return false
    while (a > 0) {
        b = b * 10 + a % 10
        a /= 10
    }
    return x == b
}

/**
 * 判断是否是有效的括号
 * 逻辑: 1.将() {} [] 以键值对的形式放入map中
 *       2.遍历输入的字符串数组
 *       3.如果是左括号(,{,[ 放入一个队列中
 *       4.不是左括号的话,从map中取出队列中的第一个字符串 进行比对 如果和当前遍历的字符串相等就出栈
 *       5. 若不相等 则此字符串不是有效括号
 *       6. 便利结束若队列为空 则是有效括号
 */
fun isValid(s: String): Boolean {
    if (s.length == 1) {
        return false
    }
    val map: Map<String, String> = hashMapOf("(" to ")", "{" to "}", "[" to "]")
    val arr = s.toCharArray()
    val deque: LinkedList<String> = LinkedList()
    arr.map { it.toString() }.forEach {
        when (it) {
            "{", "[", "(" -> deque.push(it)
            else -> {
                when {
                    deque.isEmpty() -> return false
                    map[deque.first] == it -> deque.pop()
                    else -> return false
                }
            }
        }
    }
    return deque.isEmpty()
}
