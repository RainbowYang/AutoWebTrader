package moe.rainbowyang.util

import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.Proxy.Type.*

/**
 * 对OkHttp进行包装，对外提供[get]和[post]
 * @author Rainbow Yang
 */
class OkHttpHandle(checkNetURL: String) {

    var client: OkHttpClient = OkHttpClient()

    init {
        try {
            get(checkNetURL)
        } catch (e: IOException) {
            client = OkHttpClient().newBuilder()
                    .proxy(Proxy(HTTP, InetSocketAddress("127.0.0.1", 1080)))
                    .build()!!
            println("所需平台网站已被封锁，所以启动默认代理127.0.0.1:1080")
        }
    }


    @Throws(IOException::class)
    fun get(url: String, parameter: LinkedHashMap<String, String> = linkedMapOf()): String {

        //创建url，并添加参数
        val url = HttpUrl.parse(url)!!.newBuilder().apply {
            parameter.forEach { (name, value) -> addEncodedQueryParameter(name, value) }
        }.build()

        val request = Request.Builder().url(url).build()

        return client.call(request)
    }


    @Throws(IOException::class)
    fun post(url: String, parameter: LinkedHashMap<String, String>): String {

        //根据参数，生成formBody
        val formBody = FormBody.Builder().apply {
            parameter.forEach { (name, value) -> add(name, value) }
        }.build()

        val request = Request.Builder().url(url).post(formBody).build()

        return client.call(request)
    }

    @Throws(IOException::class)
    private fun OkHttpClient.call(request: Request): String {
        val response = this.newCall(request).execute()

        return if (response.isSuccessful) {
            response.body()!!.string()
        } else {
            throw IOException("Unexpected code $response")
        }
    }
}