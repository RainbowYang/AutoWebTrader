package moe.rainbowyang.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * 用于生成 Okex的http请求中 所需的签名
 */
object Signer {

    /**
     * 生成签名
     */
    fun buildSign(sArray: Map<String, String>, secretKey: String): String {
        var mysign = ""
        try {
            var prestr = createLinkString(sArray) // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            prestr += "&secret_key=$secretKey" // 把拼接后的字符串再与安全校验码连接起来
            mysign = getMD5String(prestr)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mysign
    }

    /*
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     */
    private fun createLinkString(params: Map<String, String>): String {

        val keys = ArrayList(params.keys)
        keys.sort()
        var prestr = ""
        for (i in keys.indices) {
            val key = keys[i]
            val value = params[key]
            // 拼接时，不包括最后一个&字符
            prestr += if (i == keys.size - 1) {
                "$key=$value"
            } else {
                "$key=$value&"
            }
        }
        return prestr
    }

    /*
     * 生成32位大写MD5值
     */
    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    private fun getMD5String(str: String?): String {
        try {
            if (str == null || str.trim { it <= ' ' }.isEmpty()) {
                return ""
            }
            var bytes = str.toByteArray()
            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(bytes)
            bytes = messageDigest.digest()
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(HEX_DIGITS[bytes[i].toInt() and 0xf0 shr 4] + ""
                        + HEX_DIGITS[bytes[i].toInt() and 0xf])
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}