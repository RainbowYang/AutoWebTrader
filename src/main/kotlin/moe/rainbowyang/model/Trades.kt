package moe.rainbowyang.model

import java.util.*

/**
 * 交易信息
 *
 * @author Rainbow Yang
 */
class Trades : LinkedList<Trades.Trade>() {
    data class Trade(
            val date: Long,
            val date_ms: Long,
            val amount: Double,
            val price: Double,
            val type: String,
            val tid: Int
    )
}
