@file:Suppress("unused")

package com.distribution.common.utils

import okhttp3.*
import org.apache.http.client.utils.URLEncodedUtils
import org.apache.http.message.BasicNameValuePair
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author ChunLiang Hu
 * @Company
 * @Project swwx-payment
 * @Package com.tencent.sxwx.payment.util
 * @create 2018/5/14-15:49
 */
object OkHttpUtil {

    private val M_OK_HTTP_CLIENT = OkHttpClient()

    private const val CHARSET_NAME = "UTF-8"

    init {
        M_OK_HTTP_CLIENT.newBuilder().connectTimeout(30, TimeUnit.SECONDS).build()
    }

    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun execute(request: Request): Response {
        return M_OK_HTTP_CLIENT.newCall(request).execute()
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    fun enqueue(request: Request, responseCallback: Callback) {
        M_OK_HTTP_CLIENT.newCall(request).enqueue(responseCallback)
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     *
     * @param request
     */
    fun enqueue(request: Request) {
        M_OK_HTTP_CLIENT.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

            }
        })
    }

    @Throws(IOException::class)
    fun getStringFromServer(url: String): String {
        val request = Request.Builder().url(url).build()
        val response = execute(request)
        return if (response.isSuccessful) {
            Objects.requireNonNull<ResponseBody>(response.body()).string()
        } else {
            throw IOException("Unexpected code $response")
        }
    }

    /**
     * 这里使用了HttpClinet的API。只是为了方便
     *
     * @param params
     * @return
     */
    private fun formatParams(params: List<BasicNameValuePair>): String {
        return URLEncodedUtils.format(params, CHARSET_NAME)
    }

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url
     * @param params
     * @return
     */
    fun attachHttpGetParams(url: String, params: List<BasicNameValuePair>): String {
        return url + "?" + formatParams(params)
    }

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url
     * @param params
     * @return
     */
    fun attachHttpGetParams(url: String, params: Map<String, String>): String {
        val pairList = LinkedList<BasicNameValuePair>()
        params.forEach { k, v -> pairList.add(BasicNameValuePair(k, v)) }
        return url + "?" + formatParams(pairList)
    }

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     *
     * @param url
     * @param name
     * @param value
     * @return
     */
    fun attachHttpGetParam(url: String, name: String, value: String): String {
        return "$url?$name=$value"
    }
}
