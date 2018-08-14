package moe.rainbowyang.platform.okex

import moe.rainbowyang.platform.Platform
import moe.rainbowyang.util.*

class Okex : Platform(OKEX_URL) {

    override fun ticker(symbol: String): String =
            get(TICKER_URL, SYMBOL sto symbol)

    override fun depth(symbol: String, size: Int) =
            get(DEPTH_URL, SYMBOL sto symbol, SIZE sto size)

    override fun trades(symbol: String, since: Long) =
            get(TRADES_URL, SYMBOL sto symbol, SINCE sto since)

    override fun kline(symbol: String, type: String, size: Int) =
            get(KLINE_URL, SYMBOL sto symbol, TYPE sto type, SIZE sto size)

    //强制转换为String，再组成Pair
    private infix fun Any.sto(that: Any): Pair<String, String> = Pair(this.toString(), that.toString())
}