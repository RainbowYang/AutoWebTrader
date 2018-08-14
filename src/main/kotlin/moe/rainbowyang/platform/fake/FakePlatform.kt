package moe.rainbowyang.platform.fake

/**
 * 本类只是为了策略测试
 * @author Rainbow Yang
 */
class FakePlatform(var transCoin: Double = 0.0, var paymentCoin: Double = 1.0, val fees: Double = 0.0) {

    fun buy(price: Double) {
        transCoin += paymentCoin / price * (1 - fees)
        paymentCoin = 0.0
    }

    fun sell(price: Double) {
        paymentCoin += transCoin * price * (1 - fees)
        transCoin = 0.0
    }
}