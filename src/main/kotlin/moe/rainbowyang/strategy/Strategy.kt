package moe.rainbowyang.strategy

import moe.rainbowyang.platform.fake.FakePlatform
import moe.rainbowyang.platform.okex.Okex
import moe.rainbowyang.util.OKB
import moe.rainbowyang.util.THIRTY_MIN
import moe.rainbowyang.util.USDT
import java.util.*
import kotlin.concurrent.schedule

/**
 * 交易策略
 * @author Rainbow Yang
 */
class Strategy {

    val okex = Okex()
    val fake = FakePlatform()

    fun start() {
        Timer().schedule(0, 1000) {
            plan()
        }
    }

    fun plan() {
        val kline = Kline()

        val MA = kline.MA5
        val close = kline.last().close
        if (close > MA) {
            print("buy")
        } else if (close < MA) {
            print("sell")
        }

    }


    fun Kline() = okex.kline(OKB, USDT, THIRTY_MIN, 600)
}
