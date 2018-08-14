package moe.rainbowyang.platform

/**
 * 平台类，用于汇总对于平台的api操作
 *
 * @author Rainbow Yang
 */
abstract class Platform {
    var account: Account? = null

    /** 注册api账号*/
    fun register(account: Account) {
        this.account = account
    }

    // Account-Unneeded START

    /** 获取币币交易对的当前行情*/
    abstract fun ticker(symbol: String): String

    /** 获取币币市场深度，默认200*/
    abstract fun depth(symbol: String, size: Int = 200): String

    /** 获取币币交易信息(60条)*/
    abstract fun trades(symbol: String, since: Long): String

    /** 获取币币K线数据*/
    abstract fun kline(symbol: String, type: String, size: Int): String

    // Account-Unneeded END
}