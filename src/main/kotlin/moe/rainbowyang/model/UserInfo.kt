package moe.rainbowyang.model

/**
 * @author Rainbow Yang
 */
data class UserInfo(val info: Info) {
    data class Info(val funds: Funds) {
        data class Funds(val free: Bill, val freezed: Bill) {
            data class Bill(val btc: Double, val eth: Double, val etc: Double,
                            val ltc: Double, val okb: Double, val xrp: Double,
                            val usdt: Double
            ) {
                fun get(coin: String) =
                        this::class.java.getDeclaredField(coin.toLowerCase()).get(this).toString().toDouble()

            }
        }
    }
}