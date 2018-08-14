package moe.rainbowyang.platform.okex

/**
 * @author Rainbow Yang
 */
fun main(args: Array<String>) {
    val okex = Okex()
    println(okex.ticker("btc_usdt"))
}