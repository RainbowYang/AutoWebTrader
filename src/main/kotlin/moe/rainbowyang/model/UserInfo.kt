package moe.rainbowyang.model

/**
 * @author Rainbow Yang
 */
data class UserInfo(val info: Info) {

    fun getAccount(coin: String, payment: String) = Account(coin, payment)

    inner class Account(val coin: String, val payment: String) {
        val coinFree = info.funds.free.getAmount(coin)
        val coinfrozen= info.funds.freezed.getAmount(coin)
        val coinAll = coinFree + coinfrozen

        val paymentFree = info.funds.free.getAmount(payment)
        val paymentfrozen = info.funds.freezed.getAmount(payment)
        val paymentAll= paymentFree + paymentfrozen
    }

    data class Info(val funds: Funds) {
        data class Funds(val free: Bill, val freezed: Bill) {
            data class Bill(val btc: Double, val eth: Double, val etc: Double,
                            val ltc: Double, val okb: Double, val xrp: Double,
                            val usdt: Double
            ) {
                fun getAmount(coin: String) =
                        this::class.java.getDeclaredField(coin).get(this).toString().toDouble()


            }
        }
    }
}