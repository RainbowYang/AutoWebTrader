package moe.rainbowyang.platform

import moe.rainbowyang.model.*
import moe.rainbowyang.util.*
import java.io.IOException

/**
 * 平台类，用于汇总对于平台的api操作
 *
 * @author Rainbow Yang
 */
abstract class Platform(checkNet: String) {

    var account: APIKey? = null

    val apiKey get() = account!!.apiKey
    val secretKey get() = account!!.secretKey

    val okHttpHandle = OkHttpHandle(checkNet)

    /** 注册api账号*/
    fun register(account: APIKey) {
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
    @Throws(IOException::class)
    open fun ticker(coin: String, payment: String): Ticker = throw UnsupportedOperationException()

    /** 获取币币市场深度，默认200*/
    @Throws(IOException::class)
    open fun depth(coin: String, payment: String, size: Int = 200): Depth = throw UnsupportedOperationException()

    /** 获取币币交易信息(60条)*/
    @Throws(IOException::class)
    open fun trades(coin: String, payment: String, since: Long): Trades = throw UnsupportedOperationException()

    /** 获取币币K线数据*/
    @Throws(IOException::class)
    open fun kLine(coin: String, payment: String, period: String): KLine = throw UnsupportedOperationException()

    // Account-Unneeded END ↑

    // Account-Needed Start ↓

    /** 获取用户账户信息 */
    @Throws(IOException::class)
    open fun userInfo(): UserInfo = throw UnsupportedOperationException()

    /** 发送交易请求 */
    @Throws(IOException::class)
    open fun trade(coin: String, payment: String, type: String, price: Double, amount: Double): String =
            throw UnsupportedOperationException()

    /**
     * 发送交易请求
     *
     * 自动判断买进还是卖出
     *
     * @param amount 当amount为正时，为买入；负时，卖出
     *
     */
    fun trade(coin: String, payment: String, price: Double, amount: Double): String =
            if (amount >= 0) {
                trade(coin, payment, BUY, price, amount)
            } else {
                trade(coin, payment, SELL, price, -amount)
            }

    /** 发送购买请求 */
    open fun buy(coin: String, payment: String, price: Double, amount: Double): String =
            trade(coin, payment, BUY, price, amount)

    /** 发送卖出请求 */
    open fun sell(coin: String, payment: String, price: Double, amount: Double): String =
            trade(coin, payment, SELL, price, amount)

// Account-Needed END ↑

    protected infix fun String.with(that: String) = this + "_" + that

    @Throws(IOException::class)
    fun get(url: String, vararg parameter: Pair<String, String>): String =
            okHttpHandle.get(url, linkedMapOf(*parameter))

    // post with apiKey and sign
    @Throws(IOException::class)
    fun post(url: String, vararg parameter: Pair<String, String>): String {
        isAccountRegistered()

        val sign = Signer.buildSign(linkedMapOf(API_KEY to apiKey, *parameter), secretKey)
        return okHttpHandle.post(url, linkedMapOf(API_KEY to apiKey, *parameter, SIGN to sign))
    }
}