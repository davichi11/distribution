package io.leetcode

import java.util.*
import java.util.function.IntConsumer

/**
 * @Company
 * @Project renren-fast
 * @Package io.leetcode
 * @Description TODO(描述)
 * @author ChunLiang Hu
 * @create 2018/4/4-19:47
 */
fun main(args: Array<String>) {
    val arr = intArrayOf(1, 1, 2, 3, 4, 4)
    println("去重后数组长度=${removeDuplicates(arr)}")
    arr.forEach { print(it) }

    println("最大利润是${maxProfit(intArrayOf(2, 3, 1, 6, 4, 9, 0))}")

//    rotate(intArrayOf(1, 2, 3, 4, 5, 6, 7), 2)

    val newarr = intArrayOf(1, 2, 3, 1, 1, 2)
    println("数组[1,2,3,1]中是否存在重复${containsDuplicate(newarr)}")

    println("数组(1, 2, 2, 3, 3, 4, 4, 5, 1)中 不重复的数是${singleNumber(intArrayOf(1, 2, 2, 3, 3, 4, 4, 5, 1))}")

    val arr1 = intArrayOf(1, 2, 2, 1)
    val arr2 = intArrayOf(2, 2)
    val i = intersect(arr1, arr2)
    i.forEach { print(it) }
    println()

    val digits = intArrayOf(1, 2, 3, 0)
    plusOne2(digits).forEach { print(it) }

    moveZeroes(intArrayOf(0, 1, 0, 3, 12))
}


/**
 * 数字数组去重,返回新长度
 * 这道题的解题思路是，我们使用快慢指针来记录遍历的坐标，
 * 最开始时两个指针都指向第一个数字，如果两个指针指的数字相同，
 * 则快指针向前走一步，如果不同，则两个指针都向前走一步，
 * 这样当快指针走完整个数组后，慢指针当前的坐标加1就是数组中不同数字的个数
 */
fun removeDuplicates(nums: IntArray): Int {
    if (nums.isEmpty()) {
        return 0
    }

    var i = 0 //快指针
    var j = 0 //慢指针

    while (i < nums.size) {
        if (nums[j] != nums[i]) {
            nums[++j] = nums[i]
        }
        i++
    }
    return j + 1
}

/**
 * 买卖股票的最佳时机 II
 * 问题描述:假设有一个数组，它的第 i 个元素是一个给定的股票在第 i 天的价格。
 * 设计一个算法来找到最大的利润。你可以完成尽可能多的交易（多次买卖股票）。然而，你不能同时参与多个交易（你必须在再次购买前出售股票）。
 */
fun maxProfit(prices: IntArray): Int {
    if (prices.isEmpty()) {
        return 0
    }
    var sum = 0
    for (i in 0 until prices.size - 1) {
        if (prices[i] < prices[i + 1]) {
            sum += prices[i + 1] - prices[i]
        }
    }
    return sum
}

/**
 * 旋转数组
 * 问题描述:将包含 n 个元素的数组向右旋转 k 步。
 * 例如，如果  n = 7 ,  k = 3，给定数组  [1,2,3,4,5,6,7],向右旋转后的结果为 [5,6,7,1,2,3,4]。
 */
fun rotate(nums: IntArray, k: Int) {
    var k = k
    k %= nums.size
    reverse(nums, 0, nums.size - 1)
    reverse(nums, 0, k - 1)
    reverse(nums, k, nums.size - 1)

    Arrays.stream(nums).forEach(IntConsumer { print(it) })
}

private fun reverse(nums: IntArray, i: Int, j: Int) {
    var i = i
    var j = j
    while (i < j) {
        swap(nums, i, j)
        i++
        j--
    }
}

private fun swap(nums: IntArray, i: Int, j: Int) {
    val tmp = nums[i]
    nums[i] = nums[j]
    nums[j] = tmp
}

/**
 * 存在重复
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果任何值在数组中出现至少两次，函数应该返回 true。如果每个元素都不相同，则返回 false。
 */
fun containsDuplicate(nums: IntArray): Boolean {
    if (nums.size == 1 || nums.isEmpty()) {
        return false
    }
    val set: HashSet<Int> = hashSetOf()
    nums.forEach { set.add(it) }
    println("重复次数为${nums.size - set.size}")
    return set.size < nums.size
}

/**
 * 只出现一次的数字
 * 给定一个整数数组，除了某个元素外其余元素均出现两次。请找出这个只出现一次的元素。
 */
fun singleNumber(nums: IntArray): Int {
    var temp = 0
    for (num in nums) {
        temp = temp xor num  //异或运算
    }
    return temp
}

/**
 * 取交集
 */
fun intersect(nums1: IntArray, nums2: IntArray): IntArray {
    nums1.sort()
    nums2.sort()

    val tmp = ArrayList<Int>()

    var i = 0
    var j = 0
    while (i < nums1.size && j < nums2.size) {
        when {
            nums2[j] > nums1[i] -> i++
            nums2[j] < nums1[i] -> j++
            else -> {
                tmp.add(nums1[i])
                i++
                j++
            }
        }
    }

    val result = IntArray(tmp.size)
    for (k in result.indices) {
        result[k] = tmp[k]
    }
    return result
}

/**
 * 加一
 */
fun plusOne(digits: IntArray): IntArray {
    val list = digits.map { it + 1 }.sortedDescending()
    return list.toIntArray()
}

fun plusOne2(digits: IntArray): IntArray {
    val n = digits.size
    for (i in digits.indices.reversed()) {
        if (digits[i] < 9) {
            ++digits[i]
            return digits
        }
        digits[i] = 0
    }
    val res = IntArray(n + 1)
    res[0] = 1
    return res
}

/**
 * 移动零
 * 给定一个数组 nums, 编写一个函数将所有 0 移动到它的末尾，同时保持非零元素的相对顺序。
 * 例如， 定义 nums = [0, 1, 0, 3, 12]，调用函数之后， nums 应为 [1, 3, 12, 0, 0]。
 */
fun moveZeroes(nums: IntArray) {
    var j = 0
    for (i in 0 until nums.size) {
        if (nums[i] != 0) {
            nums[j++] = nums[i]
        }
    }

    while (j < nums.size) {
        nums[j] = 0
        j++
    }
    nums.forEach { print(it) }
}



