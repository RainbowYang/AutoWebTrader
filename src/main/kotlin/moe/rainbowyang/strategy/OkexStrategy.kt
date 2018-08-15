package moe.rainbowyang.strategy

import moe.rainbowyang.model.KLine
import moe.rainbowyang.platform.APIKey
import moe.rainbowyang.platform.okex.Okex

/**
 * @author Rainbow Yang
 */
class OkexStrategy(val coin: String, val payment: String, planPeriod: Long, val period: String) :
        KLineStrategy(planPeriod) {

    val okex = Okex()

    init {
        okex.register(APIKey())
    }

    override fun kLine(): KLine = okex.kline(coin, payment, period)
    override fun account() = okex.userInfo().getAccount(coin, payment)
    override fun trade(price: Double, amount: Double) = okex.trade(coin, payment, price, amount)

}