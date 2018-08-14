package moe.rainbowyang.model

import java.util.*

/**
 * @author Rainbow Yang
 */
class KLine : LinkedList<KLine.KPoint>() {
    class KPoint : LinkedList<Double>() {
        val time: Long get() = this[0].toLong()
        val open: Double get() = this[1]
        val high: Double get() = this[2]
        val low: Double get() = this[3]
        val close: Double get() = this[4]
        val vol: Double get() = this[5]
    }
}