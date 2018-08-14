package moe.rainbowyang.platform

import moe.rainbowyang.util.API_KEY
import moe.rainbowyang.util.OkHttpHandle
import moe.rainbowyang.util.SIGN
import moe.rainbowyang.util.Signer
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other

/**
 * 平台类，用于汇总对于平台的api操作
 *
 * @author Rainbow Yang
 */
abstract class Platform(checkNet: String) {

    var account: Account? = null

    val apiKey get() = account!!.apiKey
    val secretKey get() = account!!.secretKey

    val okHttpHandle = OkHttpHandle(checkNet)

    /** 注册api账号*/
    fun register(account: Account) {
        this.account = account
        account.apiKey
        account.secretKey
    }

    fun isAccountRegistered() {
        if (account == null) {
            throw UnsupportedOperationException("$this has no account registered")
        }
    }


    // Account-Unneeded START ↓

    /** 获取币币交易对的当前行情*/
    abstract fun ticker(trans: String, payment: String): String

    /** 获取币币市场深度，默认200*/
    abstract fun depth(trans: String, payment: String, size: Int = 200): String

    /** 获取币币交易信息(60条)*/
    abstract fun trades(trans: String, payment: String, since: Long): String

    /** 获取币币K线数据*/
    abstract fun kline(trans: String, payment: String, period: String, size: Int): String

    // Account-Unneeded END ↑

    // Account-Needed Start ↓

    /** 获取用户账户信息 */
    abstract fun userInfo(): String

    /** 发送交易请求 */
    abstract fun trade(trans: String, payment: String, type: String, price: Double, amount: Double): String

    // Account-Needed END ↑

    protected infix fun String.with(that: String) = this + "_" + that

    fun get(url: String, vararg parameter: Pair<String, String>): String =
            okHttpHandle.get(url, linkedMapOf(*parameter))

    // post with apiKey and sign
    fun post(url: String, vararg parameter: Pair<String, String>): String {
        isAccountRegistered()

        val sign = Signer.buildSign(parameter.toMap(), account!!.secretKey)
        return okHttpHandle.post(url, linkedMapOf(API_KEY to apiKey, *parameter, SIGN to sign))

    }
}