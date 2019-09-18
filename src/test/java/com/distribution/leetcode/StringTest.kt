package io.leetcode

/**
 * @Company
 * @Project renren-fast
 * @Package io.leetcode
 
 * @author ChunLiang Hu
 * @create 2018/4/8-08:52
 */
fun main() {
    println(reverseString("hello"))

    println(reverse(-1563847412))
    val s = "cc"
    println("字符串$s 中的第一个唯一字符的下标是${firstUniqChar(s)}")
    val ns = "9223372036854775808"
    println("字符串$ns 转换为${myAtoi(ns)}")
}

fun reverseString(s: String): String {
    val count = s.length
    val value = s.toCharArray()
    val n = count - 1
    for (j in n - 1 shr 1 downTo 0) {
        val k = n - j
        val cj = value[j]
        val ck = value[k]
        value[j] = ck
        value[k] = cj
    }
    return String(value)
}


fun reverse(number: Int): Int {
    val result = when {
        number <= -2147483648 || number > 2147483647 -> 0
        number < 0 -> ("-" + (0 - number).toString().reversed()).toLong()
        else -> number.toString().reversed().toLong()
    }

    return when {
        result >= 2147483647 || result <= -2147483648 -> 0
        else -> result.toInt()
    }
}

/**
 * 字符串中的第一个唯一字符
 */
fun firstUniqChar(s: String): Int {
    if (s.isEmpty()) {
        return -1
    }
    val chars = s.toCharArray()
    val uniqChars = findUniqChars(chars)
    if (uniqChars.isEmpty()) {
        return -1
    }
    val uniqFirst = uniqChars[0]
    for (i in chars.indices) {
        if (uniqFirst == chars[i]) {
            return i

        }
    }
    return -1
}

fun findUniqChars(chars: CharArray): List<Char> {
    val map = mutableMapOf<Char, Int>()
    val list = mutableListOf<Char>()
    for (c in chars) {
        when {
            map[c] == null -> map[c] = 1
            else -> map[c] = map[c]!! + 1
        }
    }
    map.filterValues { it == 1 }.keys.forEach { list.add(it) }
    return list
}

fun myAtoi(str: String): Int {
    if (str.isEmpty() || str.isBlank()) {
        return 0
    }
    val charArray = str.trim().toCharArray()
    var symbol = ""
    var num: Long = 0
    if (charArray[0] == '-' || charArray[0] == '+') {
        symbol = charArray[0].toString()
    } else if (charArray[0] in '0'..'9') {
        num = charArray[0].toString().toLong()
    } else {
        return 0
    }
    for (i in 1 until charArray.size) {
        when {
            num > Int.MAX_VALUE -> return when (symbol) {
                "-" -> Int.MIN_VALUE
                else -> Int.MAX_VALUE
            }
            charArray[i] in '0'..'9' -> num = num * 10 + charArray[i].toString().toInt()
            else -> return trans(num, symbol)
        }
    }
    return trans(num, symbol)
}

fun trans(num: Long, symbol: String): Int {
    return when {
        num > 0 && symbol == "-" -> when {
            -num < Int.MIN_VALUE -> Int.MIN_VALUE
            else -> (-num).toInt()
        }
        num > 0 && num > Int.MAX_VALUE -> Int.MAX_VALUE
        else -> num.toInt()
    }
}

