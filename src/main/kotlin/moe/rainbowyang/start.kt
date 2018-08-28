package moe.rainbowyang

import moe.rainbowyang.strategy.OkexStrategy
import moe.rainbowyang.util.SIX_HOUR
import moe.rainbowyang.util.USDT
import moe.rainbowyang.util.XRP

/**
 * @author Rainbow Yang
 */
fun main(args: Array<String>) {
    val okexStrategy = OkexStrategy(XRP, USDT, 60000, SIX_HOUR)

    okexStrategy.start()

}