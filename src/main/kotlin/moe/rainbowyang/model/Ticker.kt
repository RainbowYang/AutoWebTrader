package moe.rainbowyang.model

/**
 * 币币行情
 *
 * @author Rainbow Yang
 */
data class Ticker(val data: Long, val ticker: RealTicker) {
    data class RealTicker(
            val high: Double,
            val vol: Double,
            val last: Double,
            val low: Double,
            val buy: Double,
            val sell: Double
    )
}