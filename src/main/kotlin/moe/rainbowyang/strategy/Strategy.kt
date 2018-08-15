package moe.rainbowyang.strategy

import moe.rainbowyang.model.KLine
import moe.rainbowyang.model.UserInfo
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

        val account = account()
        val amount = (percent - account.position()) * account.allAsCoin()

        trade(price(), amount)
    }

    abstract fun kLine(): KLine
    abstract fun account(): UserInfo.Account
    abstract fun trade(price: Double, amount: Double): String

    fun price() = kLine().last.close

    fun UserInfo.Account.allAsCoin() = coinAll + paymentAll / price()
    fun UserInfo.Account.allAsPayment() = coinAll * price() + paymentAll
    fun UserInfo.Account.position() = coinAll / allAsCoin()

    fun printUserInfo() = with(account()) {
        println("-----------------------------------")
        println("Time: ${SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Date())}")
        println("Price: 1 ${coin.toUpperCase()} = ${price()} ${payment.toUpperCase()}")
        println("Position: ${position()}")
        println("Coin(${coin.toUpperCase()}):")
        println("    Free:$coinFree")
        println("    Frozen:$coinfrozen")
        println("    All:$coinAll")
        println("Payment(${payment.toUpperCase()}):")
        println("    Free:  $paymentFree")
        println("    Frozen:$paymentfrozen")
        println("    All:   $paymentAll")
        println("Total:")
        println("    as Coin:${allAsCoin()} ${coin.toUpperCase()}")
        println("    as Payment:${allAsPayment()} ${payment.toUpperCase()}")
        println("-----------------------------------")
    }
}
