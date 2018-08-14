package moe.rainbowyang.platform

import moe.rainbowyang.util.fromJson
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * 账户类
 * @author Rainbow Yang
 */
data class Account(val apiKey: String, val secretKey: String) {
    companion object {
        operator fun invoke(file: String = "account.json") =
                InputStreamReader(FileInputStream(file)).fromJson<Account>()!!
    }
}