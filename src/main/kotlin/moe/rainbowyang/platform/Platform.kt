package moe.rainbowyang.platform

import moe.rainbowyang.model.*
import moe.rainbowyang.util.API_KEY
import moe.rainbowyang.util.OkHttpHandle
import moe.rainbowyang.util.SIGN
import moe.rainbowyang.util.Signer

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
    open fun ticker(trans: String, payment: String): Ticker = throw UnsupportedOperationException()

    /** 获取币币市场深度，默认200*/
    open fun depth(trans: String, payment: String, size: Int = 200): Depth = throw UnsupportedOperationException()

    /** 获取币币交易信息(60条)*/
    open fun trades(trans: String, payment: String, since: Long): Trades = throw UnsupportedOperationException()

    /** 获取币币K线数据*/
    open fun kline(trans: String, payment: String, period: String, size: Int): KLine =
            throw UnsupportedOperationException()

    // Account-Unneeded END ↑

    // Account-Needed Start ↓

    /** 获取用户账户信息 */
    open fun userInfo(): UserInfo = throw UnsupportedOperationException()

    /** 发送交易请求 */
    open fun trade(trans: String, payment: String, type: String, price: Double, amount: Double): String =
            throw UnsupportedOperationException()

    // Account-Needed END ↑

    protected infix fun String.with(that: String) = this + "_" + that

    fun get(url: String, vararg parameter: Pair<String, String>): String =
            okHttpHandle.get(url, linkedMapOf(*parameter))

    // post with apiKey and sign
    fun post(url: String, vararg parameter: Pair<String, String>): String {
        isAccountRegistered()

        val sign = Signer.buildSign(linkedMapOf(API_KEY to apiKey, *parameter), account!!.secretKey)
        return okHttpHandle.post(url, linkedMapOf(API_KEY to apiKey, *parameter, SIGN to sign))
    }
}