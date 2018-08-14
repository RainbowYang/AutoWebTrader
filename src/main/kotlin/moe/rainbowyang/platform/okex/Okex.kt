package moe.rainbowyang.platform.okex

import moe.rainbowyang.model.*
import moe.rainbowyang.platform.Platform
import moe.rainbowyang.util.*

class Okex : Platform(OKEX_URL) {

    override fun ticker(trans: String, payment: String) =
            get(TICKER_URL, SYMBOL to (trans with payment)).fromJson<Ticker>()

    override fun depth(trans: String, payment: String, size: Int) =
            get(DEPTH_URL, SYMBOL to (trans with payment), SIZE to size).fromJson<Depth>()

    override fun trades(trans: String, payment: String, since: Long) =
            get(TRADES_URL, SYMBOL to (trans with payment), SINCE to since).fromJson<Trades>()

    override fun kline(trans: String, payment: String, period: String, size: Int) =
            get(KLINE_URL, SYMBOL to (trans with payment), TYPE to period, SIZE to size).fromJson<KLine>()


    override fun userInfo() = post(USERINFO_URL).fromJson<UserInfo>()

    override fun trade(trans: String, payment: String, type: String, price: Double, amount: Double) =
            post(TRADE_URL, SYMBOL to (trans with payment), TYPE to type, PRICE to price, AMOUNT to amount)

    //强制转换为String，再组成Pair
    private infix fun Any.to(that: Any): Pair<String, String> = Pair(this.toString(), that.toString())
}