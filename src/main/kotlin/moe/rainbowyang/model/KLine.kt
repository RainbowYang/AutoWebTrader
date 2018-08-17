package moe.rainbowyang.model

import java.util.*

/**
 * @author Rainbow Yang
 */
class KLine : LinkedList<KLine.KPoint>() {

    val MA5 get() = MA(5)
    val MA10 get() = MA(10)
    val MA20 get() = MA(20)
    val MA30 get() = MA(30)

    fun MA(range: Int, until: Int = size): Double {

        var sum = 0.0

        (until - range until until).forEach { sum += this[it].close }

        return sum / range
    }

    class KPoint : LinkedList<Double>() {
        val time: Long get() = this[0].toLong()
        val open: Double get() = this[1]
        val high: Double get() = this[2]
        val low: Double get() = this[3]
        val close: Double get() = this[4]
        val vol: Double get() = this[5]
    }
}