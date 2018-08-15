package moe.rainbowyang.platform

import moe.rainbowyang.util.fromJson
import java.io.FileInputStream
import java.io.InputStreamReader

class APIKey(val apiKey: String, val secretKey: String) {
    companion object {
        operator fun invoke(file: String = "api_key.json") =
                InputStreamReader(FileInputStream(file)).fromJson<APIKey>()!!
    }
}