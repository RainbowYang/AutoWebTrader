package moe.rainbowyang.strategy

import moe.rainbowyang.model.KLine
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule

/**
 * 交易策略
 * @author Rainbow Yang
 */
abstract class Strategy(var planPeriod: Long) {

    fun start() {
        Timer().schedule(0, planPeriod) {
            plan()
            kLine().last.close
        }
    }

    /** 具体策略 */
    abstract fun plan()

    fun setPosition(percent: Double) {
        if (percent > 1 || percent < 0) throw UnsupportedOperationException("$percent is not allowed")

        val amount = (percent - position()) * allAsCoin()

        trade(price(), amount)
    }

    fun price() = kLine().last.close
    abstract fun kLine(): KLine

    fun allAsCoin() = coin() + payment() / price()
    fun allAsPayment() = coin() * price() + payment()
    fun position() = coin() / allAsCoin()

    fun coin() = coinFree() + coinfrozen()
    abstract fun coinFree(): Double
    abstract fun coinfrozen(): Double

    fun payment() = paymentFree() + paymentfrozen()
    abstract fun paymentFree(): Double
    abstract fun paymentfrozen(): Double

    abstract fun trade(price: Double, amount: Double): String

    fun printUserInfo() {
        println("--------------------------------")
        println("Time: ${SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Date())}")
        println("Position: ${position()}")
        println("Price: 1 Coin=${price()}Payment")
        println("Coin:")
        println("    free:${coinFree()}")
        println("    frozen:${coinfrozen()}")
        println("Payment:")
        println("    free:${paymentFree()}")
        println("    frozen:${paymentfrozen()}")
        println("Total:")
        println("    as Coin:${allAsCoin()}")
        println("    as Payment:${allAsPayment()}")
        println("--------------------------------")
    }
}
