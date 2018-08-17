package moe.rainbowyang

import moe.rainbowyang.strategy.OkexStrategy
import moe.rainbowyang.util.FIFTEEN_MIN
import moe.rainbowyang.util.OKB
import moe.rainbowyang.util.ONE_HOUR
import moe.rainbowyang.util.USDT

/**
 * @author Rainbow Yang
 */
fun main(args: Array<String>) {
    val okexStrategy = OkexStrategy(OKB, USDT, 10000, FIFTEEN_MIN)

    okexStrategy.start()

}