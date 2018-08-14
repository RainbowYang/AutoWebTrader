package moe.rainbowyang.util

import com.google.gson.Gson
import java.io.Reader

// 基于Kotlin的特点，对Gson的调用方式进行简化。

inline fun <reified T> String.fromJson() = Gson().fromJson<T>(this, T::class.java)

inline fun <reified T> Reader.fromJson() = Gson().fromJson<T>(this, T::class.java)

fun Any?.toJson() = Gson().toJson(this)
