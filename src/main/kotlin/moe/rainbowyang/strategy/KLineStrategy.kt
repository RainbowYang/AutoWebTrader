package moe.rainbowyang.strategy

import moe.rainbowyang.model.KLine
import moe.rainbowyang.model.UserInfo
import moe.rainbowyang.platform.APIKey
import moe.rainbowyang.platform.okex.Okex

/**
 * @author Rainbow Yang
 */
class KLineStrategy(val coin: String, val payment: String, planPeriod: Long, val period: String) :
        Strategy(planPeriod) {

    val okex = Okex()

    init {
        okex.register(APIKey())
    }

    override fun plan() {
        val kline = kLine()

        if (price() > kline.MA10) {
            setPosition(0.8)
        } else {
            setPosition(0.2)
        }
    }

    override fun kLine(): KLine = okex.kline(coin, payment, period)

    override fun account() = okex.userInfo().getAccount(coin, payment)

    override fun trade(price: Double, amount: Double) = okex.trade(coin, payment, price, amount)

}