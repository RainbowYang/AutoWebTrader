package moe.rainbowyang.strategy

import moe.rainbowyang.platform.okex.Okex
import moe.rainbowyang.util.*

/**
 * @author Rainbow Yang
 */
fun main(args: Array<String>) {
    val periods = listOf("1min", "3min", "5min", "15min", "30min", "1hour", "2hour", "4hour", "6hour", "12hour", "1day", "1week")

    val coins = listOf(BTC, LTC, ETH, OKB, XRP)

    val okex = Okex()
    for (coin in coins) {
        println(coin)
        for (period in periods) {
            val kLine = okex.kLine(coin, USDT, period)

            var coin = 1.0
            var payment = 0.0

            val fprice = kLine.last.close
            (30..kLine.size).forEach {

                val MA5 = kLine.MA(5, it)
                val MA10 = kLine.MA(10, it)
                val MA20 = kLine.MA(20, it)
                val MA30 = kLine.MA(30, it)

                val price = kLine[it - 1].close

                if (MA5 > MA10 && MA10 > MA20 && MA20 > MA30) {
                    coin += payment / price * 0.998
                    payment = 0.0
                } else if (MA30 > MA20 && MA20 > MA10 && MA10 > MA5) {
                    payment += coin * price * 0.998
                    coin = 0.0
                }
            }
            println("    $period :${coin + payment / fprice}")
        }
    }
}

