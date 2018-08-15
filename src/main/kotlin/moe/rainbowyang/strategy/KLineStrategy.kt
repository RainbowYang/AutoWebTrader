package moe.rainbowyang.strategy

/**
 * @author Rainbow Yang
 */
abstract class KLineStrategy(planPeriod: Long) : Strategy(planPeriod) {
    override fun plan() {

        printUserInfo(account())

        val kLine = kLine()

        val MA5 = kLine.MA5
        val MA10 = kLine.MA10
        val MA20 = kLine.MA20
        val MA30 = kLine.MA30

        if (MA5 > MA10 && MA10 > MA20 && MA20 > MA30) {
            setPosition(1.0)
        } else if (MA5 < MA10 && MA10 < MA20 && MA20 < MA30) {
            setPosition(0.0)
        }


    }
}