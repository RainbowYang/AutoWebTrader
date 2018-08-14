package moe.rainbowyang.platform.okex

import moe.rainbowyang.platform.Platform
import moe.rainbowyang.util.*

class Okex : Platform() {
    override fun ticker(symbol: String): String =
            get(OKEX_URL + TICKER_URL, SYMBOL to symbol)

    override fun depth(symbol: String, size: Int) =
            get(OKEX_URL + DEPTH_URL, SYMBOL to symbol, SIZE to size.toString())

    override fun trades(symbol: String, since: Long) =
            get(OKEX_URL + TRADES_URL, SYMBOL to symbol, SINCE to since.toString())

    override fun kline(symbol: String, type: String, size: Int) =
            get(OKEX_URL + KLINE_URL, SYMBOL to symbol, TYPE to type, SIZE to size.toString())
}